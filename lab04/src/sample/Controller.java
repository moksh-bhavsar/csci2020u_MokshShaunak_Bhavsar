package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {
    @FXML
    private TextField userName;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField fullName;
    @FXML
    private TextField email;
    @FXML
    private TextField phoneNumber;
    @FXML
    private Text actiontarget;
    public void handleRegisterButtonAction(ActionEvent actionEvent) {
        System.out.printf("Username: %s\n", userName.getText());
        System.out.printf("Full Name: %s\n", fullName.getText());
        System.out.printf("Phone #: %s\n",phoneNumber.getText());
        System.out.printf("E-mail: %s",email.getText());

        actiontarget.setText("Registered Successfully");
    }
}
