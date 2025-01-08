package Steps;

import Modelo.Libro;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;

@CucumberContextConfiguration
public class CucumberTestSteps {

    ObjectMapper objectMapper = new ObjectMapper();
    Libro libro;
    Libro libro1;
    Libro libro2;
    Libro libro3;
    int codigo;
    HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    @Given("un libro titulado Cinco Semanas en Globo por Julio Verne de genero Aventura")
    public void tituloCincoSemanasEnGlobo() {
        libro = new Libro(0,"Cinco_Semanas_en_Globo","Julio_Verne","Aventura");
    }
    @Given("un libro titulado Don Quijote De La Mancha por Miguel de Cervantes de genero Comedia")
    public void tituloDonQuijoteDeLaMancha() {
        libro = new Libro(0,"Don_Quijote_De_La_Mancha","Miguel_de_Cervantes","Comedia");
    }
    @Given("un libro titulado La Liga de los Pelirrojos por Sir Arthur Conan Doyle de genero Misterio")
    public void tituloLaLigaDeLosPelirrojos() {
        libro = new Libro(0,"La_Liga_de_los_Pelirrojos","Sir_Arthur_Conan_Doyle","Misterio");
    }
    @Given("un libro sin titulo por Miguel de Cervantes de genero Comedia")
    public void unLibroSinTitulo() {
        libro = new Libro(0,"","Miguel_de_Cervantes","Comedia");
    }
    @Given("un libro titulado Comillas por Miguel de Cervantes de genero Comedia")
    public void unLibroTituladoComillas() {
        libro = new Libro(0,"\"","Miguel_de_Cervantes","Comedia");
    }
    @Given("un libro que no existe")
    public void libroNoExiste() {
        libro = null;
    }
    @Given("un libro titulado Don Quijote De La Mancha por Miguel de Cervantes sin genero")
    public void sinGenero() {
        Libro libro = new Libro(0,"Don_Quijote_De_La_Mancha","Miguel_de_Cervantes","");
    }
    @Given("es el primer libro")
    public void primerLibro() {
        libro1 = new Libro(libro.id(), libro.title(), libro.author(), libro.genre());
        try {
            String json = objectMapper.writeValueAsString(libro1);
            HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8081/api/books"))
                    .header("Content-Type", "application/json")
                    .header("accept", "*/*")
                    .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ConnectionError) {
            fail(ConnectionError.getMessage());
        }
    }
    @Given("es el segundo libro")
    public void segundoLibro() {
        libro2 = new Libro(libro.id(), libro.title(), libro.author(), libro.genre());
        try {
            String json = objectMapper.writeValueAsString(libro2);
            HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8081/api/books"))
                    .header("Content-Type", "application/json")
                    .header("accept", "*/*")
                    .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ConnectionError) {
            fail(ConnectionError.getMessage());
        }
    }
    @Given("es el tercer libro")
    public void tercerLibro() {
        libro3 = new Libro(libro.id(), libro.title(), libro.author(), libro.genre());
        try {
            String json = objectMapper.writeValueAsString(libro3);
            HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8081/api/books"))
                    .header("Content-Type", "application/json")
                    .header("accept", "*/*")
                    .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ConnectionError) {
            fail(ConnectionError.getMessage());
        }
    }
    @When("el libro existe en la base de datos")
    public void libroExisteEnBaseDeDatos() {
        try {
            String json = objectMapper.writeValueAsString(libro);
            HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8081/api/books"))
                    .header("Content-Type", "application/json")
                    .header("accept", "*/*")
                    .GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assert libro.id() != null : "El libro no tiene un ID asignado";
        } catch (Exception ConnectionError) {
            fail(ConnectionError.getMessage());
        }
    }
    @When("el usuario hace un Post")
    public void usuarioHacePost() {
        try {
            String json = objectMapper.writeValueAsString(libro);
            HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8081/api/books"))
                    .header("Content-Type", "application/json")
                    .header("accept", "*/*")
                    .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            codigo = response.statusCode();
        } catch (Exception ConnectionError) {
            fail(ConnectionError.getMessage());
        }
    }
    @When("el usuario hace un Get")
    public void usuarioHaceGet() {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8081/api/books"))
                    .header("Content-Type", "application/json")
                    .header("accept", "*/*")
                    .GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assert libro.id() != null : "El libro no tiene un ID asignado";
        } catch (Exception ConnectionError) {
            fail(ConnectionError.getMessage());
        }
    }
    @When("el usuario hace un Put de autor Miguel de Cervantes Saavedra")
    public void usuarioHacePutAutor() {
        Libro libroModificado = new Libro(0,"Don_Quijote_De_La_Mancha","Miguel_de_Cervantes_Saavedra","Comedia");
        try {
            String json = objectMapper.writeValueAsString(libroModificado);
            HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8081/api/books"))
                    .header("Content-Type", "application/json")
                    .header("accept", "*/*")
                    .PUT(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            codigo = response.statusCode();
        } catch (Exception ConnectionError) {
            fail(ConnectionError.getMessage());
        }
    }
    @When("el usuario hace un Put de autor vacio")
    public void usuarioHacePutAutorVacio() {
        Libro libroModificado = new Libro(0,"Don_Quijote_De_La_Mancha","","Comedia");
        try {
            String json = objectMapper.writeValueAsString(libroModificado);
            HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8081/api/books"))
                    .header("Content-Type", "application/json")
                    .header("accept", "*/*")
                    .PUT(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            codigo = response.statusCode();
        } catch (Exception ConnectionError) {
            fail(ConnectionError.getMessage());
        }
    }
    @When("el usuario hace un Delete")
    public void usuarioHaceDelete() {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8081/api/books"))
                    .header("Content-Type", "application/json")
                    .header("accept", "*/*")
                    .DELETE().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            codigo = response.statusCode();
        } catch (Exception ConnectionError) {
            fail(ConnectionError.getMessage());
        }
    }
    @When("el usuario lista todos los libros empezando por {int} en paginas de {int}")
    public void usuarioListaTodosLibros(int offset, int size) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8081/api/books"))
                    .header("Content-Type", "application/json")
                    .header("accept", "*/*")
                    .GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ConnectionError) {
            fail(ConnectionError.getMessage());        }
    }
    @Then("el libro es guardado")
    public void libroGuardado() {
        try {
            assert codigo == 201 : "El titulo del libro no coincide";
        } catch (Exception ConnectionError) {
            fail(ConnectionError.getMessage());
        }
    }
    @Then("el autor del libro es actualizado a Miguel de Cervantes Saavedra")
    public void libroActualizado() {
        try {
            assert codigo == 200 : "El autor del libro no coincide";
        } catch (Exception ConnectionError) {
            fail(ConnectionError.getMessage());
        }
    }
    @Then("el libro es eliminado")
    public void libroEliminado() {
        assert codigo == 200 : "Fallo en eliminar el libro";
    }
    @Then("el libro es rechazado")
    public void libroRechazado() {
        assert codigo == 400 : "Fallo en rechazar el libro";
    }
    @Then("libro no encontrado")
    public void libroNoEncontrado() {
        assert codigo == 404 : "Libro encontrado";
    }
    @Then("la actualizacion es rechazada")
    public void actualizacionRechazada() {
        try {
            assert codigo == 400 : "Actualizacion no rechazada";
        } catch (Exception ConnectionError) {
            fail(ConnectionError.getMessage());
        }
    }
    @Then("muestra una lista de todos los libros")
    public void muestraListaTodosLibros() {
        try {
            assert codigo == 200 : "Libros no encontrados";
        } catch (Exception ConnectionError) {
            fail(ConnectionError.getMessage());
        }
    }
}