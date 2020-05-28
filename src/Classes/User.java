package Classes;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

public class User {
    private  int id;
    private String email;
    private String name;
    private String surname;
    private String type;

    public User(int idUser){
        id = idUser;
    }

    public User(int idUser, String e, String p, String n, String s, String t){
        id = idUser;
        email = e;
        name = n;
        surname = s;
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

}
