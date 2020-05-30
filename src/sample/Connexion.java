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
import java.util.HashMap;

import Classes.API;
import Classes.User;
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

    public User checkConnexion() throws IOException, InterruptedException {
        Gson gson = new Gson ();

        HashMap<String, String> inputValues = new HashMap<>();
        inputValues.put("email",email);
        inputValues.put("password",password);

        HashMap<String, Object> inputData = new HashMap<>();
        inputData.put("table","user");
        inputData.put("values", inputValues);
        String inputJson = gson.toJson(inputData);

        var response = API.sendRequest(inputJson, "connection");

        //TODO: if API is down

        if(response.body().equals("null")){
            return null;
        } else {
            return gson.fromJson(response.body(),User.class);
        }
    }

    public User getUserData(int id) throws IOException, InterruptedException {
        Gson gson = new Gson ();

        HashMap<String, String> inputValues = new HashMap<>();
        inputValues.put("id",Integer.toString(id));

        HashMap<String, Object> inputData = new HashMap<>();
        inputData.put("table","user");
        inputData.put("values", inputValues);
        String inputJson = gson.toJson(inputData);

        var response = API.sendRequest(inputJson, "readOne");
        if(response.body().equals("null")){
            return null;
        } else {
            return gson.fromJson(response.body(),User.class);
        }
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
