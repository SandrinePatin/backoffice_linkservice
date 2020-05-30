package ModifyScreen;

import Classes.API;
import Classes.Section;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class ControllerDeleteItemWindow {
    Section actualSection;

    @FXML
    private Label LabelItemToDelete;

    @FXML
    private Button btnDeleteItem;

    public void loadItemToDelete(String itemName, Section section) {
        LabelItemToDelete.setText(itemName);
        actualSection = section;
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (actionEvent.getSource() == btnDeleteItem) {
            actualSection.deleteSection();

            Stage primaryStage = (Stage) btnDeleteItem.getScene().getWindow();
            primaryStage.close();
        }

    }
}
