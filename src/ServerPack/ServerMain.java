package ServerPack;

import ComunicationPack.Reporting;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
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
        //Parent root = FXMLLoader.load(getClass().getResource("/socketserverfx/server.fxml"));
        FXMLLoader loader = new FXMLLoader();
       // Parent root=FXMLLoader.load(getClass().getResource("/ServerPack/server.fxml"));
        loader.setLocation(getClass().getResource("/GraphicPack/server.fxml"));
        Parent root=loader.load();

        primaryStage.setTitle("EdoServer v.0.1");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);
        serverController = loader.getController();
        serverController.setMain(this);
        primaryStage.show();
       // serverController.comboSelect.requestFocus();
      //  serverController.setData();
       /* final ComboBox<String> comboSelect = new ComboBox<ObservableArray>();
        comboSelect.getItems().addAll("PRIMO", "SECONDO", "TERZO");
        comboSelect.setValue("PRIMO");
        System.out.println(comboSelect.getValue());

*/

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
       /* obsMsg.add("Segnalazione da: "+repo.getUser().getUsername()+" "+repo.getUser().getSurname()+"("
        +repo.getUser().getNickname()+") : "+repo.getMsg()); */ //TODO: VORREI USERNAME
        obsMsg.add("Segnalazione da:"+repo.getUser().getNickname()+") : "+repo.getMsg());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                serverController.listView.setItems(obsMsg);
                serverController.listView.scrollTo(obsMsg);
                serverController.listView.setItems((ObservableList) new Separator());
            }
        });

    }


}


