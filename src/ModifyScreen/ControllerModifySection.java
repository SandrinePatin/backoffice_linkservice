package ModifyScreen;

import Classes.Section;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerModifySection {
    @FXML
    private TextField tfNameModifySection;
    @FXML
    private TextArea tfDescriptionModifySection;

    @FXML
    private Button btnModifySectionData;


    public void initialize(URL location, ResourceBundle resources){}

    public void loadSection(Section section) {
        tfNameModifySection.setText(section.getName());
        tfDescriptionModifySection.setText(section.getDescription());
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (actionEvent.getSource() == btnModifySectionData) {
            System.out.println(btnModifySectionData.getText());
        }
    }

}
