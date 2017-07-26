package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class LibViewController implements Initializable {

    @FXML private Label closeButton;
    @FXML private Label addBookButton;
    @FXML private Label addUserButton;
    @FXML private Label addTakeButton;
    @FXML private Button logoutButton;
    @FXML private GridPane mybooksPane;
    @FXML private GridPane booksPane;
    @FXML private GridPane usersPane;
    @FXML private GridPane takesPane;

    @FXML private TextField bookSearchField;
    @FXML private ComboBox bookSearchBox;

    @FXML private Tab myTab;
    @FXML private Tab searchTab;
    @FXML private Tab userTab;
    @FXML private Tab takeTab;

    @FXML private Label nameSession;
    @FXML private Label emailSession;
    @FXML private Label admSession;
    @FXML private Label raSession;

    private User logCtrl = UserSession.session;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date nowDate = new Date();
    Date sqlNowDate = new java.sql.Date(nowDate.getTime());


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {

            nameSession.setText(logCtrl.getNome());
            emailSession.setText(logCtrl.getEmail());
            raSession.setText(String.valueOf(logCtrl.getRa()));


            if(logCtrl.getAdm() == 1) {

                myTab.setDisable(true);
                myTab.setText("");
                myTab.getStyleClass().add("wizard");

                searchTab.setDisable(false);
                userTab.setDisable(false);
                takeTab.setDisable(false);

                admSession.setText("Admin");

                drawAdmBook(booksPane);
                drawUser(usersPane);
                drawTakes(takesPane);
            }
            else{
                myTab.setDisable(false);
                searchTab.setDisable(false);

                admSession.setText("Aluno");

                userTab.setDisable(true);
                userTab.getStyleClass().add("wizard");
                takeTab.setDisable(true);
                takeTab.getStyleClass().add("wizard");

                drawBook(mybooksPane);
                drawAdmBook(booksPane);
            }
        });

    }

    @FXML
    private void addBookAct(){

        Platform.runLater(() -> {

                try {

                    FXMLLoader addBookScreen = new FXMLLoader(getClass().getResource("../view/AddBookView.fxml"));

                    Parent root =  addBookScreen.load();
                    Stage stage = (Stage) this.addBookButton.getScene().getWindow();
                    stage.setScene(new Scene(root, 720, 480));
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
        });
    }

    @FXML
    private void addUserAct(){

        Platform.runLater(() -> {

            try {

                FXMLLoader addUserScreen = new FXMLLoader(getClass().getResource("../view/AddUserView.fxml"));

                Parent root = addUserScreen.load();
                Stage stage = (Stage) this.addUserButton.getScene().getWindow();
                stage.setScene(new Scene(root, 720, 480));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void addTakeAct(){Platform.runLater(() -> {

        try {

            FXMLLoader addTakeScreen = new FXMLLoader(getClass().getResource("../view/AddTakeView.fxml"));

            Parent root = addTakeScreen.load();
            Stage stage = (Stage) this.addTakeButton.getScene().getWindow();
            stage.setScene(new Scene(root, 720, 480));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    });
    }

    @FXML
    private void bookSearchAct(){

        BookDAO bDAO = new BookDAO();

        String field = bookSearchField.getText();
        System.out.println(field);
        String box = (String) bookSearchBox.getValue();
        System.out.println(box);
       // booksPane.getChildren().clear();
        bDAO.bookSearch(field, box);
    }



    @FXML
    private void closeAct(){

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void logoutAct(){
        Platform.runLater(() -> {

            int n = JOptionPane.showConfirmDialog(
                    null, "Deseja sair?!",
                    "Atenção!",
                    JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {

                UserSession.session = null;

                try {

                    FXMLLoader loginScreen = new FXMLLoader(getClass().getResource("../view/LoginView.fxml"));

                    Parent root = loginScreen.load();
                    Stage stage = (Stage) this.logoutButton.getScene().getWindow();
                    stage.setScene(new Scene(root, 720, 480));
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }


    private void drawBook(GridPane grid){


        grid.setPadding(new Insets(20,0,20,65));
        grid.setVgap(10);

        TakeDAO tDAO = new TakeDAO();

        int i = 0;

        for(Take t: tDAO.readByUser(logCtrl.getId())){

            Pane bookContainer = new Pane();
            ImageView bookCover = new ImageView();
            Label bookTitle = new Label(t.getBook().getTitulo());
            Label bookAut = new Label();
            Label bookWarn = new Label();
            Label bookValidade = new Label();
            Label bookISBN = new Label();

            bookContainer.setPrefHeight(115);
            bookContainer.setPrefWidth(470);
            bookContainer.getStyleClass().add("bookPane");

            bookCover.setImage(new Image(getClass().getResourceAsStream("../assets/genericBook.png")));
            bookCover.setFitHeight(115);
            bookCover.setFitWidth(73);
            bookCover.setPreserveRatio(true);

            bookTitle.setPrefHeight(25);
            bookTitle.setPrefWidth(300);
            bookTitle.setLayoutX(117);
            bookTitle.setLayoutY(15);
            bookTitle.getStyleClass().add("titleStd");


            bookAut.setPrefHeight(25);
            bookAut.setPrefWidth(180);
            bookAut.setLayoutX(117);
            bookAut.setLayoutY(50);
            bookAut.getStyleClass().add("bookSubTitle");


            if(t.getDevDate() != null){
                bookWarn.setText("Devolvido");
                bookWarn.getStyleClass().add("good");
            }
            else if(t.getDevDate() == null && t.getValDate().after(nowDate)){
                bookWarn.setText("Emprestado");
                bookWarn.getStyleClass().add("attention");
            }
            else if(t.getDevDate() == null && t.getValDate().before(nowDate)){
                bookWarn.setText("Atrazado");
                bookWarn.getStyleClass().add("warning");
            }
            bookWarn.setPrefHeight(25);
            bookWarn.setPrefWidth(180);
            bookWarn.setLayoutX(117);
            bookWarn.setLayoutY(76);


            bookValidade.setText("Validade: " + sdf.format(t.getValDate()));
            bookValidade.setPrefWidth(140);
            bookValidade.setLayoutX(317);
            bookValidade.setLayoutY(70);
            bookValidade.getStyleClass().add("bookCod");


            bookISBN.setText("ISBN. " + t.getBook().getIsbn());
            bookISBN.setPrefWidth(140);
            bookISBN.setLayoutX(317);
            bookISBN.setLayoutY(86);
            bookISBN.getStyleClass().add("bookCod");


            bookContainer.getChildren().add(bookCover);
            bookContainer.getChildren().add(bookTitle);
            bookContainer.getChildren().add(bookAut);
            bookContainer.getChildren().add(bookWarn);
            bookContainer.getChildren().add(bookValidade);
            bookContainer.getChildren().add(bookISBN);


            grid.add(bookContainer, 0, i);
            i++;
        }
    }

    private void drawAdmBook(GridPane grid) {

        grid.setPadding(new Insets(20,0,20,65));
        grid.setVgap(10);

        int i = 0;

        BookDAO bDAO = new BookDAO();

        for(Book b: bDAO.read()) {

            Pane bookContainer = new Pane();
            ImageView bookCover = new ImageView();
            Label bookTitle = new Label(b.getTitulo());
            Label bookAut = new Label(b.getAutor());
            Label bookWarn = new Label();
            Button ed = new Button();
            Button rm = new Button();
            Label bookISBN = new Label("ISBN: " + b.getIsbn());

            bookContainer.setPrefHeight(115);
            bookContainer.setPrefWidth(470);
            bookContainer.getStyleClass().add("bookPane");

            bookCover.setImage(new Image(getClass().getResourceAsStream("../assets/genericBook.png")));
            bookCover.setFitHeight(115);
            bookCover.setFitWidth(73);
            bookCover.setPreserveRatio(true);

            bookTitle.setPrefHeight(25);
            bookTitle.setPrefWidth(300);
            bookTitle.setLayoutX(117);
            bookTitle.setLayoutY(15);
            bookTitle.getStyleClass().add("titleStd");


            bookAut.setPrefHeight(25);
            bookAut.setPrefWidth(180);
            bookAut.setLayoutX(117);
            bookAut.setLayoutY(50);
            bookAut.getStyleClass().add("bookSubTitle");

            if(b.getQtd() > 5){ bookWarn.setText(b.getQtd() + " Un. Disponiveis"); bookWarn.getStyleClass().add("good");}
            else if(b.getQtd() > 0){ bookWarn.setText(b.getQtd() + " Un. Disponiveis"); bookWarn.getStyleClass().add("attention");}
            else{bookWarn.setText("Indisponivel!"); bookWarn.getStyleClass().add("warning");}
            bookWarn.setPrefHeight(25);
            bookWarn.setPrefWidth(180);
            bookWarn.setLayoutX(117);
            bookWarn.setLayoutY(76);


            ed.setText("...");
            if(logCtrl.getAdm() == 0) ed.setVisible(false);
            ed.setPrefHeight(30);
            ed.setPrefWidth(30);
            ed.setLayoutX(405);
            ed.setLayoutY(5);
            ed.getStyleClass().add("blueButton");
            ed.setStyle("-fx-font-size: 12px");
            ed.setCursor(Cursor.cursor("OPEN_HAND"));
            ed.setOnMouseClicked((event) -> {
                Platform.runLater(() -> {

                    try {
                        FXMLLoader addBookScreen = new FXMLLoader(getClass().getResource("../view/AddBookView.fxml"));

                        Parent root =  addBookScreen.load();
                        AddBookController controller = addBookScreen.getController();
                        controller.setBook(b);

                        Stage stage = (Stage) this.addBookButton.getScene().getWindow();
                        stage.setScene(new Scene(root, 720, 480));
                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            });

            rm.setText("x");
            if(logCtrl.getAdm() == 0) rm.setVisible(false);
            rm.setPrefHeight(30);
            rm.setPrefWidth(30);
            rm.setLayoutX(435);
            rm.setLayoutY(5);
            rm.getStyleClass().add("redButton");
            rm.setStyle("-fx-font-size: 12px");
            rm.setCursor(Cursor.cursor("OPEN_HAND"));
            rm.setOnMouseClicked((event) ->{

                int alert = JOptionPane.showConfirmDialog(
                        null, "Deseja APAGAR o livro " + b.getTitulo() +" ?!",
                        "Atenção!",
                        JOptionPane.YES_NO_OPTION);

                if (alert == JOptionPane.YES_OPTION) {

                    BookDAO subDAO = new BookDAO();
                    subDAO.delete(b);
                    grid.getChildren().clear();
                    drawAdmBook(grid);
                }

            });


            bookISBN.setPrefWidth(140);
            bookISBN.setLayoutX(317);
            bookISBN.setLayoutY(86);
            bookISBN.getStyleClass().add("bookCod");


            bookContainer.getChildren().add(bookCover);
            bookContainer.getChildren().add(bookTitle);
            bookContainer.getChildren().add(bookAut);
            bookContainer.getChildren().add(bookWarn);
            bookContainer.getChildren().add(ed);
            bookContainer.getChildren().add(rm);
            bookContainer.getChildren().add(bookISBN);


            grid.add(bookContainer, 0, i);
            i++;
        }

    }

    private void drawUser(GridPane grid){

        grid.setPadding(new Insets(20,25,0,25));
        grid.setVgap(10);

        UserDAO uDAO = new UserDAO();

        int i = 0;

        for(User u: uDAO.read()) {


            Pane userContainer = new Pane();
            Label ra = new Label(String.valueOf(u.getRa()));
            Label nome = new Label(u.getNome());
            Label curso = new Label(u.getCurso().getNome());
            Label uf = new Label(u.getUf().getSigla());
            Label status = new Label();
            Button ed = new Button();
            Button rm = new Button();


            userContainer.setPrefHeight(60);
            userContainer.setMaxHeight(60);
            userContainer.setPrefWidth(420);
            userContainer.setMaxWidth(420);
            userContainer.getStyleClass().add("bookPane");

            ra.setLayoutY(22);
            ra.setLayoutX(5);
            ra.prefHeight(60);
            ra.prefWidth(80);
            ra.getStyleClass().add("userRA");

            nome.setLayoutX(80);
            nome.setLayoutY(5);
            nome.prefHeight(25);
            nome.prefWidth(340);

            curso.setLayoutX(80);
            curso.setLayoutY(35);
            curso.prefHeight(25);
            curso.prefWidth(120);
            curso.getStyleClass().add("bookSubTitle");

            uf.setLayoutX(240);
            uf.setLayoutY(35);
            uf.prefHeight(25);
            uf.prefWidth(10);
            uf.getStyleClass().add("bookSubTitle");

            if(u.getSt().equals(1)){ status.setText("Ocioso");status.getStyleClass().add("attention");}
            if(u.getSt().equals(2)){ status.setText("Ativo");status.getStyleClass().add("good");}
            status.setPrefHeight(20);
            status.setPrefWidth(70);
            status.setLayoutX(330);
            status.setLayoutY(32);


            ed.setText("...");
            ed.setPrefWidth(60);
            ed.setPrefHeight(60);
            ed.getStyleClass().add("blueButton");
            ed.setCursor(Cursor.cursor("OPEN_HAND"));
            ed.setOnMouseClicked((event) -> {
                Platform.runLater(() -> {

                    try {
                        FXMLLoader addUserScreen = new FXMLLoader(getClass().getResource("../view/AddUserView.fxml"));

                        Parent root = addUserScreen.load();
                        AddUserController controller = addUserScreen.getController();
                        controller.setUser(u);

                        Stage stage = (Stage) this.addUserButton.getScene().getWindow();
                        stage.setScene(new Scene(root, 720, 480));
                        stage.show();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            });


            rm.setText("x");
            rm.setPrefWidth(60);
            rm.setPrefHeight(60);
            rm.getStyleClass().add("redButton");
            rm.setCursor(Cursor.cursor("OPEN_HAND"));
            rm.setOnMouseClicked((event) -> {
                if(u.getAdm() == 0){
                    int alert = JOptionPane.showConfirmDialog(
                            null, "Deseja APAGAR o livro " + u.getNome() +" ?!",
                            "Atenção!",
                            JOptionPane.YES_NO_OPTION);

                    if (alert == JOptionPane.YES_OPTION) {

                        UserDAO subDAO = new UserDAO();
                        subDAO.delete(u);
                        grid.getChildren().clear();
                        drawUser(grid);
                    }
                }
                else{
                        JOptionPane.showConfirmDialog(
                                null, "Não é possivel Apagar um usuario do tipo Administrador! ",
                                "Atenção!",
                                JOptionPane.DEFAULT_OPTION);
                }
            });



            userContainer.getChildren().add(ra);
            userContainer.getChildren().add(nome);
            userContainer.getChildren().add(curso);
            userContainer.getChildren().add(uf);
            userContainer.getChildren().add(status);

            grid.add(userContainer, 0, i);
            grid.add(ed,1, i);
            grid.add(rm,2, i);

            i++;

        }
    }


    //Falta o rm do drawTakes

    private void drawTakes(GridPane grid){

        grid.setPadding(new Insets(20,0,0,50));
        grid.setVgap(10);


        TakeDAO tDAO = new TakeDAO();

        int i = 0;

        for(Take t: tDAO.read()) {

            Pane infoContainer = new Pane();
            Pane placeHolder = new Pane();

            Label id = new Label(String.valueOf(t.getId()));
            Label nome = new Label(t.getUser().getNome());
            Label titulo = new Label(t.getBook().getTitulo());
            Label status = new Label();

            Button ok = new Button("+");
            Button rm = new Button("x");

            Label retirada = new Label("Retirada:");

            Label retDate = new Label(sdf.format(t.getRetDate()));
            Label devolucao = new Label("Devolução:");
            Label devDate = new Label();
            Label validade = new Label("Validade:");
            Label valDate = new Label(sdf.format(t.getValDate()));

            infoContainer.setPrefHeight(100);
            infoContainer.setPrefWidth(400);
            infoContainer.getStyleClass().add("bookPane");

            placeHolder.setPrefHeight(100);
            placeHolder.setPrefWidth(100);
            placeHolder.getStyleClass().add("bookPane");

            id.setLayoutX(14);
            id.setLayoutY(7);
            id.getStyleClass().add("bookCod");


            nome.setLayoutX(14);
            nome.setLayoutY(26);
            nome.setPrefHeight(15);
            nome.setPrefWidth(250);


            titulo.setLayoutX(14);
            titulo.setLayoutY(50);
            titulo.setPrefHeight(15);
            titulo.setPrefWidth(250);

            if(t.getDevDate() != null){
                status.setText("Devolvido");
                status.getStyleClass().add("good");
            }
            else if(t.getDevDate() == null && t.getValDate().after(nowDate)){
                status.setText("Emprestado");
                status.getStyleClass().add("attention");
            }
            else if(t.getDevDate() == null && t.getValDate().before(nowDate)){
                status.setText("Atrazado");
                status.getStyleClass().add("warning");
            }
            status.setLayoutX(286);
            status.setLayoutY(8);
            status.setPrefHeight(25);
            status.setPrefWidth(100);


            retirada.setLayoutX(14);
            retirada.setLayoutY(79);
            retirada.setPrefHeight(15);
            retirada.setPrefWidth(50);
            retirada.getStyleClass().add("bookCod");

            retDate.setLayoutX(66);
            retDate.setLayoutY(80);
            retDate.setPrefHeight(15);
            retDate.getStyleClass().add("bookCod");


            devolucao.setLayoutX(260);
            devolucao.setLayoutY(79);
            devolucao.setPrefHeight(15);
            devolucao.setPrefWidth(60);
            devolucao.getStyleClass().add("bookCod");

            if(t.getDevDate() != null) devDate.setText(sdf.format(t.getDevDate()));
            devDate.setLayoutX(325);
            devDate.setLayoutY(80);
            devDate.setPrefHeight(15);
            devDate.getStyleClass().add("bookCod");


            validade.setLayoutX(140);
            validade.setLayoutY(79);
            validade.setPrefHeight(15);
            validade.setPrefWidth(50);
            validade.getStyleClass().add("bookCod");

            valDate.setLayoutX(195);
            valDate.setLayoutY(80);
            valDate.setPrefHeight(15);
            valDate.getStyleClass().add("bookCod");

            infoContainer.getChildren().add(id);
            infoContainer.getChildren().add(nome);
            infoContainer.getChildren().add(titulo);
            infoContainer.getChildren().add(status);
            infoContainer.getChildren().add(retirada);
            infoContainer.getChildren().add(retDate);
            infoContainer.getChildren().add(devolucao);
            infoContainer.getChildren().add(devDate);
            infoContainer.getChildren().add(validade);
            infoContainer.getChildren().add(valDate);


            ok.setLayoutY(0);
            ok.setLayoutX(50);
            ok.setPrefHeight(50);
            ok.setPrefWidth(50);
            ok.getStyleClass().add("greenButton");
            ok.setCursor(Cursor.cursor("OPEN_HAND"));
            ok.setOnMouseClicked((event) -> {

                TakeDAO subDAO = new TakeDAO();

                t.setDevDate(sqlNowDate);
                t.setSt(2);
                ok.getStyleClass().add("grayButton");

                subDAO.validate(t);
                grid.getChildren().clear();
                drawTakes(grid);

            });



            rm.setLayoutY(0);
            rm.setLayoutX(50);
            rm.setPrefHeight(50);
            rm.setPrefWidth(50);
            rm.getStyleClass().add("redButton");
            rm.setCursor(Cursor.cursor("OPEN_HAND"));
            rm.setOnMouseClicked((event) -> {
                int alert = JOptionPane.showConfirmDialog(
                        null, "Deseja APAGAR o emprestimo do livro " + t.getBook().getTitulo() +" por " + t.getUser().getNome() + " ?!",
                        "Atenção!",
                        JOptionPane.YES_NO_OPTION);

                if (alert == JOptionPane.YES_OPTION) {

                    TakeDAO subDAO = new TakeDAO();
                    subDAO.delete(t);
                    grid.getChildren().clear();
                    drawTakes(grid);
                }
            });

            placeHolder.getChildren().add(ok);

            grid.add(infoContainer, 0, i);

            if( devDate.getText().equals("")) {
                grid.add(placeHolder, 1, i);
            }
            else{
                placeHolder.getChildren().remove(ok);
                placeHolder.getChildren().add(rm);
                grid.add(placeHolder, 1, i);
            }

            i++;
        }
    }

}
