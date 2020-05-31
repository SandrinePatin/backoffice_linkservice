package home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class DisconnectionConfirm {
    private Stage principalStage;

    @FXML
    private Button btnYes;
    @FXML
    private Button btnNo;

    public void initWindow(Stage stage){
        principalStage = stage;
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (actionEvent.getSource() == btnYes) {
            Stage primaryStage = (Stage) btnNo.getScene().getWindow();
            primaryStage.close();
            principalStage.close();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("../sample/sample.fxml")
            );

            Stage mainStage = new Stage();
            mainStage.setTitle("LinkService Backoffice");
            mainStage.setScene(new Scene((Pane) loader.load()));
            sample.Controller controller = loader.getController();

            mainStage.show();
        }
        if (actionEvent.getSource() == btnNo) {
            Stage primaryStage = (Stage) btnNo.getScene().getWindow();
            primaryStage.close();
        }
    }
}
