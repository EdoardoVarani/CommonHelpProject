package ServerPack;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Created by edoar on 11/01/2016.
 */
public class ServerController{

    private boolean isConnected=false;
    private int port=4321;
    private  ServerMain serverMain;

    @FXML
    ListView recvListView;
    @FXML
    TextField textAll;
    @FXML
    Button sendAllButton, serverLogin, connectButton;


    @FXML private void sendToAll(){ // invia messaggio a tutti quelli non in modalità aereo
        String textall= textAll.getText();
        serverMain.sendToClients(textall); //main method, chiamerà la relativa send nell'acceptor
        System.out.println("shouted to all in controller");
    }
    @FXML public void connectNow(){ //Start the Server.
        serverMain.creaServer();
        System.out.println("*********** SERVER STARTED ************");
        connectButton.setVisible(false);
    }

    @FXML private void logAdminNow(){}

    public void setMain(ServerMain serverMain){
        this.serverMain=serverMain;
    }
}


