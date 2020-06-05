package PopUpScreens;

import Classes.TypeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerModifyType {
    TypeService actualType;

    @FXML
    private TextField tfNameModifyType;
    @FXML
    private TextArea tfDescriptionModifyType;
    @FXML
    private TextArea tfImageModifyType;

    @FXML
    private Button btnModifySectionData;
    @FXML
    private Button btnCreateSectionData;


    public void initialize(URL location, ResourceBundle resources) {
    }

    public void loadSection(TypeService typeService) {
        actualType = typeService;
        tfNameModifyType.setPromptText(typeService.getName());
        tfDescriptionModifyType.setPromptText(typeService.getDescription());

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

            createType();
        }
    }

    private void updateSection() throws IOException, InterruptedException {
        String newName = tfNameModifyType.getText();
        String newDescription = tfDescriptionModifyType.getText();
        String newImage = tfImageModifyType.getText();

        if (newName.equals("")) {
            newName = actualType.getName();
        }
        if (newDescription.equals("")) {
            newDescription = actualType.getDescription();
        }
        if(newImage.equals("")) {
            newImage = actualType.getImage();
        }

        actualType.updateType(newName, newDescription, newImage);
    }

    private void createType() throws IOException, InterruptedException {
        boolean error = false;
        String newName = tfNameModifyType.getText();
        String newDescription = tfDescriptionModifyType.getText();
        String newImage = tfImageModifyType.getText();

        actualType = new TypeService(0, newName, newDescription, newImage);

        if (newName.equals("")) {
            tfNameModifyType.setStyle("-fx-border-color: red");
            error = true;
        }
        if (newDescription.equals("")) {
            tfDescriptionModifyType.setStyle("-fx-border-color: red");
            error = true;
        }
        if (newImage.equals("")) {
            tfDescriptionModifyType.setStyle("-fx-border-color: red");
            error = true;
        }
        if (!error) {
            actualType.createType();
            Stage primaryStage = (Stage) btnCreateSectionData.getScene().getWindow();
            primaryStage.close();
        }

    }
}
