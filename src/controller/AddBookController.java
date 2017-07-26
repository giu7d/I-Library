package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Book;
import model.BookDAO;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddBookController implements Initializable {


    @FXML private Label closeButton;
    @FXML private Button cancelButton;
    @FXML private Button addButton;

    @FXML private TextField titleField;
    @FXML private TextField autField;
    @FXML private TextField qtdField;
    @FXML private TextField isbnField;
    @FXML private TextField editionField;

    private Book bookControl = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> {

            if(bookControl != null){

                this.titleField.setText(bookControl.getTitulo());
                this.autField.setText(bookControl.getAutor());
                this.qtdField.setText(String.valueOf(bookControl.getQtd()));
                this.isbnField.setText(bookControl.getIsbn());
                this.editionField.setText(String.valueOf(bookControl.getEdicao()));

                addButton.setText("Modificar");
            }

        });

    }


    @FXML

    private void modAct() throws SQLException {

        if(bookControl == null) {
            int alert = JOptionPane.showConfirmDialog(
                    null, "Deseja COMPLETAR o cadastro?!",
                    "Atenção!",
                    JOptionPane.YES_NO_OPTION);

            if (alert == JOptionPane.YES_OPTION) {

                BookDAO DAO = new BookDAO();
                Book b = new Book();

                b.setTitulo(titleField.getText());
                b.setAutor(autField.getText());
                b.setEdicao(Integer.valueOf(editionField.getText()));
                b.setIsbn(isbnField.getText());
                b.setQtd(Integer.valueOf(qtdField.getText()));
                b.setSt(1);

                DAO.create(b);
                backAct();
            }
        }
        else{
            int alert = JOptionPane.showConfirmDialog(
                    null, "Deseja MODIFICAR este item?!",
                    "Atenção!",
                    JOptionPane.YES_NO_OPTION);

            if (alert == JOptionPane.YES_OPTION) {

                BookDAO DAO = new BookDAO();
                Book b = new Book();

                b.setTitulo(titleField.getText());
                b.setAutor(autField.getText());
                b.setEdicao(Integer.valueOf(editionField.getText()));
                b.setIsbn(isbnField.getText());
                b.setQtd(Integer.valueOf(qtdField.getText()));

                b.setId(bookControl.getId());
                b.setSt(bookControl.getSt());

                DAO.update(b);
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


    public void setBook(Book b){
        this.bookControl = b;
    }

}
