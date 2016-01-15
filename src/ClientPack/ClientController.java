package ClientPack;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Created by edoar on 11/01/2016.
 */
public class ClientController {

    private  boolean connected;
    private Client client;
    private String host="localhost";
    private int port=4321;
    private boolean isConnected;

    @FXML
    Button clientConnectButton;
    @FXML
    TextField textToSend;
    @FXML Button sendButton;
    @FXML Button loginButton;

    @FXML  private void connectNow(){ //connect to main server
        client=new Client();
        client.connect();
    }

    @FXML private void loginNow(){}
    @FXML private void sendText(){

        System.out.println("SENDDDDD");
if (!textToSend.getText().equalsIgnoreCase("")){
    client.sendMessageToServer(textToSend.getText());
}
    }
}
