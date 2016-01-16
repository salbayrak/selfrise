$ ->
  $.get "/persons", (persons) ->
    $.each persons, (index, person) ->
      name = $("<div>").addClass("name").text person.name
      surname = $("<div>").addClass("surname").text person.surname
      age = $("<div>").addClass("age").text person.age
      $("#persons").append $("<li>").append(name).append(surname).append(age)