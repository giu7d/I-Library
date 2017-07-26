package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import model.UserDAO;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.*;
import java.util.ResourceBundle;

public class SingupViewController{

    @FXML private Label closeButton;
    @FXML private Button cancelButton;
    @FXML private Button createLoginButton;
    @FXML private TextField emailField;
    @FXML private TextField raField;
    @FXML private PasswordField passField;
    @FXML private PasswordField passConfField;



    @FXML
    private void closeAct(){

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void backAct(){
        Platform.runLater(() -> {
            try {

                FXMLLoader loginScreen = new FXMLLoader(getClass().getResource("../view/LoginView.fxml"));

                Parent root = loginScreen.load();
                Stage stage = (Stage) this.cancelButton.getScene().getWindow();
                stage.setScene(new Scene(root, 720, 480));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void createLoginAct() {
        Platform.runLater(() -> {

            if(passConfField.getText().equals(passField.getText())) {
                int n = JOptionPane.showConfirmDialog(
                        null, "Deseja completar o cadastro?!",
                        "Atenção!",
                        JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {

                    UserDAO DAO = new UserDAO();
                    User u = new User();

                    u.setEmail(emailField.getText());
                    u.setRa(Integer.valueOf(raField.getText()));


                    try {
                        u.setPasswd(getCrypt(passField));
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }


                    DAO.updatePass(u);
                    backAct();
                }
            }
        });
    }

    public String getCrypt(PasswordField pF) throws NoSuchAlgorithmException {

        MessageDigest crypt = MessageDigest.getInstance("SHA-256");
        crypt.update(pF.getText().getBytes());
        String passWeed = new String(crypt.digest());

        return passWeed;
    }


}
