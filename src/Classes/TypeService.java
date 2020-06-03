package Classes;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

public class TypeService {
    private int id;
    private String name;
    private String description;
    private String picture;

    public TypeService(int idSection, String n, String d, String i) {
        id = idSection;
        name = n;
        description = d;
        picture = i;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() { return picture; }

    public void createType() throws IOException, InterruptedException {

        updateInDatabase("create");
    }

    public void updateType(String newName, String newDescription, String newPicture) throws IOException, InterruptedException {
        name = newName;
        description = newDescription;
        picture = newPicture;

        updateInDatabase("update");
    }

    public void deleteType() throws IOException, InterruptedException {
        updateInDatabase("deleteOne");

    }

    private void updateInDatabase(String action) throws IOException, InterruptedException {
        Gson gson = new Gson();
        HashMap<String, String> inputValues = new HashMap<>();
        if(action.equals("create") || action.equals("update")){
            inputValues.put("name", name);
            inputValues.put("description", description);
            inputValues.put("picture", picture);
        }

        if (action.equals("update") || action.equals("delete")) {
            inputValues.put("id", Integer.toString(id));
        }

        HashMap<String, Object> inputData = new HashMap<>();
        inputData.put("table", "type_service");
        inputData.put("values", inputValues);
        String inputJson = gson.toJson(inputData);

        var response = API.sendRequest(inputJson, action);
        //TODO: if API is disconnected
    }
}
