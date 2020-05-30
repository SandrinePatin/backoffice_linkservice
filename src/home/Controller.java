package home;

import Classes.API;
import Classes.Section;
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
import java.lang.management.GarbageCollectorMXBean;
import java.net.URL;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private User userConnected;

    @FXML
    private VBox pnItems = null;
    @FXML
    private VBox pnItemsSection = null;
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
    private Button btnSignout;

    //Settings
    @FXML
    private Button btnModifyUser;

    //Section
    @FXML
    private Button btnCreateSection;

    @FXML
    private Label userNameField;

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

    public void initData(User sessionUser) throws IOException, InterruptedException {
        userConnected = sessionUser;

        String fullName = userConnected.getName() + ' ' + userConnected.getSurname();
        userNameField.setText(fullName);
    }


    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (actionEvent.getSource() == btnUsers) {
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
        if (actionEvent.getSource() == btnModifyUser) {
            modifyUserInfos();
        }
        if (actionEvent.getSource() == btnCreateSection) {
            createSection();
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

    private void loadSectionData() throws IOException, InterruptedException {

        if (pnItemsSection.getChildren().size() > 0) {
            pnItemsSection.getChildren().clear();
        }

        Gson gson = new Gson();

        HashMap<String, Object> inputData = new HashMap<>();
        inputData.put("table", "section");
        String inputJson = gson.toJson(inputData);

        HttpResponse<String> response = API.sendRequest(inputJson, "readAll");
        HashMap<String, Section> sectionData = API.decodeResponseMultipleAsSection(response);


        Node[] nodes = new Node[10];
        for (int i = 0; i < sectionData.size(); i++) {
            try {
                final int j = i;

                Section section = sectionData.get(Integer.toString(i));

                int sectionId = section.getId();
                String sectionName = section.getName();
                String sectionDescription = section.getDescription();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemSection.fxml"));
                nodes[i] = loader.load();

                nodes[i].setId(Integer.toString(i));

                ControllerItemSection controllerItemSection = loader.getController();
                controllerItemSection.updateItemSection(sectionId, sectionName, sectionDescription);

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

    private void createSection() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("../ModifyScreen/ModifySection.fxml")
        );

        Stage mainStage = new Stage();
        mainStage.setTitle("LSB: Création d'une Section");
        mainStage.setScene(new Scene((Pane) loader.load()));
        ModifyScreen.ControllerModifySection controller = loader.getController();
        controller.loadCreateWindow();

        mainStage.show();
    }
}
