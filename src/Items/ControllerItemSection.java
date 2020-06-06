package Items;

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
    private Label labelActiveSection;

    @FXML
    private Button btnModifySection;
    @FXML
    private Button btnDeleteSection;

    public void updateItemSection(Section s) {
        section = s;
        labelIdSection.setText(Integer.toString(s.getId()));
        labelNameSection.setText(s.getName());
        labelDescriptionSection.setText(s.getDescription());
        labelActiveSection.setText(Integer.toString(s.getActive()));
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (actionEvent.getSource() == btnModifySection) {
            loadNewWindow("../PopUpScreens/ModifySection.fxml", "LSB: Modification d'une Section", "modify");
        }
        if (actionEvent.getSource() == btnDeleteSection){
            loadNewWindow("../PopUpScreens/DeleteItemWindow.fxml", "LSB: Supression d'une Section", "delete");
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
            PopUpScreens.ControllerModifySection controller = loader.getController();
            controller.loadSection(section);
        } else if (action.equals("delete")){
            PopUpScreens.ControllerDeleteItemWindow controller = loader.getController();
            controller.loadItemToDelete("Rubrique => " + section.getName(), section.getId(), "section");
        }

        mainStage.show();
    }


    public void initialize(URL location, ResourceBundle resources) {

    }
}
