package Classes;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

public class User {
    private  int id;
    private String email;
    private String name;
    private String surname;
    private String birthdate;
    private String adress;
    private String city;
    private String type;

    public User(int idUser){
        id = idUser;
    }

    public User(int idUser, String e, String p, String n, String s, String b, String a, String c, String t){
        id = idUser;
        email = e;
        name = n;
        surname = s;
        birthdate = b.substring(0,10);
        adress = a;
        city = c;
        type = t;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() { return name; }

    public String getSurname() { return surname; }

    public String getEmail() { return email; }

    public String getBirthdate() { return birthdate; }

    public String getAdress() { return adress; }

    public String getCity() { return city; }

    public void updateUser(String newName, String newSurname, String newBirthdate, String newAdress, String newCity) throws IOException, InterruptedException {
        name = newName;
        surname = newSurname;
        birthdate = newBirthdate.substring(0,10);
        adress = newAdress;
        city = newCity;

        updateInDatabase();
    }

    private void updateInDatabase() throws IOException, InterruptedException {
        Gson gson = new Gson ();

        HashMap<String, String> inputValues = new HashMap<>();
        inputValues.put("name",name);
        inputValues.put("surname",surname);
        inputValues.put("birthdate",birthdate);
        inputValues.put("adress",adress);
        inputValues.put("city",city);
        inputValues.put("id", Integer.toString(id));

        HashMap<String, Object> inputData = new HashMap<>();
        inputData.put("table","user");
        inputData.put("values", inputValues);
        String inputJson = gson.toJson(inputData);

        var response = API.sendRequest(inputJson, "update");

        //TODO: if API is disconnected
    }
}
