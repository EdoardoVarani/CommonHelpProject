package ServerPack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by edoar on 11/01/2016.
 */
public class ServerMain extends Application {

    ServerController serverController = new ServerController();

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("/socketserverfx/server.fxml"));
        Parent root=FXMLLoader.load(getClass().getResource("/ServerPack/server.fxml"));
        primaryStage.setTitle("EdoServer v.0.1");
        primaryStage.setScene(new Scene(root, 300, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


