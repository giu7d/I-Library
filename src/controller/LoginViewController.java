package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.*;
import model.User;
import model.UserDAO;

import javax.swing.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginViewController {

    @FXML private Label closeButton;
    @FXML private Button loginButton;
    @FXML private Button singupButton;
    @FXML private TextField raField;
    @FXML private PasswordField passField;

    @FXML
    private void closeAct(){

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void loginAct(){

        UserDAO DAO = new UserDAO();
        User u = new User();

        if(raField.getText().equals("") || passField.getText().equals("")) {
            JOptionPane.showConfirmDialog(
                    null, "Informe o RA e a Senha!",
                    "Atenção!",
                    JOptionPane.DEFAULT_OPTION);
        }

        else {

            u.setRa(Integer.valueOf(raField.getText()));

            try {
                u.setPasswd(getCrypt(passField));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            if (DAO.login(u.getRa(), u.getPasswd())) {


                User log = DAO.readSearch(u.getRa() , u.getPasswd());

                Platform.runLater(() -> {

                    UserSession.session = log;

                    try {



                        FXMLLoader libScreen = new FXMLLoader(getClass().getResource("../view/LibView.fxml"));
                        Parent root = libScreen.load();
                        Stage stage = (Stage) this.loginButton.getScene().getWindow();
                        stage.setScene(new Scene(root, 800, 480));
                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                JOptionPane.showConfirmDialog(
                        null, "Login Invalido!!! RA ou Senha incorretos!",
                        "Atenção!",
                        JOptionPane.DEFAULT_OPTION);
            }
        }
    }


    @FXML
    private void singupAct(){

        Platform.runLater(() -> {
            try {

                FXMLLoader singupScreen = new FXMLLoader(getClass().getResource("../view/SingupView.fxml"));

                Parent root = singupScreen.load();
                Stage stage = (Stage) this.singupButton.getScene().getWindow();
                stage.setScene(new Scene(root, 720, 480));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public String getCrypt(PasswordField pF) throws NoSuchAlgorithmException {

        MessageDigest crypt = MessageDigest.getInstance("SHA-256");
        crypt.update(pF.getText().getBytes());
        String passWd = new String(crypt.digest());

        return passWd;
    }

}
