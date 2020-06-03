package Items;

import Classes.User;
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

public class ControllerItemUser {

    private User user;

    @FXML
    private Label labelIdUser;
    @FXML
    private Label labelEmailUser;
    @FXML
    private Label labelPointsUser;
    @FXML
    private Label labelTypeUser;

    @FXML
    private Button btnDeleteUser;

    public void loadItem(User u) {
        user = u;
        labelIdUser.setText(Integer.toString(user.getId()));
        labelEmailUser.setText(user.getEmail());
        labelPointsUser.setText(Integer.toString(user.getPoints()));
        labelTypeUser.setText(user.getType());
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (actionEvent.getSource() == btnDeleteUser){
            loadNewWindow("../ModifyScreen/DeleteItemWindow.fxml", "LSB: Supression d'un utilisateur", "delete");
        }
    }

    public void loadNewWindow(String file, String title, String action) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(file)
        );

        Stage mainStage = new Stage();
        mainStage.setTitle(title);
        mainStage.setScene(new Scene((Pane) loader.load()));

        ModifyScreen.ControllerDeleteItemWindow controller = loader.getController();
        controller.loadItemToDelete("Type de Service => " + user.getEmail(), user.getId(), "type_service");


        mainStage.show();
    }


    public void initialize(URL location, ResourceBundle resources) {

    }

}
