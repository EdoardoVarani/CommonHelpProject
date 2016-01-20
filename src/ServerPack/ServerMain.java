package ServerPack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by edoar on 11/01/2016.
 */
public class ServerMain extends Application {

    ServerController serverController;
    private int port=4321;
    private ServerSocket serverSocket;
    private AcceptorThread acceptorThread;



    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("/socketserverfx/server.fxml"));
        FXMLLoader loader = new FXMLLoader();
       // Parent root=FXMLLoader.load(getClass().getResource("/ServerPack/server.fxml"));
        loader.setLocation(getClass().getResource("/ServerPack/server.fxml"));
        Parent root=loader.load();

        primaryStage.setTitle("EdoServer v.0.1");
        primaryStage.setScene(new Scene(root, 300, 400));
        serverController = loader.getController();
        serverController.setMain(this);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void creaServer(){
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        acceptorThread = new AcceptorThread(serverSocket);
        acceptorThread.start();
    }
    public void sendToClients(String msg){
        acceptorThread.sendToClients(msg);
    }

}


