package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private Button connectBtn;

    @FXML
    private TextField username;
    @FXML
    private TextField password;

    public void handleClicks(ActionEvent actionEvent) {
        if(actionEvent.getSource() == connectBtn){
            System.out.println(username.getText());

            Connexion connexion = new Connexion(username.getText(), password.getText());
        }
    }

}
