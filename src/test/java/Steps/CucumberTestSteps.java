package Steps;

import Modelo.Libro;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import org.example.App;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
public class CucumberTestSteps {
    Libro libro = null;
    List<Libro> libroList = new ArrayList<>();
    RepositorioLibro repositorioMock = Mockito.mock(RepositorioLibro.class);
    Exception error = null;
    private long idCounter = 1L;
    int statusCode;
    {
        Mockito.when(repositorioMock.save(Mockito.any(Libro.class))).thenAnswer(invocation -> {
            Libro libroToSave = invocation.getArgument(0);
            if (libroToSave.getId() == null) {
                libroToSave.setId(idCounter++);
            }
            libroList.add(libroToSave);
            return libroToSave;
        });

        Mockito.when(repositorioMock.findById(Mockito.anyLong())).thenAnswer(invocation -> {
            long id = invocation.getArgument(0);
            return libroList.stream().filter(l -> l.getId() != null && l.getId() == id).findFirst().map(Optional::of).orElse(Optional.empty());
        });
    }
    ServicioLibro servLibro = new ServicioLibroImpl(repositorioMock);
    @Given("un libro titulado Cinco Semanas en Globo por Julio Verne de genero Aventura")
    public void tituloCincoSemanasEnGlobo() {
        Libro libro = new Libro(0,"Cinco Semanas en Globo","Julio Verne","Aventura");
    }
    @Given("un libro titulado Don Quijote De La Mancha por Miguel de Cervantes de genero Comedia")
    public void tituloDonQuijoteDeLaMancha() {
        Libro libro = new Libro(0,"Don Quijote De La Mancha","Miguel de Cervantes","Comedia");
    }
    @Given("un libro titulado La Liga de los Pelirrojos por Sir Arthur Conan Doyle de genero Misterio")
    public void tituloLaLigaDeLosPelirrojos() {
        Libro libro = new Libro(0,"La Liga de los Pelirrojos","Sir Arthur Conan Doyle","Misterio");
    }
    @Given("un libro sin titulo por Miguel de Cervantes de genero Comedia")
    public void unLibroSinTitulo() {
        Libro libro = new Libro(0,"","Miguel de Cervantes","Comedia");
    }
    @Given("un libro titulado Comillas por Miguel de Cervantes de genero Comedia")
    public void unLibroTituladoComillas() {
        Libro libro = new Libro(0,"\"","Miguel de Cervantes","Comedia");
    }
    @Given("un id de libro vacio")
    public void idLibroVacio() {
        libro.setId(null);
    }
    @Given("un libro titulado Don Quijote De La Mancha por Miguel de Cervantes sin genero")
    public void sinGenero() {
        Libro libro = new Libro(0,"Don Quijote De La Mancha","Miguel de Cervantes","");
    }
    @Given("es el primer libro")
    public void primerLibro() {
        libro1=libro;
        App.guardarLibro(libro1);
    }
    @Given("es el segundo libro")
    public void segundoLibro() {
        libro2=libro;
        App.guardarLibro(libro2);
    }
    @Given("es el tercer libro")
    public void tercerLibro() {
        libro3=libro;
        App.guardarLibro(libro3);
    }
    @When("el libro existe en la base de datos")
    public void libroExisteEnBaseDeDatos() {
        App.guardarLibro(libro);
        assert libro.getId() != null : "El libro no tiene un ID asignado";
    }
    @When("el usuario hace un Post")
    public void usuarioHacePost() {
        App.guardarLibro(libro);
    }
    @When("el usuario hace un Get")
    public void usuarioHaceGet() {
        try {
            App.obtenerLibro(libro.getId());
        } catch (Exception e) {
            error = e;
        }
    }
    @When("el usuario hace un Put de autor Miguel de Cervantes Saavedra")
    public void usuarioHacePutAutor() {
        libro.setAuthor("Miguel de Cervantes Saavedra");
        App.libroAModificar(libro.getId(), libro);
    }
    @When("el usuario hace un Put de autor vacio")
    public void usuarioHacePutRangoVacio() {
        libro.setAuthor("");
        App.libroAModificar(libro.getId(), libro);
    }
    @When("el usuario hace un Delete")
    public void usuarioHaceDelete() {
        App.eliminarLibro(libro.getId());
    }
    @When("el usuario lista todos los libros empezando por {int} en paginas de {int}}")
    public void usuarioListaTodosLibros(int offset, int size) {
        App.obtenerTodosLibros(offset,size);
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