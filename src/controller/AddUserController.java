package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AddUserController implements Initializable {


    @FXML private Label closeButton;
    @FXML private Button cancelButton;
    @FXML private Button addButton;
    @FXML private ComboBox<Curso> cursoCombo;
    @FXML private ComboBox<Uf> ufCombo;
    @FXML private TextField nameField;
    @FXML private TextField raField;

    private User userControl = null;

    public void initialize(URL location, ResourceBundle resources) {


        UfDAO uDAO = new UfDAO();
        for(Uf uf: uDAO.read() ){
            ufCombo.getItems().add(uf);
        }

        CursoDAO cDAO = new CursoDAO();
        for(Curso c: cDAO.read() ){
            cursoCombo.getItems().add(c);
        }

        Platform.runLater(() -> {

            if(userControl != null){

                this.nameField.setText(userControl.getNome());
                this.raField.setText(String.valueOf(userControl.getRa()));
                this.ufCombo.getSelectionModel().select(userControl.getUf());
                this.cursoCombo.getSelectionModel().select(userControl.getCurso());

                addButton.setText("Modificar");
            }

        });

    }


    @FXML
    private void modAct() throws SQLException {
        if(userControl == null) {

            int alert = JOptionPane.showConfirmDialog(
                    null, "Deseja COMPLETAR o cadastro?!",
                    "Atenção!",
                    JOptionPane.YES_NO_OPTION);

            if (alert == JOptionPane.YES_OPTION) {

                UserDAO DAO = new UserDAO();
                User u = new User();

                u.setNome(nameField.getText());
                u.setRa(Integer.valueOf(raField.getText()));
                u.setUf(ufCombo.getSelectionModel().getSelectedItem());
                u.setCurso(cursoCombo.getSelectionModel().getSelectedItem());

                DAO.create(u);
                backAct();

            }
        }
        else{
            int alert = JOptionPane.showConfirmDialog(
                    null, "Deseja MODIFICAR este item?!",
                    "Atenção!",
                    JOptionPane.YES_NO_OPTION);

            if (alert == JOptionPane.YES_OPTION) {

                UserDAO DAO = new UserDAO();
                User u = new User();

                u.setNome(nameField.getText());
                u.setRa(Integer.valueOf(raField.getText()));
                u.setUf(ufCombo.getSelectionModel().getSelectedItem());
                u.setCurso(cursoCombo.getSelectionModel().getSelectedItem());

                u.setId(userControl.getId());
                u.setSt(userControl.getSt());

                DAO.update(u);
                backAct();

            }
        }

    }


    @FXML
    private void closeAct(){

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
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

    public void setUser(User u){
        this.userControl = u;
    }

}
