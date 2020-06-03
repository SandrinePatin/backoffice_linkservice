package Items;

import Classes.Service;
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

public class ControllerItemService {
    private Service service;

    @FXML
    private Label labelIdService;
    @FXML
    private Label labelNameService;
    @FXML
    private Label labelDateService;
    @FXML
    private Label labelTypeService;
    @FXML
    private Label labelCreatorService;

    @FXML
    private Button btnDeleteService;

    public void loadItem(Service s) {
        service = s;
        labelIdService.setText(Integer.toString(service.getId()));
        labelNameService.setText(service.getName());
        labelDateService.setText(service.getDate());
        labelTypeService.setText(Integer.toString(service.getId_type()));
        labelCreatorService.setText(Integer.toString(service.getId_creator()));
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (actionEvent.getSource() == btnDeleteService){
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
        controller.loadItemToDelete("Utilisateur => " + service.getName(), service.getId(), "service");


        mainStage.show();
    }

    public void initialize(URL location, ResourceBundle resources) {

    }
}
