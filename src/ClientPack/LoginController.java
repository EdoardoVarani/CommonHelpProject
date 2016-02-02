package ClientPack;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Created by edoar on 02/02/2016.
 */
public class LoginController {

    private ClientMain clientMain;
    @FXML
    JFXTextField nickLogin;
    @FXML
    JFXPasswordField pwdLogin;
    @FXML
    JFXButton loginNow;
    @FXML
    Label loginText;


    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    @FXML void loginClicked(){
        String nick = nickLogin.getText();
        String pwd= pwdLogin.getText();
        clientMain.loginUser(nick,pwd);
    }
}
