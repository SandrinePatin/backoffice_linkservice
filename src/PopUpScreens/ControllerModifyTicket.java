package PopUpScreens;

import Classes.Ticket;
import Classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ControllerModifyTicket {
    Ticket actualTicket;
    int idUserCreator;

    @FXML
    private TextField tfNameModifySection;
    @FXML
    private TextArea tfDescriptionModifySection;
    @FXML
    private ComboBox<String> cbStatutTicket;
    @FXML
    private TextField tfUserModifyTicket;

    @FXML
    private Button btnModifyTicketData;
    @FXML
    private Button btnCreateTicketData;


    public void initialize(URL location, ResourceBundle resources) {
    }

    public void loadSection(Ticket ticket) {
        actualTicket = ticket;

        tfNameModifySection.setPromptText(ticket.getDate());
        tfDescriptionModifySection.setPromptText(ticket.getDescription());
        tfUserModifyTicket.setPromptText(ticket.getUserAssigned());

        cbStatutTicket.getSelectionModel().select(ticket.getStatut());

        setStatusAvailable();

        btnCreateTicketData.setVisible(false);
        btnModifyTicketData.setVisible(true);
    }

    public void loadCreateWindow(int idUser) {
        idUserCreator = idUser;
        setStatusAvailable();
        btnCreateTicketData.setVisible(true);
        btnModifyTicketData.setVisible(false);
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (actionEvent.getSource() == btnModifyTicketData) {
            updateTicket();

            Stage primaryStage = (Stage) btnModifyTicketData.getScene().getWindow();
            primaryStage.close();
        }
        if (actionEvent.getSource() == btnCreateTicketData) {

            createTicket();
        }
    }

    private void updateTicket() throws IOException, InterruptedException {
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

    private void createTicket() throws IOException, InterruptedException {
        boolean error = false;
        String newDescription = tfDescriptionModifySection.getText();
        String newUserAssigned = tfUserModifyTicket.getText();
        String todayDate;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        todayDate = dtf.format(now);

        actualTicket = new Ticket(0, todayDate, newDescription, idUserCreator, newUserAssigned, 0);

        if (newDescription.equals("")) {
            tfDescriptionModifySection.setStyle("-fx-border-color: red");
            error = true;
        }
        if (error != true) {
            actualTicket.createTicket();
            Stage primaryStage = (Stage) btnCreateTicketData.getScene().getWindow();
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

    private void setStatusAvailable(){
        cbStatutTicket.getItems().addAll(
                "0 - A résoudre",
                "1 - Résolu"
        );
    }

    private void setUserAvailable(){

    }
}
