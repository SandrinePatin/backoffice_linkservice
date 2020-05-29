package home;

import Classes.API;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import Classes.User;

import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private User userConnected;

    @FXML
    private VBox pnItems = null;
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnTickets;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnForum;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnServices;

    @FXML
    private Button btnSignout;

    @FXML
    private Label userNameField;

    @FXML
    private Pane pnlUsers;

    @FXML
    private Pane pnlTickets;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlForum;

    @FXML
    private Pane pnlSettings;

    @FXML
    private Pane pnlServices;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pnlOverview.setStyle("-fx-background-color : #E2E2E2");
        pnlOverview.toFront();

        Node[] nodes = new Node[10];
        for (int i = 0; i < nodes.length; i++) {
            try {

                final int j = i;
                nodes[i] = FXMLLoader.load(getClass().getResource("Item.fxml"));

                //give the items some effect

                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #A09B9B");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #E2E2E2");
                });
                pnItems.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void initData(User sessionUser) throws IOException, InterruptedException {
        userConnected = sessionUser;

        String fullName = userConnected.getName() + ' ' + userConnected.getSurname();
        userNameField.setText(fullName);

    }


    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnUsers) {
            pnlUsers.setStyle("-fx-background-color : #E2E2E2");
            pnlUsers.toFront();
        }
        if (actionEvent.getSource() == btnForum) {
            pnlForum.setStyle("-fx-background-color : #E2E2E2");
            pnlForum.toFront();
        }
        if (actionEvent.getSource() == btnOverview) {
            pnlOverview.setStyle("-fx-background-color : #E2E2E2");
            pnlOverview.toFront();
        }
        if (actionEvent.getSource() == btnTickets) {
            pnlTickets.setStyle("-fx-background-color : #E2E2E2");
            pnlTickets.toFront();
        }
        if (actionEvent.getSource() == btnSettings) {
            pnlSettings.setStyle("-fx-background-color : #E2E2E2");
            pnlSettings.toFront();
        }
        if (actionEvent.getSource() == btnServices) {
            pnlServices.setStyle("-fx-background-color : #E2E2E2");
            pnlServices.toFront();
        }
    }
}
