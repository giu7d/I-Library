import controller.ConnectionFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.User;

import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application{

    @Override
    public void start(Stage defaultStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/LoginView.fxml"));
        root.getStylesheets().add("assets/Style.css");

        defaultStage.setTitle("ILib");
        defaultStage.setScene(new Scene(root, 720, 480));
        defaultStage.setResizable(false);
        defaultStage.initStyle(StageStyle.UNDECORATED);
        defaultStage.show();

    }


    public static void main(String[] args) throws SQLException {

        launch(args);

        Connection connection = new ConnectionFactory().getConnection();
        System.out.println("Conexão aberta!");
        connection.close();
    }

}
