package ClientPack;

import UserPack.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

/**
 * Created by edoar on 11/01/2016.
 */
public class ClientController {

    private  boolean connected;
    private String host="localhost";
    private int port=4321;
    private boolean isConnected;
    private ClientMain clientMain;
    @FXML
    Button clientConnectButton;
    @FXML
    TextField textToSend;
    @FXML Button sendButton;
    @FXML Button loginButton;
    @FXML Button registerButton;
    /** Register Pane @FMXL objects */
    @FXML TextField nicknameField;
    @FXML TextField passwordField;
    @FXML TextField nameField;
    @FXML TextField surnameField;
    @FXML DatePicker bornPicker;


    //EVENT LISTENERS
    @FXML  private void connectNow(){ //connect to main server
        clientMain.creaClient();
    }

    @FXML private void loginNow(){}
    @FXML private void registerAct(){
        Parent root=null;
        try {
            root= FXMLLoader.load(getClass().getResource("register.fxml"));//launch register form
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Registrazione Utente");
        if (root!=null)
        { stage.setScene(new Scene(root, 450, 450));
        stage.show();}

    }

    @FXML private void sendText(){

        System.out.println("SENDDDDD");
if (!textToSend.getText().equalsIgnoreCase("")){
        clientMain.sendMessageToServer(textToSend.getText());
   // client.sendMessageToServer(textToSend.getText());

}
    }
    public void setMain(ClientMain main){
        this.clientMain=main;

    }
    @FXML private void registerNow(){
        String regNick= nicknameField.getText();
        String regPwd= passwordField.getText();
        String regName= nameField.getText();
        String regSurn= surnameField.getText();
        LocalDate isoDate = bornPicker.getValue();
        ChronoLocalDate chronoDate =
                ((isoDate != null) ? bornPicker.getChronology().date(isoDate) : null);
        System.out.println("Nick: " +regNick);
        System.out.println("pwd:"+regPwd);
        System.out.println("nome:"+regName);
        System.out.println("cognome: "+regSurn);
        System.err.println("Selected date: " + chronoDate);
        User user=new User(regNick,regPwd,regName,regSurn,chronoDate);
        user.printUser();


    }
}
