package ClientPack;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

/**
 * Created by edoar on 20/01/2016.
 */
public class RegisterController {

   // private Boolean isRegistering=false;
    /** Register Pane @FMXL objects */
    @FXML
    TextField nicknameField;
    @FXML TextField passwordField;
    @FXML TextField nameField;
    @FXML TextField surnameField;
    @FXML
    DatePicker bornPicker;

    private ClientMain clientMain;


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
        System.out.println("Selected date: " + chronoDate);
       // isRegistering=true;

        clientMain.registerUser(regNick,regPwd,regName,regSurn);
    }

    public void setMain(ClientMain main){
        this.clientMain=main;

    }
}
