package dal

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import models.Goal

import scala.concurrent.{ Future, ExecutionContext }

/**
  * A repository for goals.
  *
  * @param dbConfigProvider The Play db config provider. Play will inject this for you.
  */
@Singleton
class GoalRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import driver.api._

  /**
    * Here we define the table. It will have a name of goals
    */
  private class GoalsTable(tag: Tag) extends Table[Goal](tag, "goals") {

    /** The ID column, which is the primary key, and auto incremented */
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    /** The name column */
    def description = column[String]("description")

    /** The age column */
    def score = column[Int]("score")

    /**
      * This is the tables default "projection".
      *
      * It defines how the columns are converted to and from the Person object.
      *
      * In this case, we are simply passing the id, name and page parameters to the Person case classes
      * apply and unapply methods.
      */
    def * = (id, description, score) <> ((Goal.apply _).tupled, Goal.unapply)
  }

  /**
    * The starting point for all queries on the goals table.
    */
  private val goals = TableQuery[GoalsTable]

  /**
    * Create a person with the given name and age.
    *
    * This is an asynchronous operation, it will return a future of the created person, which can be used to obtain the
    * id for that person.
    */
  def create(description: String, score: Int): Future[Goal] = db.run {
    // We create a projection of just the name and age columns, since we're not inserting a value for the id column
    (goals.map(g => (g.description, g.score))
      // Now define it to return the id, because we want to know what id was generated for the person
      returning goals.map(_.id)
      // And we define a transformation for the returned value, which combines our original parameters with the
      // returned id
      into ((descriptionScore, id) => Goal(id, descriptionScore._1, descriptionScore._2))
      // And finally, insert the person into the database
      ) += (description, score)
  }

  /**
    * List all the goals in the database.
    */
  def list(): Future[Seq[Goal]] = db.run {
    goals.result
  }
}
