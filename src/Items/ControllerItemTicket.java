package Items;

import Classes.Ticket;
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

public class ControllerItemTicket {
    private Ticket ticket;

    @FXML
    private Label labelIdTicket;
    @FXML
    private Label labelDateTicket;
    @FXML
    private Label labelDescriptionTicket;
    @FXML
    private Label labelStatutTicket;
    @FXML
    private Label labelUserTicket;

    @FXML
    private Button btnModifyTicket;
    @FXML
    private Button btnDeleteTicket;

    public void setItemTicket(Ticket t) {
        ticket = t;
        labelIdTicket.setText(Integer.toString(ticket.getId()));
        labelDateTicket.setText(ticket.getDate().substring(0, 10));
        labelDescriptionTicket.setText(ticket.getDescription());
        labelUserTicket.setText(ticket.getId_user_assigned());
        String statut = getLabelOfTicketStatus(ticket.getStatut());
        labelStatutTicket.setText(statut);
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (actionEvent.getSource() == btnModifyTicket) {
            loadNewWindow("../PopUpScreens/ModifyTicket.fxml", "LSB: Modification d'une Section", "modify");
        }
        if (actionEvent.getSource() == btnDeleteTicket){
            ticket.closeTicket();
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
            PopUpScreens.ControllerModifyTicket controller = loader.getController();
            controller.loadSection(ticket);
        } else if (action.equals("delete")){
            PopUpScreens.ControllerDeleteItemWindow controller = loader.getController();
            controller.loadItemToDelete("Rubrique => " + ticket.getId(), ticket.getId(), "section");
        }

        mainStage.show();
    }

    private String getLabelOfTicketStatus(int status){
        if(status == 0){
            return "A résoudre";
        } else if (status == 1){
            return "Résolu";
        }
        return "Statut Inconnu";
    }

    public void initialize(URL location, ResourceBundle resources) {

    }

}
