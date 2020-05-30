package Classes;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

public class Section {
    private int id;
    private String name;
    private String description;

    public Section(int idSection, String n, String d) {
        id = idSection;
        name = n;
        description = d;
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

    public void createSection() throws IOException, InterruptedException {
        updateInDatabase("create");
    }

    public void updateSection(String newName, String newDescription) throws IOException, InterruptedException {
        name = newName;
        description = newDescription;

        updateInDatabase("update");
    }

    private void updateInDatabase(String action) throws IOException, InterruptedException {
        Gson gson = new Gson();

        HashMap<String, String> inputValues = new HashMap<>();
        inputValues.put("name", name);
        inputValues.put("description", description);

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
