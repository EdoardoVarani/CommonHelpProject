package ClientPack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by edoar on 11/01/2016.
 */
public class ClientMain extends Application {

    ClientController clientController;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/ClientPack/client.fxml"));
        primaryStage.setTitle("EdoClient v.0.1");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }
}
