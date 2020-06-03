package home;

import Classes.API;
import Classes.Section;
import Classes.TypeService;
import Items.ControllerItemSection;
import Items.ControllerItemType;
import Items.ControllerItemUser;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import Classes.User;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller <O, C> implements Initializable {

    private User userConnected;
    private Gson gson = new Gson();

    @FXML
    private VBox pnItems = null;
    @FXML
    private VBox pnItemsSection = null;
    @FXML
    private VBox pnItemsTypes = null;
    @FXML
    private VBox pnItemsUser = null;
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
    private Button btnTypesServices;
    @FXML
    private Button btnSignout;

    //Settings
    @FXML
    private Button btnModifyUser;

    //Section
    @FXML
    private Button btnCreateSection;

    @FXML
    private Label userNameField;

    //Type
    @FXML
    private Button btnCreateType;

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
    @FXML
    private Pane pnlTypesServices;

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfSurname;
    @FXML
    private Label tfEmail;
    @FXML
    private DatePicker dfBirthdate;
    @FXML
    private TextField tfAdress;
    @FXML
    private TextField tfCity;


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

    public void initData(User sessionUser){
        userConnected = sessionUser;

        String fullName = userConnected.getName() + ' ' + userConnected.getSurname();
        userNameField.setText(fullName);
    }


    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (actionEvent.getSource() == btnUsers) {
            loadTableUsersData();
            pnlUsers.setStyle("-fx-background-color : #E2E2E2");
            pnlUsers.toFront();
        }
        if (actionEvent.getSource() == btnForum) {
            loadSectionData();
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
            loadUserData();
            pnlSettings.setStyle("-fx-background-color : #E2E2E2");
            pnlSettings.toFront();
        }
        if (actionEvent.getSource() == btnServices) {
            pnlServices.setStyle("-fx-background-color : #E2E2E2");
            pnlServices.toFront();
        }
        if (actionEvent.getSource() == btnTypesServices) {
            loadTypeData();
            pnlTypesServices.setStyle("-fx-background-color : #E2E2E2");
            pnlTypesServices.toFront();
        }
        if (actionEvent.getSource() == btnModifyUser) {
            modifyUserInfos();
        }
        if (actionEvent.getSource() == btnCreateSection) {
            loadCreateWindow("section", "../ModifyScreen/ModifySection.fxml", "LSB: Création d'une Section");
        }
        if (actionEvent.getSource() == btnCreateType) {
            loadCreateWindow("type", "../ModifyScreen/ModifyType.fxml", "LSB: Création d'un Type de service");
        }
        if (actionEvent.getSource() == btnSignout) {
            confirmDisconnection();
        }
    }

    private void confirmDisconnection() throws IOException {
        Stage primaryStage = (Stage) btnSignout.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("../home/disconnectionConfirm.fxml")
        );

        Stage mainStage = new Stage();
        mainStage.setTitle("LinkService Backoffice");
        mainStage.setScene(new Scene((Pane) loader.load()));
        home.DisconnectionConfirm controller = loader.getController();
        controller.initWindow(primaryStage);

        mainStage.show();
    }

    private void loadUserData() {
        tfName.setPromptText(userConnected.getName());
        tfSurname.setPromptText(userConnected.getSurname());
        tfEmail.setText(userConnected.getEmail());
        dfBirthdate.setPromptText(userConnected.getBirthdate());
        tfAdress.setPromptText(userConnected.getAdress());
        tfCity.setPromptText(userConnected.getCity());
    }

    private void loadSectionData() throws IOException, InterruptedException {
        clearPane(pnItemsSection);

        HttpResponse<String> response = getData("section");
        HashMap<String, Section> sectionData = new HashMap<>();

        if(response.body().startsWith("i", 2)){
            sectionData.put("0",gson.fromJson(response.body(), Section.class));
        } else {
            sectionData = API.decodeResponseMultipleAsSection(response);
        }

        if(sectionData != null){
            Node[] nodes = new Node[10];
            for (int i = 0; i < sectionData.size(); i++) {
                try {
                    final int j = i;

                    Section section = sectionData.get(Integer.toString(i));

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Items/ItemSection.fxml"));
                    nodes[i] = loader.load();

                    nodes[i].setId(Integer.toString(i));

                    ControllerItemSection controllerItemSection = loader.getController();
                    controllerItemSection.updateItemSection(section);

                    nodes[i].setOnMouseEntered(event -> {
                        nodes[j].setStyle("-fx-background-color : #A09B9B");
                    });
                    nodes[i].setOnMouseExited(event -> {
                        nodes[j].setStyle("-fx-background-color : #E2E2E2");
                    });
                    pnItemsSection.getChildren().add(nodes[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            //TODO if no section to display
        }

    }

    private void loadTypeData() throws IOException, InterruptedException {
        clearPane(pnItemsTypes);

        HttpResponse<String> response = getData("type_service");
        HashMap<String, TypeService> typeData = new HashMap<>();

        if(response.body().startsWith("i", 2)){
            typeData.put("0",gson.fromJson(response.body(), TypeService.class));
        } else {
            typeData = API.decodeResponseMultipleAsTypeService(response);
        }

        if(typeData != null){
            Node[] nodes = new Node[10];
            for (int i = 0; i < typeData.size(); i++) {
                try {
                    final int j = i;

                    TypeService type = typeData.get(Integer.toString(i));

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Items/ItemType.fxml"));
                    nodes[i] = loader.load();

                    nodes[i].setId(Integer.toString(i));

                    ControllerItemType controllerItemType = loader.getController();
                    controllerItemType.loadItem(type);

                    nodes[i].setOnMouseEntered(event -> {
                        nodes[j].setStyle("-fx-background-color : #A09B9B");
                    });
                    nodes[i].setOnMouseExited(event -> {
                        nodes[j].setStyle("-fx-background-color : #E2E2E2");
                    });
                    pnItemsTypes.getChildren().add(nodes[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            //TODO : no item to display
        }

    }

    private void loadTableUsersData() throws IOException, InterruptedException {
        Gson gson = new Gson();
        clearPane(pnItemsUser);

        HttpResponse<String> response = getData("user");
        HashMap<String, User> typeData = new HashMap<>();

        if(response.body().startsWith("i", 2)){
            typeData.put("0",gson.fromJson(response.body(), User.class));
        } else {
            typeData = API.decodeResponseMultipleAsUser(response);
        }

        if(typeData != null){
            Node[] nodes = new Node[10];
            for (int i = 0; i < typeData.size(); i++) {
                try {
                    final int j = i;

                    User user = typeData.get(Integer.toString(i));

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Items/ItemUser.fxml"));
                    nodes[i] = loader.load();

                    nodes[i].setId(Integer.toString(i));

                    ControllerItemUser controllerItemUser = loader.getController();
                    controllerItemUser.loadItem(user);

                    nodes[i].setOnMouseEntered(event -> {
                        nodes[j].setStyle("-fx-background-color : #A09B9B");
                    });
                    nodes[i].setOnMouseExited(event -> {
                        nodes[j].setStyle("-fx-background-color : #E2E2E2");
                    });
                    pnItemsUser.getChildren().add(nodes[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            //TODO : no item to display
        }
    }

    private HttpResponse<String> getData(String table) throws IOException, InterruptedException {
        Gson gson = new Gson();

        HashMap<String, Object> inputData = new HashMap<>();
        inputData.put("table", table);
        String inputJson = gson.toJson(inputData);

        return API.sendRequest(inputJson, "readAll");
    }

    private void modifyUserInfos() throws IOException, InterruptedException {
        String newName = tfName.getText();
        String newSurname = tfSurname.getText();
        String newAdress = tfAdress.getText();
        String newCity = tfCity.getText();
        String newBirthDate;
        LocalDate newBirthDateValue = dfBirthdate.getValue();

        if (newName.equals("")) {
            newName = userConnected.getName();
        }
        if (newSurname.equals("")) {
            newSurname = userConnected.getSurname();
        }
        if (newAdress.equals("")) {
            newAdress = userConnected.getAdress();
        }
        if (newCity.equals("")) {
            newCity = userConnected.getCity();
        }
        if (newBirthDateValue == null) {
            newBirthDate = userConnected.getBirthdate();
        } else {
            Instant instant = Instant.from(newBirthDateValue.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            newBirthDate = dateFormat.format(date);
        }

        userConnected.updateUser(newName, newSurname, newBirthDate, newAdress, newCity);
    }

    private void loadCreateWindow(String object, String window, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(window)
        );

        Stage mainStage = new Stage();
        mainStage.setTitle(title);
        mainStage.setScene(new Scene((Pane) loader.load()));
        if(object.equals("section")){
            ModifyScreen.ControllerModifySection controller = loader.getController();
            controller.loadCreateWindow();
        } else if (object.equals("type")){
            ModifyScreen.ControllerModifyType controller = loader.getController();
            controller.loadCreateWindow();
        }
        mainStage.show();
    }

    private void clearPane(Pane paneToClear){
        if (paneToClear.getChildren().size() > 0) {
            paneToClear.getChildren().clear();
        }
    }

}
