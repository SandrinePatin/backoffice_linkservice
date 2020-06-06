package Classes;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

public class Section {
    private final int id;
    private String name;
    private String description;
    private int active;

    public Section(int idSection, String n, String d, int a) {
        id = idSection;
        name = n;
        description = d;
        active = a;
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

    public int getActive() { return active; }

    public void createSection() throws IOException, InterruptedException {
        updateInDatabase("create");
    }

    public void updateSection(String newName, String newDescription, int newStatusActive) throws IOException, InterruptedException {
        name = newName;
        description = newDescription;
        active = newStatusActive;

        updateInDatabase("update");
    }

    private void updateInDatabase(String action) throws IOException, InterruptedException {
        Gson gson = new Gson();
        HashMap<String, String> inputValues = new HashMap<>();
        if(action.equals("create") || action.equals("update")){
            inputValues.put("name", name);
            inputValues.put("description", description);
            inputValues.put("active", Integer.toString(active));
        }

        if (action.equals("update")) {
            inputValues.put("id", Integer.toString(id));
        }

        HashMap<String, Object> inputData = new HashMap<>();
        inputData.put("table", "section");
        inputData.put("values", inputValues);
        String inputJson = gson.toJson(inputData);

        var response = API.sendRequest(inputJson, action);

        //TODO: if API is disconnected
    }
}
