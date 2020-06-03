package Items;

import Classes.Section;
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
    private Button btnModifyType;
    @FXML
    private Button btnDeleteType;

    public void updateItemSection(int id, String name, String description, String image) {
        labelIdType.setText(Integer.toString(id));
        labelNameType.setText(name);
        labelDescriptionType.setText(description);
        labelImageType.setText(image);

        typeService = new TypeService(id, name, description, image);
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (actionEvent.getSource() == btnModifyType) {
            loadNewWindow("../ModifyScreen/ModifyType.fxml", "LSB: Modification d'un type", "modify");
        }
        if (actionEvent.getSource() == btnDeleteType){
            loadNewWindow("../ModifyScreen/DeleteItemWindow.fxml", "LSB: Supression d'un type", "delete");
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
            ModifyScreen.ControllerModifyType controller = loader.getController();
            controller.loadSection(typeService);
        } else if (action.equals("delete")){
            ModifyScreen.ControllerDeleteItemWindow controller = loader.getController();
            controller.loadItemToDelete("Type de Service => " + typeService.getName(), typeService.getId(), "type_service");
        }

        mainStage.show();
    }


    public void initialize(URL location, ResourceBundle resources) {

    }

}
