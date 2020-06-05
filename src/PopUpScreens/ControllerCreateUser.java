package PopUpScreens;

import Classes.TypeService;
import Classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerCreateUser {

    @FXML
    private Button btnCreateSupportAccount;

    @FXML
    private TextField SupportMail;
    @FXML
    private TextField SupportPassword;
    @FXML
    private TextField SupportName;
    @FXML
    private TextField SuportSurname;
    @FXML
    private DatePicker SupportBirthdate;


    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException, NoSuchAlgorithmException {
        if (actionEvent.getSource() == btnCreateSupportAccount) {
            createSupportAccount();
        }
    }

    private void createSupportAccount() throws IOException, InterruptedException, NoSuchAlgorithmException {
        boolean error = false;
        String email = SupportMail.getText();
        String password = SupportPassword.getText();
        String name = SupportName.getText();
        String surname = SuportSurname.getText();
        String birthDate = "2000-01-01";
        LocalDate birthDateValue = SupportBirthdate.getValue();



        error = checkValue(email, SupportMail);
        error = checkValue(password, SupportPassword) || error;
        error = checkValue(name, SupportName) || error;
        error = checkValue(surname, SuportSurname) || error;
        if(birthDateValue == null){
            SupportBirthdate.setStyle("-fx-border-color: red");
            error = true;
        } else {
            Instant instant = Instant.from(birthDateValue.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            birthDate = dateFormat.format(date);
        }


        if (!error) {
            User.createUser(email, password, name, surname, birthDate, "support");

            Stage primaryStage = (Stage) btnCreateSupportAccount.getScene().getWindow();
            primaryStage.close();
        }

    }

    private boolean checkValue(String value, TextField origin){
        if(value.equals("")){
            origin.setStyle("-fx-border-color: red");
            return true;
        }
        return false;
    }

}
