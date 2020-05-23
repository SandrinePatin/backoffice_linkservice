package sample;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Connexion {
    private String email;
    private String password;

    public Connexion(String u, String p) throws NoSuchAlgorithmException {
        email = u;
        password = passwordHash(p);
    }

    public String getUsername() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int checkConnexion() throws IOException, InterruptedException {
        Gson gson = new Gson ();
        String api_url = "http://localhost:4000/connection";

        HashMap<String, String> inputValues = new HashMap<>();
        inputValues.put("email",email);
        inputValues.put("password",password);

        HashMap<String, Object> inputData = new HashMap<>();
        inputData.put("table","user");
        inputData.put("values", inputValues);
        String inputJson = gson.toJson(inputData);

        var request = HttpRequest.newBuilder()
                .uri(URI.create(api_url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .build();
        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
        if(response.body().equals("null")){
            return 0;
        } else {
            User user = gson.fromJson(response.body(),User.class);
            return user.getId();
        }
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

        System.out.println(retMap);

        System.out.println(retMap.get("0"));


        return true;
    }

    private String passwordHash(String stringToHash) throws NoSuchAlgorithmException {
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
