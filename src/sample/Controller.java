package sample;

import Classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Controller {

    @FXML
    private Button connectBtn;
    @FXML
    private Label errorLabel;

    @FXML
    private TextField username;
    @FXML
    private TextField password;

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException, NoSuchAlgorithmException {
        if (actionEvent.getSource() == connectBtn) {
            Connexion connexion = new Connexion(username.getText(), password.getText());

            User user = connexion.checkConnexion();

            if (user == null) {
                errorLabel.setVisible(true);
            } else {
                user = connexion.getUserData(user.getId());

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("../home/Home.fxml")
                );

                Stage mainStage = new Stage();
                mainStage.setTitle("LinkService Backoffice");
                mainStage.setScene(new Scene((Pane) loader.load()));
                home.Controller controller = loader.getController();
                controller.initData(user);

                mainStage.show();

                Stage primaryStage = (Stage) connectBtn.getScene().getWindow();
                primaryStage.close();

            }
        }
    }

}
