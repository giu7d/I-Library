package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;


public class AddTakeController implements Initializable {

    @FXML private Label closeButton;
    @FXML private Button cancelButton;
    @FXML private Button addButton;
    @FXML private ComboBox<Book> livroCombo;
    @FXML private ComboBox<User> userCombo;
    @FXML private DatePicker datePick;

    private Take takeControl = null;

    public void initialize(URL location, ResourceBundle resources) {

        BookDAO bDAO = new BookDAO();
        for (Book b : bDAO.read()) {
            if(b.getQtd()>0) livroCombo.getItems().add(b);
        }

        UserDAO uDAO = new UserDAO();
        for (User u : uDAO.read()) {
            if(u.getSt() == 2) userCombo.getItems().add(u);
        }

        Platform.runLater(() -> {

            if(takeControl != null){

                this.livroCombo.getSelectionModel().select(takeControl.getBook());
                this.userCombo.getSelectionModel().select(takeControl.getUser());
                this.datePick.setVisible(false);

                addButton.setText("Modificar");
            }

        });



    }

    @FXML
    private void modAct(){
        if(takeControl == null) {
            int alert = JOptionPane.showConfirmDialog(
                    null, "Deseja COMPLETAR o cadastro?!",
                    "Atenção!",
                    JOptionPane.YES_NO_OPTION);

            if (alert == JOptionPane.YES_OPTION) {

                TakeDAO DAO = new TakeDAO();
                Take t = new Take();

                t.setBook(livroCombo.getSelectionModel().getSelectedItem());
                t.setUser(userCombo.getSelectionModel().getSelectedItem());


                Date nowDate = new Date();
                Date sqlNowDate = new java.sql.Date(nowDate.getTime());
                t.setRetDate(sqlNowDate);

                Date valDate = Date.from(datePick.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date sqlValDate = new java.sql.Date(valDate.getTime());
                t.setValDate(sqlValDate);

                if (t.getDevDate() != null) t.setSt(2);
                else if (t.getValDate().before(sqlNowDate)) t.setSt(1);
                else if (t.getValDate().after(sqlNowDate)) t.setSt(0);

                DAO.create(t);
                backAct();

            }

        }
        else {
            int alert = JOptionPane.showConfirmDialog(
                    null, "Deseja COMPLETAR o cadastro?!",
                    "Atenção!",
                    JOptionPane.YES_NO_OPTION);

            if (alert == JOptionPane.YES_OPTION) {

                TakeDAO DAO = new TakeDAO();
                Take t = new Take();

                t.setBook(livroCombo.getSelectionModel().getSelectedItem());
                t.setUser(userCombo.getSelectionModel().getSelectedItem());

                t.setRetDate(takeControl.getRetDate());
                t.setId(takeControl.getId());

                t.setValDate(takeControl.getValDate());

                //DAO.update(t);
                backAct();

            }

        }
    }

    @FXML
    private void backAct(){
        Platform.runLater(() -> {
            try {

                FXMLLoader libScreen = new FXMLLoader(getClass().getResource("../view/LibView.fxml"));

                Parent root = libScreen.load();
                Stage stage = (Stage) this.cancelButton.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 480));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void closeAct(){

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    public
        void setTake(Take t){
        this.takeControl = t;
    }

}
