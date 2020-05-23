package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Controller {

    @FXML
    private Button connectBtn;

    @FXML
    private TextField username;
    @FXML
    private TextField password;

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException, NoSuchAlgorithmException {
        if(actionEvent.getSource() == connectBtn){

            Connexion connexion = new Connexion(username.getText(), password.getText());
            System.out.println(connexion.getPassword());
            connexion.checkConnexion();



        }
    }

}
