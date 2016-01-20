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


    @FXML private void sendToAll(){

        String textall= textAll.getText();
        serverMain.sendToClients(textall); //main method, chiamerà la relativa send nell'acceptor
    }
    @FXML public void connectNow(){
        serverMain.creaServer();
        System.out.println("*********** SERVER ONLINE ************");
    }

    @FXML private void logAdminNow(){}

    public void setMain(ServerMain serverMain){
        this.serverMain=serverMain;
    }
}


