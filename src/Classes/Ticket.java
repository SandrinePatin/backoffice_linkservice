package Classes;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Ticket {
    private int id;
    private String date;
    private String description;
    private String id_user_assigned;
    private int id_user_creator;
    private int statut;

    public Ticket(int idTicket, String da, String de, int userC, String uA, int s) {
        id = idTicket;
        date = da.substring(0,10);
        description = de;
        statut = s;
        id_user_creator = userC;

        id_user_assigned = Objects.requireNonNullElse(uA, "No user");

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

    public String getId_user_assigned() {
        if (id_user_assigned == null) {
            return "No user";
        }
        return id_user_assigned;
    }

    public int getStatut() {
        return statut;
    }

    public void createTicket() throws IOException, InterruptedException {
        updateInDatabase("create");
    }

    public void updateTicket(String newDescription, String newUserAssigned, String newStatus) throws IOException, InterruptedException {
        date = date.substring(0, 10);
        description = newDescription;
        id_user_assigned = newUserAssigned;
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
            inputValues.put("id_user_creator", Integer.toString(id_user_creator));

            if(id_user_assigned != null && !id_user_assigned.equals("")){
                inputValues.put("id_user_assigned", id_user_assigned);
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
    }
}
