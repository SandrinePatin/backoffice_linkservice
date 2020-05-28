package Classes;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class API {

    public static HttpResponse<String> sendRequest(String jsonData, String action) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4000/"+action))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                .build();
        var client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
