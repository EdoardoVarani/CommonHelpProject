package ClientPack;

import com.jfoenix.controls.JFXPasswordField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Created by edoar on 20/01/2016.
 */
public class RegisterController {

   // private Boolean isRegistering=false;
    /** Register Pane @FMXL objects */
    @FXML
    TextField nicknameField;
    @FXML TextField nameField;
    @FXML TextField surnameField;
    @FXML
    Label freeUser;
    @FXML
    JFXPasswordField passwordField;
    boolean nickFree;

    public void setNickFree(boolean nickFree) {
        this.nickFree = nickFree;
      /*  if (nickFree){
           freeUser.setText("UTENTE LIBERO, registrazione...");
        } */
    }
    private ClientMain clientMain;


    @FXML private void registerNow(){
        String regNick= nicknameField.getText();
        String regPwd= passwordField.getText();
        String regName= nameField.getText();
        String regSurn= surnameField.getText();
        System.out.println("Nick: " +regNick);
        System.out.println("pwd:"+regPwd);
        System.out.println("nome:"+regName);
        System.out.println("cognome: "+regSurn);
       // isRegistering=true;
        clientMain.registerUser(regNick,regPwd,regName,regSurn);
    }

    public void setMain(ClientMain main){
        this.clientMain=main;

    }

}
