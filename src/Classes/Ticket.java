package Classes;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

public class Ticket {
    private int id;
    private String date;
    private String description;
    private int statut;

    public Ticket (int idTicket, String da, String de, int s) {
        id = idTicket;
        date = da;
        description = de;
        statut = s;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() { return description; }

    public int getStatut() { return statut; }

    public void createTicket() throws IOException, InterruptedException {
        updateInDatabase("create");
    }

    public void updateTicket(String newDate, String newDescription, int newStatus) throws IOException, InterruptedException {
        date = newDate;
        description = newDescription;
        statut = newStatus;

        updateInDatabase("update");
    }

    private void updateInDatabase(String action) throws IOException, InterruptedException {
        Gson gson = new Gson();
        HashMap<String, String> inputValues = new HashMap<>();
        if(action.equals("create") || action.equals("update")){
            inputValues.put("date", date);
            inputValues.put("description", description);
            inputValues.put("statut", Integer.toString(statut));
        }

        if (action.equals("update")) {
            inputValues.put("id", Integer.toString(id));
        }

        HashMap<String, Object> inputData = new HashMap<>();
        inputData.put("table", "ticket");
        inputData.put("values", inputValues);
        String inputJson = gson.toJson(inputData);

        var response = API.sendRequest(inputJson, action);

        //TODO: if API is disconnected
    }
}
