package ClientPack;

import ComunicationPack.Post;
import ComunicationPack.Reporting;
import UserPack.Preferences;
import UserPack.User;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by edoar on 11/01/2016.
 */
public class ClientMain extends Application {

    private Preferences prefs;
    //CONTROLLERS
    private  ClientController clientController;
    private  RegisterController registerController;
    private LoginController loginController;
    //CONNECTION
    private BufferedWriter outClient;
    private Socket clientSocket;
    private Integer port =4321;
    private String hostname ="localhost";
    private ClientBoss clientBoss;
    //Clients
    private boolean isNicknameFree=false;
    private boolean isClientLogged=false;
    private ObservableList<String> obsMsg= FXCollections.observableArrayList();
    //STAGES
    public Stage registerStage;
    public Stage loginStage;
    public Font robo;

    String path= "/GraphicPack/Images/teacher44.png";
    // private  final Image IMG_SCUOLA= new Image(path);

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GraphicPack/client.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("PubblicaComunicazione");
        primaryStage.getIcons().add(new Image("/GraphicPack/Images/favicon.png"));
        Scene s= new Scene(root,629,666);
        primaryStage.setScene(s);
        robo= Font.loadFont(getClass().getResourceAsStream("/GraphicPack/Fonts/Roboto-Black.ttf"),20);
       // s.getStylesheets().add("https://fonts.googleapis.com/css?family=Roboto");
        //primaryStage.setMinWidth(625);
        clientController = loader.getController();
        clientController.tabbone.setMinWidth(625);
        clientController.setMain(this);

