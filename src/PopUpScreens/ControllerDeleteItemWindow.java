package PopUpScreens;

import Classes.API;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class ControllerDeleteItemWindow{

    private int idToDelete;
    private String table;

    @FXML
    private Label LabelItemToDelete;

    @FXML
    private Button btnDeleteItem;

    public void loadItemToDelete(String itemName, int id, String t) {
        LabelItemToDelete.setText(itemName);
        idToDelete = id;
        table = t;
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (actionEvent.getSource() == btnDeleteItem) {
            desactive();

            Stage primaryStage = (Stage) btnDeleteItem.getScene().getWindow();
            primaryStage.close();
        }
    }

    private void desactive() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HashMap<String, String> inputValues = new HashMap<>();
        inputValues.put("id", Integer.toString(idToDelete));
        inputValues.put("active", Integer.toString(0));

        HashMap<String, Object> inputData = new HashMap<>();
        inputData.put("table", table);
        inputData.put("values", inputValues);
        String inputJson = gson.toJson(inputData);

        var response = API.sendRequest(inputJson, "update");
    }

}
