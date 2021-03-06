package Items;

import Classes.TypeService;
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

public class ControllerItemType {

    private TypeService typeService;

    @FXML
    private Label labelIdType;
    @FXML
    private Label labelNameType;
    @FXML
    private Label labelDescriptionType;
    @FXML
    private Label labelImageType;
    @FXML
    private Label labelActiveType;

    @FXML
    private Button btnModifyType;
    @FXML
    private Button btnDeleteType;

    public void loadItem(TypeService t) {
        typeService = t;
        labelIdType.setText(Integer.toString(t.getId()));
        labelNameType.setText(t.getName());
        labelDescriptionType.setText(t.getDescription());
        labelImageType.setText(t.getImage());
        labelActiveType.setText(Integer.toString(t.getActive()));
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (actionEvent.getSource() == btnModifyType) {
            loadNewWindow("../PopUpScreens/ModifyType.fxml", "LSB: Modification d'un type", "modify");
        }
        if (actionEvent.getSource() == btnDeleteType){
            loadNewWindow("../PopUpScreens/DeleteItemWindow.fxml", "LSB: Supression d'un type", "delete");
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
            PopUpScreens.ControllerModifyType controller = loader.getController();
            controller.loadSection(typeService);
        } else if (action.equals("delete")){
            PopUpScreens.ControllerDeleteItemWindow controller = loader.getController();
            controller.loadItemToDelete("Type de Service => " + typeService.getName(), typeService.getId(), "type_service");
        }

        mainStage.show();
    }


    public void initialize(URL location, ResourceBundle resources) {

    }

}
