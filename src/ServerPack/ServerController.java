package ServerPack;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by edoar on 11/01/2016.
 */
public class ServerController implements Initializable{

    private boolean isConnected=false;
    private int port=4321;
    private  ServerMain serverMain;

    @FXML
    ListView listView;
    @FXML
    TextField textAll;
    @FXML
    JFXButton sendAllButton, connectButton;

    @FXML
    JFXComboBox<String> comboSelect;

ObservableList<String> list= FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.addAll("uno","due","tre");
        comboSelect.setItems(list);
        // comboSelect.getItems().clear();
    }

    @FXML private void sendToAll(){ // invia messaggio a tutti quelli non in modalità aereo
        String textall= textAll.getText();
        serverMain.sendToClients(textall); //main method, chiamerà la relativa send nell'acceptor
        System.out.println("shouted to all in controller");
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


