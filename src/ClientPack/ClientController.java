package ClientPack;

import com.jfoenix.controls.*;
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
    TextField textToSend;
    @FXML Button loginButton;
    @FXML Button registerButton;
    @FXML
    Label welcomeLabel, regLabel;
    @FXML
    JFXButton prefSendButton, segnalazioneBtn;
    @FXML JFXButton clientConnectButton;
    @FXML
    JFXTextArea segnalazioneText;
    @FXML JFXTabPane tabbone;


    @FXML
    JFXTabPane clientTab;
    @FXML
    JFXCheckBox scuolaBox, makingBox, religioneBox, promozione_territorioBox, donazione_sangueBox, anzianiBox, tasseBox;
    @FXML ListView listView;
    @FXML ImageView connectionUP;
    @FXML ImageView connectionDOWN;






    @FXML CheckBox airplane;
    @FXML private void airplaneNow(){
        airplane.isSelected();
        clientMain.airplaneChanged(airplane.isSelected());
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
        clientMain.updatePrefences(scuolaBox.isSelected(), makingBox.isSelected(),
                religioneBox.isSelected(),
                promozione_territorioBox.isSelected(),donazione_sangueBox.isSelected(),
                anzianiBox.isSelected(),tasseBox.isSelected());//nlabla.isSelected...
        System.out.println("PREFERENZE INVIATE");
    }

    public void launchToMessagging(){

        System.out.println("in launchtomessaging");
        SingleSelectionModel<Tab> selectionModel = clientTab.getSelectionModel();
        selectionModel.select(1); // select by index starting with 0
    }

    @FXML private void segnalazioneNow(){
        clientMain.sendReport(segnalazioneText.getText());
    }
}
