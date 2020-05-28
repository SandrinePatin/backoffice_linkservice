package Classes;

public class User {
    private  int id;
    private String email;
    private String name;
    private String surname;
    private String type;

    public User(int idUser){
        id = idUser;
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

    public void getUserInfos(){

    }
}
