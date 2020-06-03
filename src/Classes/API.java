package Classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

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

    public static HashMap<String, Section> decodeResponseMultipleAsSection (HttpResponse<String> response){

        HashMap<String, Section> retMap = new Gson().fromJson(
                response.body(), new TypeToken<HashMap<String, Section>>() {}.getType()
        );
        return retMap;
    }

    public static HashMap<String, TypeService> decodeResponseMultipleAsTypeService (HttpResponse<String> response){

        HashMap<String, TypeService> retMap = new Gson().fromJson(
                response.body(), new TypeToken<HashMap<String, TypeService>>() {}.getType()
        );
        return retMap;
    }

    public static HashMap<String, User> decodeResponseMultipleAsUser (HttpResponse<String> response){

        HashMap<String, User> retMap = new Gson().fromJson(
                response.body(), new TypeToken<HashMap<String, User>>() {}.getType()
        );
        return retMap;
    }

    private boolean testReadAll() throws IOException, InterruptedException {
        String api_url = "http://localhost:4000/readAll";
        Gson gson = new Gson ();

        HashMap<String, Object> inputData = new HashMap<>();
        inputData.put("table","section");
        String inputJson = gson.toJson(inputData);

        var request = HttpRequest.newBuilder()
                .uri(URI.create(api_url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .build();
        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Json de sorti (type string) :\n" + response.body());

        HashMap<String, Object> retMap = new Gson().fromJson(
                response.body(), new TypeToken<HashMap<String, Object>>() {}.getType()
        );

        System.out.println(retMap.get("0"));

        return true;
    }

}
