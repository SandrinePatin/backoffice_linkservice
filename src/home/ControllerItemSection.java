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

    public void updateItemSection(int id, String name, String description) {
        labelIdSection.setText(Integer.toString(id));
        labelNameSection.setText(name);
        labelDescriptionSection.setText(description);

        section = new Section(id, name, description);
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (actionEvent.getSource() == btnModifySection) {

            System.out.println(btnModifySection.getParent().getId());

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("../ModifyScreen/ModifySection.fxml")
            );

            Stage mainStage = new Stage();
            mainStage.setTitle("LSB: Modification d'une Section");
            mainStage.setScene(new Scene((Pane) loader.load()));
            ModifyScreen.ControllerModifySection controller = loader.getController();
            controller.loadSection(section);

            mainStage.show();
        }
    }


    public void initialize(URL location, ResourceBundle resources) {

    }
}
