package ClientPack;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

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
    @FXML
    Label welcomeLabel, regLabel;
    @FXML
    JFXButton prefSendButton;

    @FXML
    JFXTabPane clientTab;
    @FXML
    JFXCheckBox scuolaBox, makingBox, religioneBox, promozione_territorioBox, donazione_sangueBox, anzianiBox, tasseBox;
    @FXML ListView listView;
    @FXML ImageView connectionUP;
    @FXML ImageView connectionDOWN;






    @FXML CheckBox airplane;
    @FXML private void Aireplaine(){
       airplane.isSelected();
        clientMain.airplaineChanged(airplane.isSelected());
    }

    //EVENT LISTENERS
    @FXML  private void connectNow(){ //connect to main server

        clientMain.creaClient();
    }
    @FXML private void loginNow(){
        clientMain.createLoginScreen();
    }
    @FXML private void registerAct(){
        clientMain.createRegisterScreen();
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

    @FXML private void changePrefs(){
        System.out.println("Premuto changeprefs");


        clientMain.updatePrefences(scuolaBox.isSelected(), makingBox.isSelected(),
                religioneBox.isSelected(),
                promozione_territorioBox.isSelected(),donazione_sangueBox.isSelected(),
                anzianiBox.isSelected(),tasseBox.isSelected());//nlabla.isSelected...
        //TODO: senda le preferences al server
              }

    public void launchToMessagging(){

        System.out.println("in launchtomessaging");
        SingleSelectionModel<Tab> selectionModel = clientTab.getSelectionModel();
//        SingleSelectionModel<Tab> selectionModel =clientTab.getSelectionModel();
        selectionModel.select(1); // select by index starting with 0
    }

}
