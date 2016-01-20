package ClientPack;

import UserPack.User;
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
    private  RegisterController registerController;
    private BufferedWriter outClient;
    private Socket clientSocket;
    private Integer port =4321;
    private ClientBoss clientBoss;
    private String hostname ="localhost";

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
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

    public void creaClient(){ //
        try {
            clientSocket = new Socket(hostname, port);  //ISTANZIO CLIENT SOCKET
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientBoss = new ClientBoss(clientSocket,this); //nuovo clientBoss, gli passo il client socket e il client main
        clientBoss.start();
    }

    public void sendMessageToServer(String msg){
        try {
           clientBoss.send(msg);
        } catch (Exception e) {e.printStackTrace();}

    }
    public void setWriter(BufferedWriter outClient){
        this.outClient=outClient;
    }
    public void authenticate(String nickname, String password, String name, String surname){

        User user= new User(nickname,password,name,surname);
      //  clientBoss.setUser(user);
        System.out.println("user: " +user.toString());
        clientBoss.sendUserToServer(user);
    }

    public void CreateRegisterScreen() {
        Parent root=null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ClientPack/register.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("EdoClient");
        // primaryStage.getIcons().add(new Image("/Images/server.png"));
        stage.setScene(new Scene(root, 500, 500));
        registerController = loader.getController();
        registerController.setMain(this);
        stage.show();

    }
}
