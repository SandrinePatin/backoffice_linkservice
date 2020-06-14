package Classes;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Ticket {
    private int id;
    private String date;
    private String description;
    private String userAssigned;
    private int userCreator;
    private int statut;

    public Ticket(int idTicket, String da, String de, int userC, String uA, int s) {
        id = idTicket;
        date = da.substring(0,10);
        description = de;
        statut = s;
        userCreator = userC;

        userAssigned = Objects.requireNonNullElse(uA, "No user");

    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getUserAssigned() {
        if (userAssigned == null) {
            return "No user";
        }
        return userAssigned;
    }

    public int getStatut() {
        return statut;
    }

    public void createTicket() throws IOException, InterruptedException {
        updateInDatabase("create");
    }

    public void updateTicket(String newDate, String newDescription, String newUserAssigned, String newStatus) throws IOException, InterruptedException {
        date = newDate.substring(0, 10);
        description = newDescription;
        userAssigned = newUserAssigned;
        statut = Integer.parseInt(newStatus);

        updateInDatabase("update");
    }

    public void closeTicket() throws IOException, InterruptedException {
        date = date.substring(0,10);
        statut = 1;
        updateInDatabase("update");
    }

    private void updateInDatabase(String action) throws IOException, InterruptedException {
        Gson gson = new Gson();
        HashMap<String, String> inputValues = new HashMap<>();
        if (action.equals("create") || action.equals("update")) {
            inputValues.put("date", date);
            inputValues.put("description", description);
            inputValues.put("statut", Integer.toString(statut));
            inputValues.put("id_user_creator", Integer.toString(userCreator));

            if(!userAssigned.equals("")){
                inputValues.put("id_user_assigned", userAssigned);
            }
        }

        if (action.equals("update")) {
            inputValues.put("id", Integer.toString(id));
        }

        HashMap<String, Object> inputData = new HashMap<>();
        inputData.put("table", "ticket");
        inputData.put("values", inputValues);
        String inputJson = gson.toJson(inputData);
        System.out.println(inputJson);
        var response = API.sendRequest(inputJson, action);
        System.out.println(response.body());
        //TODO: if API is disconnected
    }
}
