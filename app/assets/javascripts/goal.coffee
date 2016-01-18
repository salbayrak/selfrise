$ ->
  $.get "/goals", (goals) ->
    $.each goals, (index, goal) ->
      description = $("<div>").addClass("description").text goal.description
      score = $("<div>").addClass("score").text goal.score
      $("#goals").append $("<li>").append(description).append(score)