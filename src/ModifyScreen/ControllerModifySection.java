package ModifyScreen;

import Classes.Section;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
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
    private Button btnModifySectionData;
    @FXML
    private Button btnCreateSectionData;


    public void initialize(URL location, ResourceBundle resources) {
    }

    public void loadSection(Section section) {
        actualSection = section;
        tfNameModifySection.setPromptText(section.getName());
        tfDescriptionModifySection.setPromptText(section.getDescription());

        btnCreateSectionData.setVisible(false);
        btnModifySectionData.setVisible(true);
    }

    public void loadCreateWindow() {
        btnCreateSectionData.setVisible(true);
        btnModifySectionData.setVisible(false);
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

        if (newName.equals("")) {
            newName = actualSection.getName();
        }
        if (newDescription.equals("")) {
            newDescription = actualSection.getDescription();
        }

        actualSection.updateSection(newName, newDescription);
    }

    private void createSection() throws IOException, InterruptedException {
        boolean error = false;
        System.out.println("miam");
        String newName = tfNameModifySection.getText();
        String newDescription = tfDescriptionModifySection.getText();

        actualSection = new Section(0, newName, newDescription);

        if (newName.equals("")) {
            tfNameModifySection.setStyle("-fx-border-color: red");
            error = true;
        }
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

}
