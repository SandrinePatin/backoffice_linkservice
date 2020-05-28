package Classes;

public class User {
    private final int id;
    private String email;
    private String name;
    private String surname;
    private String type;

    public User(int idUser){
        id = idUser;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void getUserInfos(){

    }
}
