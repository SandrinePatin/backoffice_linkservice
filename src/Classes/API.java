package Classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public static HashMap<String, Service> decodeResponseMultipleAsService(HttpResponse<String> response) {
        HashMap<String, Service> retMap = new Gson().fromJson(
                response.body(), new TypeToken<HashMap<String, Service>>() {}.getType()
        );
        return retMap;
    }

    public static HashMap<String, Ticket> decodeResponseMultipleAsTicket(HttpResponse<String> response) {
        HashMap<String, Ticket> retMap = new Gson().fromJson(
                response.body(), new TypeToken<HashMap<String, Ticket>>() {}.getType()
        );
        return retMap;
    }

    public static String passwordHash(String stringToHash) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(stringToHash.getBytes(StandardCharsets.UTF_8));
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

}
