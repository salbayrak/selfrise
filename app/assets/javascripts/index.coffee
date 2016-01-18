$ ->
  $.get "/persons", (persons) ->
    $.each persons, (index, person) ->
      name = $("<div>").addClass("name").text person.name
      surname = $("<div>").addClass("surname").text person.surname
      role = $("<div>").addClass("role").text person.role
      age = $("<div>").addClass("age").text person.age
      $("#persons").append $("<li>").append(name).append(surname).append(role).append(age)