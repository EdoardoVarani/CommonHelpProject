package ClientPack;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 * Created by edoar on 11/01/2016.
 */
public class ClientController {

    private  boolean connected;
    private String host="localhost";
    private int port=4321;
    private boolean isConnected;
    private ClientMain clientMain;



    private boolean NickFree=false;
    @FXML
    Button clientConnectButton;
    @FXML
    TextField textToSend;
    @FXML Button sendButton;
    @FXML Button loginButton;
    @FXML Button registerButton;






    @FXML CheckBox airplane;
    @FXML private void Aireplaine(){
       airplane.isSelected();
        clientMain.airplaineChanged(airplane.isSelected());
    }

    //EVENT LISTENERS
    @FXML  private void connectNow(){ //connect to main server

        clientMain.creaClient();
    }
    @FXML private void loginNow(){}
    @FXML private void registerAct(){
        clientMain.CreateRegisterScreen();
    }

    @FXML private void sendText(){

        System.out.println("SENDDDDD");
if (!textToSend.getText().equalsIgnoreCase("")){
        clientMain.sendMessageToServer(textToSend.getText());
}
    }
    public void setMain(ClientMain main){
        this.clientMain=main;

    }

}
