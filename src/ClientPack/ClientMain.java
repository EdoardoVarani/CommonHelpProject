package ClientPack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by edoar on 11/01/2016.
 */
public class ClientMain extends Application {

  private  ClientController clientController;
    private Client client;
    private BufferedWriter outClient;
    private Socket clientSocket;
    private Integer port =4321;
    private ClientBoss clientBoss;
   // private ClientTask clientTask;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
       /*  Parent root = FXMLLoader.load(getClass().getResource("/ClientPack/client.fxml"));
        primaryStage.setTitle("EdoClient v.0.1");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show(); */

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ClientPack/client.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("EdoClient");
       // primaryStage.getIcons().add(new Image("/Images/server.png"));
        primaryStage.setScene(new Scene(root, 500, 500));
        clientController = loader.getController();
        clientController.setMain(this);
        primaryStage.show();
    }

    public void creaClient(){
        System.out.println("in main dentro creaclient");
        try {
            clientSocket = new Socket("localhost", port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientBoss = new ClientBoss(clientSocket,this);
        clientBoss.start();
       /* client=new Client();
        client.setMain(this);
        client.connect();
        ClientTask clientTask=new ClientTask(this, client);
        clientTask.start(); */

    }

    public void sendMessageToServer(String msg){

        System.out.println("from ClientMain: MEssage  "+msg);
        try {

           clientBoss.send(msg);
        } catch (Exception e) {e.printStackTrace();}

    }
    public void setWriter(BufferedWriter outClient){
        this.outClient=outClient;
    }

}
