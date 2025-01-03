package Steps;

import Modelo.Libro;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

@CucumberContextConfiguration
public class CucumberTestSteps {
    Libro libro;
    Exception error = null;

    HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    @Given("un libro titulado Cinco Semanas en Globo por Julio Verne de genero Aventura")
    public void tituloCincoSemanasEnGlobo() {
        libro = new Libro(0,"Cinco Semanas en Globo","Julio Verne","Aventura");
    }
    @Given("un libro titulado Don Quijote De La Mancha por Miguel de Cervantes de genero Comedia")
    public void tituloDonQuijoteDeLaMancha() {
        libro = new Libro(0,"Don Quijote De La Mancha","Miguel de Cervantes","Comedia");
    }
    @Given("un libro titulado La Liga de los Pelirrojos por Sir Arthur Conan Doyle de genero Misterio")
    public void tituloLaLigaDeLosPelirrojos() {
        libro = new Libro(0,"La Liga de los Pelirrojos","Sir Arthur Conan Doyle","Misterio");
    }
    @Given("un libro sin titulo por Miguel de Cervantes de genero Comedia")
    public void unLibroSinTitulo() {
        libro = new Libro(0,"","Miguel de Cervantes","Comedia");
    }
    @Given("un libro titulado Comillas por Miguel de Cervantes de genero Comedia")
    public void unLibroTituladoComillas() {
        libro = new Libro(0,"\"","Miguel de Cervantes","Comedia");
    }
    @Given("un id de libro vacio")
    public void idLibroVacio() {
        try {
            String url=String.format("localhost:8081/obtenerLibro?id=%d", libro.id());
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder(new URI(url))
                            .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                            .GET().build(),HttpResponse.BodyHandlers.ofString());
            assert libro.getId() == null : "Libro encontrado";
        } catch (Exception e) {
            error = e;
        }
    }
    @Given("un libro titulado Don Quijote De La Mancha por Miguel de Cervantes sin genero")
    public void sinGenero() {
        Libro libro = new Libro(0,"Don Quijote De La Mancha","Miguel de Cervantes","");
    }
    @Given("es el primer libro")
    public void primerLibro() {
        Libro libro1 = new Libro(libro.id, libro.title, libro.author, libro.genre);
        String url=String.format(
                "localhost:8081/guardarLibro?title=%s&author=%s&genre=%s",
                txtTitle.GetText(),
                txtAuthor.GetText(),
                txtGenre.GetText()
        );
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(new URI(url))
                        .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                        .GET().build(),HttpResponse.BodyHandlers.ofString());
    }
    @Given("es el segundo libro")
    public void segundoLibro() {
        Libro libro2 = new Libro(libro.id, libro.title, libro.author, libro.genre);
        String url=String.format(
                "localhost:8081/guardarLibro?title=%s&author=%s&genre=%s",
                txtTitle.GetText(),
                txtAuthor.GetText(),
                txtGenre.GetText()
        );
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(new URI(url))
                        .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                        .GET().build(),HttpResponse.BodyHandlers.ofString());
    }
    @Given("es el tercer libro")
    public void tercerLibro() {
        Libro libro3 = new Libro(libro.id, libro.title, libro.author, libro.genre);
        String url=String.format(
                "localhost:8081/guardarLibro?title=%s&author=%s&genre=%s",
                txtTitle.GetText(),
                txtAuthor.GetText(),
                txtGenre.GetText()
        );
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(new URI(url))
                        .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                        .GET().build(),HttpResponse.BodyHandlers.ofString());
    }
    @When("el libro existe en la base de datos")
    public void libroExisteEnBaseDeDatos() {
        String url=String.format("localhost:8081/obtenerLibro?id=%d", libro.id());
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(new URI(url))
                        .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                        .GET().build(),HttpResponse.BodyHandlers.ofString());
        assert libro.getId() != null : "El libro no tiene un ID asignado";
    }
    @When("el usuario hace un Post")
    public void usuarioHacePost() {
        String url=String.format(
                "localhost:8081/guardarLibro?title=%s&author=%s&genre=%s",
                txtTitle.GetText(),
                txtAuthor.GetText(),
                txtGenre.GetText()
        );
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(new URI(url))
                        .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                        .GET().build(),HttpResponse.BodyHandlers.ofString());
    }
    @When("el usuario hace un Get")
    public void usuarioHaceGet() {
        try {
            String url=String.format("localhost:8081/obtenerLibro?id=%d", libro.id());
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder(new URI(url))
                            .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                            .GET().build(),HttpResponse.BodyHandlers.ofString());
            assert libro.getId() != null : "El libro no tiene un ID asignado";
        } catch (Exception e) {
            error = e;
        }
    }
    @When("el usuario hace un Put de autor Miguel de Cervantes Saavedra")
    public void usuarioHacePutAutor() {
        libro.setAuthor("Miguel de Cervantes Saavedra");
        String url=String.format(
                "localhost:8081/libroAModificar?title=%s&author=%s&genre=%s",
                txtTitle.GetText(),
                txtAuthor.GetText(),
                txtGenre.GetText()
        );
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(new URI(url))
                        .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                        .GET().build(),HttpResponse.BodyHandlers.ofString());
    }
    @When("el usuario hace un Put de autor vacio")
    public void usuarioHacePutAutorVacio() {
        libro.setAuthor("");
        String url=String.format(
                "localhost:8081/libroAModificar?title=%s&author=%s&genre=%s",
                txtTitle.GetText(),
                txtAuthor.GetText(),
                txtGenre.GetText()
        );
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(new URI(url))
                        .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                        .GET().build(),HttpResponse.BodyHandlers.ofString());
    }
    @When("el usuario hace un Delete")
    public void usuarioHaceDelete() {
        String url=String.format("localhost:8081/eliminarLibro?id=%d", libro.id());
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(new URI(url))
                        .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                        .GET().build(),HttpResponse.BodyHandlers.ofString());
    }
    @When("el usuario lista todos los libros empezando por {int} en paginas de {int}}")
    public void usuarioListaTodosLibros(int offset, int size) {
        String url=String.format("localhost:8081/obtenerTodosLibros");
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(new URI(url))
                        .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                        .GET().build(),HttpResponse.BodyHandlers.ofString());
    }
    @Then("el libro es guardado")
    public void libroGuardado() {
        assert libro.getId() != null : "El libro no tiene un ID asignado";
        Optional<Libro> libroGuardado = App.obtenerLibro(libro.getId());
        assert libroGuardado.isPresent() : "Libro no encontrado en el repositorio";
        assert libroGuardado.get().getTitle().equals(libro.getTitle()) : "Titulo del libro no coincide";
    }
    @Then("el autor del libro es actualizado a Miguel de Cervantes Saavedra")
    public void libroActualizado() {
        assert libro.getId() != null : "El Libro no tiene un ID asignado";
        Optional<Libro> libroActualizado = App.obtenerLibro(libro.getId());
        assert libroActualizado.isPresent() : "Libro no encontrado en el repositorio";
        assert libroActualizado.get().getAuthor().equals("Miguel de Cervantes Saavedra") : "El autor del libro no coincide";
    }
    @Then("el libro es eliminado")
    public void libroEliminado() {
        assert libro.getId() == null : "El Libro no pudo ser eliminado";
    }
    @Then("el libro es rechazado")
    public void libroRechazado() {
        assert libro.getId() == null : "El Libro no pudo ser eliminado";
    }
    @Then("mensaje de error {string}")
    public void libroNoEncontrado(String mensajeEsperado) {
        if (error == null) {
            fail("Libro encontrado");
        }
        assertEquals(mensajeEsperado, error.getMessage());
    }
    @Then("la actualizacion es rechazada")
    public void ascensoRechazado() {
        assert libro.getId() != null : "El Libro no tiene un ID asignado";
        Optional<Libro> libroActualizado = App.obtenerLibro(libro.getId());
        assert libroActualizado.isPresent() : "Libro no encontrado en el repositorio";
        assert libroActualizado.get().getAuthor().equals("Miguel de Cervantes") : "El autor del libro no coincide";
    }
    @Then("muestra una lista de todos los libros")
    public void muestraListaTodosLibros() {
        assert libro1.getId() != null : "El Libro no tiene un ID asignado";
        Libro libroGuardado1 = App.obtenerLibro(libro1.getId());
        assert libroGuardado1.isPresent() : "Libro no encontrado en el repositorio";
        assert libroGuardado1.get().getTitle().equals(libro1.getTitle()) : "Titulo del libro no coincide";
        assert libro2.getId() != null : "El libro no tiene un ID asignado";
        Libro libroGuardado2 = App.obtenerLibro(libro2.getId());
        assert libroGuardado2.isPresent() : "Libro no encontrado en el repositorio";
        assert libroGuardado2.get().getTitle().equals(libro2.getTitle()) : "Titulo del libro no coincide";
        assert libro3.getId() != null : "El libro no tiene un ID asignado";
        Libro libroGuardado3 = App.obtenerLibro(libro3.getId());
        assert libroGuardado3.isPresent() : "Libro no encontrado en el repositorio";
        assert libroGuardado3.get().getTitle().equals(libro3.getTitle()) : "Titulo del libro no coincide";
    }
}