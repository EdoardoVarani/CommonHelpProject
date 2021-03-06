package ServerPack;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by edoar on 11/01/2016.
 */
public class ServerController implements Initializable{

    private boolean isConnected=false;
    private  ServerMain serverMain;

    @FXML
    JFXListView listView;
    @FXML
    JFXTextArea textAll;
    @FXML
    JFXButton sendButton, connectButton;

    @FXML
    JFXTextField titleText;
   @FXML
    ChoiceBox<String> choiceSelect;

    public static final ObservableList<String> list= FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        list.addAll("Scuola","Making","Religione","Attività locali", "Donazioni sangue", "Anziani", "Tasse");
        System.out.println(list);
       choiceSelect.setItems(list);

        // comboSelect.getItems().clear();
    }

    @FXML private void sendToAll(){ // invia messaggio a tutti quelli non in modalità aereo
       // String textall= textAll.getText();
       // serverMain.sendToClients(textall); //main method, chiamerà la relativa send nell'acceptor
        System.out.println("shouted to all in controller");
    }

    @FXML private void sendToClients(){
        System.out.println("SENDTOCLIENTS PRESSED");
        if (choiceSelect.getValue() != null) {
            serverMain.sendToClients(textAll.getText(), choiceSelect.getValue(), titleText.getText());
        } else {
            System.err.println("NESSUNA PREFERENZA!");
        }
    }
    @FXML public void connectNow(){ //Start the Server.
        serverMain.creaServer();
        System.out.println("*********** SERVER STARTED ************");
        connectButton.setVisible(false);
    }

    @FXML private void logAdminNow(){

    }

    public void setMain(ServerMain serverMain){
        this.serverMain=serverMain;
    }

}


