package Steps;

import Modelo.Libro;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    Libro libro1;
    Libro libro2;
    Libro libro3;
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
    @Given("un libro que no existe")
    public void libroNoExiste() {
        libro = null;
    }
    @Given("un libro titulado Don Quijote De La Mancha por Miguel de Cervantes sin genero")
    public void sinGenero() {
        Libro libro = new Libro(0,"Don Quijote De La Mancha","Miguel de Cervantes","");
    }
    @Given("es el primer libro")
    public void primerLibro() {
        libro1 = new Libro(libro.id(), libro.title(), libro.author(), libro.genre());
        try {
            String url=String.format("http://localhost:8081/guardarLibro?title=%s&author=%s&genre=%s", libro1.title(), libro1.author(), libro1.genre());
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder(new URI(url))
                            .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                            .GET().build(),HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            error = e;
        }
    }
    @Given("es el segundo libro")
    public void segundoLibro() {
        libro2 = new Libro(libro.id(), libro.title(), libro.author(), libro.genre());
        try {
            String url=String.format("http://localhost:8081/guardarLibro?title=%s&author=%s&genre=%s", libro2.title(), libro2.author(), libro2.genre());
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder(new URI(url))
                            .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                            .GET().build(),HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            error = e;
        }
    }
    @Given("es el tercer libro")
    public void tercerLibro() {
        libro3 = new Libro(libro.id(), libro.title(), libro.author(), libro.genre());
        try {
            String url=String.format("http://localhost:8081/guardarLibro?title=%s&author=%s&genre=%s", libro3.title(), libro3.author(), libro3.genre());
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder(new URI(url))
                            .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                            .GET().build(),HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            error = e;
        }
    }
    @When("el libro existe en la base de datos")
    public void libroExisteEnBaseDeDatos() {
        try {
            String url=String.format("http://localhost:8081/obtenerLibro?id=%d", libro.id());
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder(new URI(url))
                            .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                            .GET().build(),HttpResponse.BodyHandlers.ofString());
            assert libro.id() != null : "El libro no tiene un ID asignado";
        } catch (Exception e) {
            error = e;
        }
    }
    @When("el usuario hace un Post")
    public void usuarioHacePost() {
        try {
            String url=String.format("http://localhost:8081/guardarLibro?title=%s&author=%s&genre=%s", libro.title(), libro.author(), libro.genre());
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder(new URI(url))
                            .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                            .GET().build(),HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            error = e;
        }
    }
    @When("el usuario hace un Get")
    public void usuarioHaceGet() {
        try {
            String url=String.format("http://localhost:8081/obtenerLibro?id=%d", libro.id());
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder(new URI(url))
                            .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                            .GET().build(),HttpResponse.BodyHandlers.ofString());
            assert libro.id() != null : "El libro no tiene un ID asignado";
        } catch (Exception e) {
            error = e;
        }
    }
    @When("el usuario hace un Put de autor Miguel de Cervantes Saavedra")
    public void usuarioHacePutAutor() {
        Libro libroModificado = new Libro(0,"Don Quijote De La Mancha","Miguel de Cervantes Saavedra","Comedia");
        try {
            String url=String.format("http://localhost:8081/libroAModificar?title=%s&author=%s&genre=%s", libroModificado.title(), libroModificado.author(), libroModificado.genre());
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder(new URI(url))
                            .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                            .GET().build(),HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            error = e;
        }
    }
    @When("el usuario hace un Put de autor vacio")
    public void usuarioHacePutAutorVacio() {
        Libro libroModificado = new Libro(0,"Don Quijote De La Mancha","","Comedia");
        try {
            String url=String.format("http://localhost:8081/libroAModificar?title=%s&author=%s&genre=%s", libroModificado.title(), libroModificado.author(), libroModificado.genre());
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder(new URI(url))
                            .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                            .GET().build(),HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            error = e;
        }
    }
    @When("el usuario hace un Delete")
    public void usuarioHaceDelete() {
        try {
            String url=String.format("http://localhost:8081/eliminarLibro?id=%d", libro.id());
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder(new URI(url))
                            .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                            .GET().build(),HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            error = e;
        }
    }
    @When("el usuario lista todos los libros empezando por {int} en paginas de {int}")
    public void usuarioListaTodosLibros(int offset, int size) {
        try {
            String url=String.format("http://localhost:8081/obtenerTodosLibros");
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder(new URI(url))
                            .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                            .GET().build(),HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            error = e;
        }
    }
    @Then("el libro es guardado")
    public void libroGuardado() {
        assert libro.id() != null : "El libro no tiene un ID asignado";
        try {
            String url=String.format("http://localhost:8081/obtenerLibro");
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder(new URI(url))
                            .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                            .GET().build(),HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            error = e;
        }
    }
    @Then("el autor del libro es actualizado a Miguel de Cervantes Saavedra")
    public void libroActualizado() {
        assert libro.id() != null : "El Libro no tiene un ID asignado";
        try {
            String url=String.format("http://localhost:8081/obtenerLibro");
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder(new URI(url))
                            .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                            .GET().build(),HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            error = e;
        }
    }
    @Then("el libro es eliminado")
    public void libroEliminado() {
        assert libro.id() == null : "El Libro no pudo ser eliminado";
    }
    @Then("el libro es rechazado")
    public void libroRechazado() {
        assert libro.id() == null : "El Libro no pudo ser eliminado";
    }
    @Then("mensaje de error {string}")
    public void libroNoEncontrado(String mensajeEsperado) {
        if (error == null) {
            fail("Libro encontrado");
        }
        assertNotEquals(null, error);
    }
    @Then("la actualizacion es rechazada")
    public void actualizacionRechazada() {
        assert libro.id() != null : "El Libro no tiene un ID asignado";
        try {
            String url=String.format("http://localhost:8081/obtenerLibro");
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder(new URI(url))
                            .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                            .GET().build(),HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            error = e;
        }
    }
    @Then("muestra una lista de todos los libros")
    public void muestraListaTodosLibros() {
        assert libro1.id() != null : "El Libro no tiene un ID asignado";
        assert libro2.id() != null : "El libro no tiene un ID asignado";
        assert libro3.id() != null : "El libro no tiene un ID asignado";
        try {
            String url=String.format("http://localhost:8081/obtenerTodosLibros");
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder(new URI(url))
                            .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                            .GET().build(),HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            error = e;
        }
    }
}