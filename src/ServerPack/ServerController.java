package ServerPack;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Created by edoar on 11/01/2016.
 */
public class ServerController {
@FXML
    ListView recvListView;
    @FXML
    TextField textAll;
    @FXML
    Button sendAllButton, serverLogin, connectButton;

    private boolean isConnected=false;
    private int port=4321;
    private Server server;
    private  ServerMain serverMain;


    @FXML private void sendToAll(){}
    @FXML public void connectNow(){
        server = new Server(port);
        server.connect();


        System.out.println("SERVER ONLINE");
    }
    @FXML private void logAdminNow(){}

}


