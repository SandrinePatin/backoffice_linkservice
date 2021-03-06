package PopUpScreens;

import Classes.Section;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerModifySection {
    Section actualSection;

    @FXML
    private TextField tfNameModifySection;
    @FXML
    private TextArea tfDescriptionModifySection;
    @FXML
    private CheckBox tfActiveModifySection1;

    @FXML
    private Button btnModifySectionData;
    @FXML
    private Button btnCreateSectionData;


    public void initialize(URL location, ResourceBundle resources) {
    }

    public void loadSection(Section section) {
        actualSection = section;
        tfNameModifySection.setPromptText(section.getName());
        tfDescriptionModifySection.setPromptText(section.getDescription());
        tfActiveModifySection1.setSelected(section.getActive() == 1);

        btnCreateSectionData.setVisible(false);
        btnModifySectionData.setVisible(true);
        tfActiveModifySection1.setVisible(true);
    }

    public void loadCreateWindow() {
        btnCreateSectionData.setVisible(true);
        btnModifySectionData.setVisible(false);
        tfActiveModifySection1.setVisible(false);
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (actionEvent.getSource() == btnModifySectionData) {
            updateSection();

            Stage primaryStage = (Stage) btnModifySectionData.getScene().getWindow();
            primaryStage.close();
        }
        if (actionEvent.getSource() == btnCreateSectionData) {

            createSection();
        }
    }

    private void updateSection() throws IOException, InterruptedException {
        String newName = tfNameModifySection.getText();
        String newDescription = tfDescriptionModifySection.getText();
        int newStatusActive ;

        if (newName.equals("")) {
            newName = actualSection.getName();
        }
        if (newDescription.equals("")) {
            newDescription = actualSection.getDescription();
        }
        if (tfActiveModifySection1.isSelected()){
            newStatusActive = 1;
        } else {
            newStatusActive = 0;
        }

        actualSection.updateSection(newName, newDescription, newStatusActive);
    }

    private void createSection() throws IOException, InterruptedException {
        boolean error = false;
        String newName = tfNameModifySection.getText();
        String newDescription = tfDescriptionModifySection.getText();

        actualSection = new Section(0, newName, newDescription, 1);

        error = checkValue(newName, tfNameModifySection);
        if (newDescription.equals("")) {
            tfDescriptionModifySection.setStyle("-fx-border-color: red");
            error = true;
        }
        if (error != true) {
            actualSection.createSection();
            Stage primaryStage = (Stage) btnCreateSectionData.getScene().getWindow();
            primaryStage.close();
        }

    }

    private boolean checkValue(String value, TextField origin){
        if(value.equals("")){
            origin.setStyle("-fx-border-color: red");
            return true;
        }
        return false;
    }

}
