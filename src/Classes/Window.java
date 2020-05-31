package Classes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Window {

    public static void loadNewWindow(String file, String title, String action ) throws IOException {
/*
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(file)
        );

        Stage mainStage = new Stage();
        mainStage.setTitle(title);
        mainStage.setScene(new Scene((Pane) loader.load()));

        if(action.equals("modify")){
            ModifyScreen.ControllerModifySection controller = loader.getController();
            controller.loadSection(section);
        } else if (action.equals("delete")){
            ModifyScreen.ControllerDeleteItemWindow controller = loader.getController();
            controller.loadItemToDelete("Rubrique => " + section.getName(), section);
        }

        mainStage.show();
*/


    }
}
