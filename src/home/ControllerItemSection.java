package home;

import Classes.Section;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerItemSection {
    private Section section;

    @FXML
    private Label labelIdSection;
    @FXML
    private Label labelNameSection;
    @FXML
    private Label labelDescriptionSection;

    @FXML
    private Button btnModifySection;
    @FXML
    private Button btnDeleteSection;

    public void updateItemSection(int id, String name, String description) {
        labelIdSection.setText(Integer.toString(id));
        labelNameSection.setText(name);
        labelDescriptionSection.setText(description);

        section = new Section(id, name, description);
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (actionEvent.getSource() == btnModifySection) {
            loadNewWindow("../ModifyScreen/ModifySection.fxml", "LSB: Modification d'une Section", "modify");
        }
        if (actionEvent.getSource() == btnDeleteSection){
            loadNewWindow("../ModifyScreen/DeleteItemWindow.fxml", "LSB: Supression d'une Section", "delete");
        }
    }

    public void loadNewWindow(String file, String title, String action) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(file)
        );

        Stage mainStage = new Stage();
        mainStage.setTitle(title);
        mainStage.setScene(new Scene((Pane) loader.load()));

        if(action.equals("modify")){
            ModifyScreen.ControllerModifySection controller = loader.getController();
            controller.loadSection(section);
        } else if (action.equals("delete")){
            ModifyScreen.ControllerDeleteItemWindow controller = loader.getController();
            controller.loadItemToDelete("Rubrique => " + section.getName(), section);
        }

        mainStage.show();
    }


    public void initialize(URL location, ResourceBundle resources) {

    }
}
