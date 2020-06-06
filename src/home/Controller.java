package home;

import Classes.*;
import Items.ControllerItemSection;
import Items.ControllerItemService;
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

public class Controller implements Initializable {

    private User userConnected;
    private final Gson gson = new Gson();

    @FXML
    private VBox pnItems = null;
    @FXML
    private VBox pnItemsSection = null;
    @FXML
    private VBox pnItemsTypes = null;
    @FXML
    private VBox pnItemsUser = null;
    @FXML
    private VBox pnItemsService = null;
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
    @FXML
    private Button btnModifyPassword;

    //Section
    @FXML
    private Button btnCreateSection;
    @FXML
    private Button btnDisplayDesacSection;

    @FXML
    private Label userNameField;

    //Type
    @FXML
    private Button btnCreateType;
    @FXML
    private Button btnDisplayDesac;

    //Support
    @FXML
    private Button btnCreateSupport;

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
                nodes[i] = FXMLLoader.load(getClass().getResource("Item.fxml"));

                //give the items some effect

                setStyleNode(nodes[i]);
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
            loadSectionData(true);
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
            loadServiceData(true);
            pnlServices.setStyle("-fx-background-color : #E2E2E2");
            pnlServices.toFront();
        }
        if (actionEvent.getSource() == btnTypesServices) {
            loadTypeData(true);
            pnlTypesServices.setStyle("-fx-background-color : #E2E2E2");
            pnlTypesServices.toFront();
        }
        if (actionEvent.getSource() == btnModifyUser) {
            modifyUserInfos();
        }
        if (actionEvent.getSource() == btnModifyPassword) {
            modifyPassword();
        }
        if (actionEvent.getSource() == btnDisplayDesac) {
            loadTypeData(false);
        }
        if (actionEvent.getSource() == btnDisplayDesacSection) {
            loadSectionData(false);
        }
        if (actionEvent.getSource() == btnCreateSection) {
            loadWindow("section", "../PopUpScreens/ModifySection.fxml", "LSB: Création d'une Section");
        }
        if (actionEvent.getSource() == btnCreateType) {
            loadWindow("type", "../PopUpScreens/ModifyType.fxml", "LSB: Création d'un Type de service");
        }
        if (actionEvent.getSource() == btnCreateSupport) {
            loadWindow("user", "../PopUpScreens/CreateUser.fxml", "LSB: Création d'un compte Support");
        }
        if (actionEvent.getSource() == btnSignout) {
            loadWindow("disconnection","../home/disconnectionConfirm.fxml","LinkService Backoffice");
        }
    }

    private void loadUserData() {
        tfName.setPromptText(userConnected.getName());
        tfSurname.setPromptText(userConnected.getSurname());
        tfEmail.setText(userConnected.getEmail());
        dfBirthdate.setPromptText(userConnected.getBirthdate());
        tfAdress.setPromptText(userConnected.getAdress());
        tfCity.setPromptText(userConnected.getCity());
    }

    private void loadSectionData(boolean active) throws IOException, InterruptedException {
        clearPane(pnItemsSection);

        HttpResponse<String> response = getData("section",active);
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
                    Section section = sectionData.get(Integer.toString(i));

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Items/ItemSection.fxml"));
                    nodes[i] = loader.load();

                    nodes[i].setId(Integer.toString(i));

                    ControllerItemSection controllerItemSection = loader.getController();
                    controllerItemSection.updateItemSection(section);

                    setStyleNode(nodes[i]);
                    pnItemsSection.getChildren().add(nodes[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            //TODO if no section to display
        }
    }

    private void loadTypeData(boolean active) throws IOException, InterruptedException {
        clearPane(pnItemsTypes);

        HttpResponse<String> response = getData("type_service",active);
        HashMap<String, TypeService> typeData = new HashMap<>();

        if(response.body() != null){
            if(response.body().startsWith("i", 2)){
                typeData.put("0",gson.fromJson(response.body(), TypeService.class));
            } else {
                typeData = API.decodeResponseMultipleAsTypeService(response);
            }

            Node[] nodes = new Node[10];
            for (int i = 0; i < typeData.size(); i++) {
                try {
                    TypeService type = typeData.get(Integer.toString(i));

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Items/ItemType.fxml"));
                    nodes[i] = loader.load();

                    nodes[i].setId(Integer.toString(i));

                    ControllerItemType controllerItemType = loader.getController();
                    controllerItemType.loadItem(type);

                    setStyleNode(nodes[i]);
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
        clearPane(pnItemsUser);

        HttpResponse<String> response = getData("user", true);
        HashMap<String, User> typeData = new HashMap<>();

        if(response.body() != null){
            if(response.body().startsWith("i", 2)){
                typeData.put("0",gson.fromJson(response.body(), User.class));
            } else {
                typeData = API.decodeResponseMultipleAsUser(response);
            }

            Node[] nodes = new Node[10];
            for (int i = 0; i < typeData.size(); i++) {
                try {
                    User user = typeData.get(Integer.toString(i));

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Items/ItemUser.fxml"));
                    nodes[i] = loader.load();

                    nodes[i].setId(Integer.toString(i));

                    ControllerItemUser controllerItemUser = loader.getController();
                    controllerItemUser.loadItem(user);

                    setStyleNode(nodes[i]);
                    pnItemsUser.getChildren().add(nodes[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            //TODO : no item to display
        }
    }

    private void loadServiceData(boolean active) throws IOException, InterruptedException {
        clearPane(pnItemsService);

        HttpResponse<String> response = getData("service",active);
        HashMap<String, Service> ServicesData = new HashMap<>();

        if(response.body() != null){
            if(response.body().startsWith("i", 2)){
                ServicesData.put("0",gson.fromJson(response.body(), Service.class));
            } else {
                ServicesData = API.decodeResponseMultipleAsService(response);
            }

            Node[] nodes = new Node[10];
            for (int i = 0; i < ServicesData.size(); i++) {
                try {
                    Service service = ServicesData.get(Integer.toString(i));

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Items/ItemService.fxml"));
                    nodes[i] = loader.load();

                    nodes[i].setId(Integer.toString(i));

                    ControllerItemService controller = loader.getController();
                    controller.loadItem(service);

                    setStyleNode(nodes[i]);
                    pnItemsService.getChildren().add(nodes[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            //TODO : no item to display
        }
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

    private void modifyPassword() throws IOException {
        loadWindow("userPwd", "../PopUpScreens/ModifyPassword.fxml", "LSB: Modifiction du mot de passe");
    }

    private HttpResponse<String> getData(String table, boolean active) throws IOException, InterruptedException {
        Gson gson = new Gson();
        String inputJson;
        String action;
        HashMap<String, Object> inputData = new HashMap<>();
        inputData.put("table", table);

        if(table.equals("type_service") || table.equals("section") || table.equals("user")){
            HashMap<String, String> inputValues = new HashMap<>();
            inputValues.put("where"," WHERE active=" + (active ? 1 : 0));
            inputData.put("values", inputValues);
            action = "readWithFilter";
        } else {
            action = "readAll";
        }

        inputJson = gson.toJson(inputData);
        return API.sendRequest(inputJson, action);
    }

    private void loadWindow(String action, String window, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(window)
        );

        Stage mainStage = new Stage();
        mainStage.setTitle(title);
        mainStage.setScene(new Scene((Pane) loader.load()));
        switch (action) {
            case "section": {
                PopUpScreens.ControllerModifySection controller = loader.getController();
                controller.loadCreateWindow();
                break;
            }
            case "type": {
                PopUpScreens.ControllerModifyType controller = loader.getController();
                controller.loadCreateWindow();
                break;
            }
            case "userPwd": {
                PopUpScreens.ControllerModifyPassword controller = loader.getController();
                controller.loadCreateWindow(userConnected);
                break;
            }
            case "disconnection":{
                Stage primaryStage = (Stage) btnSignout.getScene().getWindow();
                home.DisconnectionConfirm controller = loader.getController();
                controller.initWindow(primaryStage);
            }
        }
        mainStage.show();
    }

    private void clearPane(Pane paneToClear){
        if (paneToClear.getChildren().size() > 0) {
            paneToClear.getChildren().clear();
        }
    }

    private void setStyleNode(Node node){
        node.setOnMouseEntered(event -> {
            node.setStyle("-fx-background-color : #A09B9B");
        });
        node.setOnMouseExited(event -> {
            node.setStyle("-fx-background-color : #E2E2E2");
        });
    }

}
