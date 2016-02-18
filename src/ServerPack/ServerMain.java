package ServerPack;

import ComunicationPack.Reporting;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ObservableList<String> obsMsg= FXCollections.observableArrayList();;


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GraphicPack/server.fxml"));
        Parent root=loader.load();
        primaryStage.setTitle("PUBBLICACOMUNICAZIONE-SERVER");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);
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
        acceptorThread = new AcceptorThread(serverSocket, this);
        acceptorThread.start();
    }
    public void sendToClients(String msg, String toWho, String title){
        acceptorThread.sendToClients(msg, toWho, title);
    }

    public void updateListView(Reporting repo){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                obsMsg.add("Segnalazione da "+repo.getUser().getNickname()+ ": "  +repo.getMsg()+System.lineSeparator());
                serverController.listView.setItems(obsMsg);
                serverController.listView.scrollTo(obsMsg);
               // serverController.listView.setItems((ObservableList) new Separator());
              //  serverController.listView.setItems((ObservableList) new Separator());
            }
        });

    }


}


