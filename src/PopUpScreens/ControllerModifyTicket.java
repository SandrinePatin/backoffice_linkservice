package PopUpScreens;

import Classes.Section;
import Classes.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerModifyTicket {
    Ticket actualTicket;

    @FXML
    private TextField tfNameModifySection;
    @FXML
    private TextArea tfDescriptionModifySection;
    @FXML
    private ComboBox<String> cbStatutTicket;
    @FXML
    private TextField tfUserModifyTicket;

    @FXML
    private Button btnModifySectionData;
    @FXML
    private Button btnCreateSectionData;


    public void initialize(URL location, ResourceBundle resources) {
    }

    public void loadSection(Ticket ticket) {
        actualTicket = ticket;
        tfNameModifySection.setPromptText(ticket.getDate());
        tfDescriptionModifySection.setPromptText(ticket.getDescription());
        tfUserModifyTicket.setPromptText(ticket.getUserAssigned());

        cbStatutTicket.getItems().addAll(
                "0 - A résoudre",
                "1 - Résolu"
        );
        cbStatutTicket.getSelectionModel().select(ticket.getStatut());

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
        String newUserAssigned = tfUserModifyTicket.getText();
        String newStatus = cbStatutTicket.getValue();

        if (newName.equals("")) {
            newName = actualTicket.getDate();
        }
        if (newDescription.equals("")) {
            newDescription = actualTicket.getDescription();
        }
        if (newUserAssigned.equals("")){
            newUserAssigned = actualTicket.getUserAssigned();
        }
        if(newStatus.contains("0")){
            newStatus = "0";
        } else if (newStatus.contains("1")){
            newStatus = "1";
        }

        actualTicket.updateTicket(newName, newDescription, newUserAssigned, newStatus);
    }

    private void createSection() throws IOException, InterruptedException {
        boolean error = false;
        String newName = tfNameModifySection.getText();
        String newDescription = tfDescriptionModifySection.getText();
        String newUserAssigned = tfUserModifyTicket.getText();

        actualTicket = new Ticket(0, newName, newDescription, newUserAssigned, 1);

        error = checkValue(newName, tfNameModifySection);
        if (newDescription.equals("")) {
            tfDescriptionModifySection.setStyle("-fx-border-color: red");
            error = true;
        }
        if (error != true) {
            actualTicket.createTicket();
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
