Feature: CucumberTest

  Scenario: Nuevo libro

    Given un libro titulado Cinco Semanas en Globo por Julio Verne de genero Aventura
    When el usuario hace un Post
    Then el libro es guardado

  Scenario: Actualizar libro

    Given un libro titulado Don Quijote De La Mancha por Miguel de Cervantes de genero Comedia
    When el libro existe en la base de datos
    And el usuario hace un Put de autor Miguel de Cervantes Saavedra
    Then el autor del libro es actualizado a Miguel de Cervantes Saavedra

  Scenario: Borrar libro

    Given un libro titulado La Liga de los Pelirrojos por Sir Arthur Conan Doyle de genero Misterio
    When el libro existe en la base de datos
    And el usuario hace un Delete
    Then el libro es eliminado

  Scenario: Libro con titulo vacio

    Given un libro sin titulo por Miguel de Cervantes de genero Comedia
    When el usuario hace un Post
    Then el libro es rechazado

  Scenario: Libro con genero vacio

    Given un libro titulado Don Quijote De La Mancha por Miguel de Cervantes sin genero
    When el usuario hace un Post
    Then el libro es rechazado

  Scenario: Libro con titulo invalido
    Given un libro titulado Comillas por Miguel de Cervantes de genero Comedia
    When el usuario hace un Post
    Then el libro es rechazado

  Scenario: No encuentra libro
    Given un id de libro vacio
    When el usuario hace un Get
    Then mensaje de error "Libro no encontrado"

  Scenario: Falla en actualizar libro por autor vacio
    Given un libro titulado Don Quijote De La Mancha por Miguel de Cervantes de genero Comedia
    When el libro existe en la base de datos
    And el usuario hace un Put de autor vacio
    Then la actualizacion es rechazada

  Scenario Outline: Listar todos los libros
    Given un libro titulado Don Quijote De La Mancha por Miguel de Cervantes de genero Comedia
    And es el primer libro
    And un libro titulado Cinco Semanas en Globo por Julio Verne de genero Aventura
    And es el segundo libro
    Given un libro titulado La Liga de los Pelirrojos por Sir Arthur Conan Doyle de genero Misterio
    And es el tercer libro
    When el libro existe en la base de datos
    And el usuario lista todos los libros empezando por <offset> en paginas de <size>
    Then muestra una lista de todos los libros

    Examples:
      | offset | size |
      | 0      | 100  |
      | 0      | 2    |
      | 1      | 2    |