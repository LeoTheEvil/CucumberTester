package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class App {
    public static void guardarLibro(String[] args) throws Exception {
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        String url=String.format(
                "localhost:8081/guardarLibro?title=%s&author=%s&genre=%s",
                txtTitle.GetText(),
                txtAuthor.GetText(),
                txtGenre.GetText()
        );

        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(
                                new URI(url))
                        .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                        .GET().build(),HttpResponse.BodyHandlers.ofString());

        System.out.println("Status code: " + response.statusCode());
        System.out.println("Headers:");
        response.headers().map().entrySet().forEach(System.out::println);
        System.out.println("Body:");
        System.out.println(response.body());
    }
    public static void obtenerLibro(int idLibro) throws Exception {
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        String url=String.format("localhost:8081/obtenerLibro?id=%d", idLibro);

        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(
                                new URI(url))
                        .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                        .GET().build(),HttpResponse.BodyHandlers.ofString());

        System.out.println("Status code: " + response.statusCode());
        System.out.println("Headers:");
        response.headers().map().entrySet().forEach(System.out::println);
        System.out.println("Body:");
        System.out.println(response.body());
    }
    public static void obtenerTodosLibros() throws Exception {
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        String url=String.format("localhost:8081/obtenerTodosLibros");

        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(
                                new URI(url))
                        .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                        .GET().build(),HttpResponse.BodyHandlers.ofString());

        System.out.println("Status code: " + response.statusCode());
        System.out.println("Headers:");
        response.headers().map().entrySet().forEach(System.out::println);
        System.out.println("Body:");
        System.out.println(response.body());
    }
    public static void libroAModificar(String[] args) throws Exception {
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        String url=String.format(
                "localhost:8081/libroAModificar?title=%s&author=%s&genre=%s",
                txtTitle.GetText(),
                txtAuthor.GetText(),
                txtGenre.GetText()
        );

        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(
                                new URI(url))
                        .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                        .GET().build(),HttpResponse.BodyHandlers.ofString());

        System.out.println("Status code: " + response.statusCode());
        System.out.println("Headers:");
        response.headers().map().entrySet().forEach(System.out::println);
        System.out.println("Body:");
        System.out.println(response.body());
    }
    public static void eliminarLibro(int idLibro) throws Exception {
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        String url=String.format("localhost:8081/eliminarLibro?id=%d", idLibro);

        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(
                                new URI(url))
                        .headers("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0)")
                        .GET().build(),HttpResponse.BodyHandlers.ofString());

        System.out.println("Status code: " + response.statusCode());
        System.out.println("Headers:");
        response.headers().map().entrySet().forEach(System.out::println);
        System.out.println("Body:");
        System.out.println(response.body());
    }
}