       // clientController.tabbone.setStyle("-fx-font-family: Roboto");
        primaryStage.show();
        System.out.println("SHOWED");
       /* clientController.clientTab.setMinHeight(400);
        clientController.clientTab.setMinWidth(400); */ //TODO: NON VA
    }

    public void creaClient(){ //
        try {
            clientSocket = new Socket(hostname, port);  //ISTANZIO CLIENT SOCKET
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientBoss = new ClientBoss(clientSocket,this); //nuovo clientBoss, gli passo il client socket e il client main
        clientBoss.start();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                clientController.clientConnectButton.setDisable(true);
            }
        });
    }
    public void changeUPDOWNStatus(boolean state){ //UNA VOLTA CONNESSO AL SERVER: ->
        if (state){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    clientController.connectionDOWN.setVisible(false);
                    clientController.connectionUP.setVisible(true);
                    clientController.registerButton.setDisable(false);
                    clientController.loginButton.setDisable(false);
        }});
    }}

    public void sendMessageToServer(String msg){
        try {
           clientBoss.send(msg);
        } catch (Exception e) {e.printStackTrace();}

    }
    public void setWriter(BufferedWriter outClient){
        this.outClient=outClient;
    }

    public void airplaneChanged(boolean airplane){
        Gson gson = new Gson();
        String json = gson.toJson(airplane);
        clientBoss.airplaneChanged(json);
    }
    public void registerUser(String nickname, String password, String name, String surname){

        User user = new User(nickname,password, name, surname);
        Gson gson= new Gson();
        String json = gson.toJson(user);//impacchetto user
        clientBoss.sendToServerForRegistration(json);
    }

    public void loginUser(String nickname, String password){
        User user= new User(nickname,password); //LOGGING_USER
        Gson gson= new Gson();
        String json = gson.toJson(user);
        clientBoss.sendToServerForLogin(json);
      //  clientBoss.sendToServerForRegistration(json);
    }

    public void createRegisterScreen() {
        Parent root=null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GraphicPack/clientRegister.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        registerStage = new Stage();
        registerStage.setTitle("EdoClient");
        // primaryStage.getIcons().add(new Image("/Images/server.png"));
        registerStage.setScene(new Scene(root, 500, 500));
        registerController = loader.getController();
        registerController.setMain(this);
        registerStage.show();

    }
    public void createLoginScreen(){
        Parent root=null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GraphicPack/clientLogin.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loginStage = new Stage();
        loginStage.setTitle("LOGIN to PUBBLICACOMUNICAZIONE");
        // primaryStage.getIcons().add(new Image("/Images/server.png"));
        loginStage.setScene(new Scene(root, 255, 339));
        loginController = loader.getController();
        loginController.setClientMain(this);
        loginStage.show();

    }

    public void updatePrefences(boolean scuola,boolean making, boolean religione,
                                boolean promozione_territorio, boolean donazione_sangue,
                                boolean anziani, boolean tasse){

        prefs= new Preferences(scuola,making,religione,promozione_territorio,donazione_sangue,
                anziani,tasse);
        prefs.setUser(clientBoss.getUser());
       // System.err.println("UTENTE: "+prefs.getUser().getUsername()+prefs.getUser().getSurname());//TODO: non ho username etc
        Gson gson= new Gson();
        String json= gson.toJson(prefs);
        clientBoss.updatePrefs(json);


    }

    public boolean isNicknameFree() {
        return isNicknameFree;
    }

    public void setNicknameFree(boolean nicknameFree) {
        isNicknameFree = nicknameFree;
        if (nicknameFree){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    registerController.freeUser.setText("Nick Libero");
                    registerController.setNickFree(true);
                    registerStage.hide();
                    registerStage.close();
                  //  clientController.launchToMessagging();
                    clientController.clientConnectButton.setVisible(true);
                    clientController.welcomeLabel.setText("Benvenuto,"+clientBoss.getUser().getNickname()+"!");
                    clientController.registerButton.setVisible(false);
                    clientController.registerButton.setDisable(true);
                }
            }); //primarystage.close (in runlater)

        } else {
          Platform.runLater(new Runnable() {
              @Override
              public void run() {
                  registerController.setNickFree(false);
                  registerController.nicknameField.clear();
                  clientController.launchToMessagging();
              }
          });
        }
    }


    public boolean isClientLogged() {
        return isClientLogged;
    }

    public void setClientLogged(boolean clientLogged) {
        isClientLogged = clientLogged;

    }
    public void rejectUser(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                loginController.nickLogin.clear();
                loginController.loginText.setText("Combinazione User-Password inesistente.");
            }
        });

    }


    public void userLogged(String serPrefs){

        Gson gs= new Gson();
        prefs =gs.fromJson(serPrefs, Preferences.class);
        clientBoss.setUser(prefs.getUser());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                loginStage.hide();
                loginStage.close();
             /*   clientController.welcomeLabel.setText("Bentornato, "+prefs.getUser().getNickname());
                clientController.launchToMessagging();
                clientController.regLabel.setVisible(false);
                clientController.loginButton.setText("LOGOUT");
                clientController.registerButton.setVisible(false);
                clientController.registerButton.setDisable(true); */
                isClientLogged=true;
                clientController.loginButton.setDisable(true);
                clientController.registerButton.setDisable(true);
                clientController.clientConnectButton.setVisible(false);
                clientController.welcomeLabel.setFont(robo);
                clientController.welcomeLabel.setText("Bentornato, "+prefs.getUser().getNickname()+"!");

                clientController.scuolaBox.setSelected(prefs.isScuola());
                clientController.makingBox.setSelected(prefs.isMaking());
                clientController.religioneBox.setSelected(prefs.isReligione());
                clientController.promozione_territorioBox.setSelected(prefs.isPromozione_territorio());
                clientController.donazione_sangueBox.setSelected(prefs.isDonazione_sangue());
                clientController.anzianiBox.setSelected(prefs.isAnziani());
                clientController.tasseBox.setSelected(prefs.isTasse());
            }
        });
    }

    public void addInListView(Post post){
      // Image img= new Image(getClass().getClassLoader().getResource("CommonHelp/GrapghicPack/Images/teacher44.png").toString());
//TODO: IMMAGINI CATEGORIA NELLA NOTIFICA
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                NotificationType type = NotificationType.NOTICE;
                TrayNotification trayNotification = new TrayNotification();
                trayNotification.setTitle("NEW! "+post.getTitle()+" canale: "+post.getToWho());
                if (post.getMessage().length()>35){
                    String cutted= post.getMessage().substring(0,35)+"...";
                    trayNotification.setMessage(cutted);
                } else trayNotification.setMessage(post.getMessage());
                trayNotification.showAndDismiss(Duration.seconds(5));

                obsMsg.add("Titolo: "+post.getTitle()+System.lineSeparator()+"Post: "+post.getMessage());
                clientController.listView.setItems(obsMsg);
                clientController.listView.scrollTo(obsMsg);
               // clientController.listView.setItems((ObservableList) new Separator());
                System.out.println(obsMsg);
            /*    if (post.getToWho().equals( Code.SCUOLA)){
                    clientController.listView.setCellFactory(param -> new ListCell<String>(){
                        private ImageView imgView= new ImageView();

                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (!empty){
                                imgView.setImage(IMG_SCUOLA);
                            }
                        }
                    });} */

            }
        });
    }
    public void sendReport(String msg){
        Reporting report= new Reporting(msg, prefs.getUser());
        Gson gson= new Gson();
        String repo= gson.toJson(report);
        clientBoss.sendReport(repo);
        System.out.println("IL report: "+report.getUser().getNickname()+" "+report.getMsg());
    }
}
