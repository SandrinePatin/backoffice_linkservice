package PopUpScreens;

import Classes.TypeService;
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

public class ControllerModifyType {
    TypeService actualType;

    @FXML
    private TextField tfNameModifyType;
    @FXML
    private TextArea tfDescriptionModifyType;
    @FXML
    private TextArea tfImageModifyType;
    @FXML
    private CheckBox tfActifType;

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
        tfImageModifyType.setPromptText(typeService.getImage());
        tfActifType.setSelected(typeService.getActive() == 1);

        btnCreateSectionData.setVisible(false);
        btnModifySectionData.setVisible(true);
        tfActifType.setVisible(true);
    }

    public void loadCreateWindow() {
        btnCreateSectionData.setVisible(true);
        btnModifySectionData.setVisible(false);
        tfActifType.setVisible(false);
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (actionEvent.getSource() == btnModifySectionData) {
            updateType();

            Stage primaryStage = (Stage) btnModifySectionData.getScene().getWindow();
            primaryStage.close();
        }
        if (actionEvent.getSource() == btnCreateSectionData) {

            createType();
        }
    }

    private void updateType() throws IOException, InterruptedException {
        String newName = tfNameModifyType.getText();
        String newDescription = tfDescriptionModifyType.getText();
        String newImage = tfImageModifyType.getText();
        int newStatusActive ;

        if (newName.equals("")) {
            newName = actualType.getName();
        }
        if (newDescription.equals("")) {
            newDescription = actualType.getDescription();
        }
        if (newImage.equals("")) {
            newImage = actualType.getImage();
        }
        if (tfActifType.isSelected()){
            newStatusActive = 1;
        } else {
            newStatusActive = 0;
        }

        actualType.updateType(newName, newDescription, newImage, newStatusActive);
    }

    private void createType() throws IOException, InterruptedException {
        boolean error = false;
        String newName = tfNameModifyType.getText();
        String newDescription = tfDescriptionModifyType.getText();
        String newImage = tfImageModifyType.getText();

        actualType = new TypeService(0, newName, newDescription, newImage, 1);

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
