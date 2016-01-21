package ClientPack;

import UserPack.User;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by edoar on 11/01/2016.
 */
public class ClientMain extends Application {

  private  ClientController clientController;
    private  RegisterController registerController;
    private BufferedWriter outClient;
    private Socket clientSocket;
    private Integer port =4321;
    private ClientBoss clientBoss;
    private String hostname ="localhost";

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ClientPack/client.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("EdoClient");
       // primaryStage.getIcons().add(new Image("/Images/server.png"));
        primaryStage.setScene(new Scene(root, 500, 500));
        clientController = loader.getController();
        clientController.setMain(this);
        primaryStage.show();
    }

    public void creaClient(){ //
        try {
            clientSocket = new Socket(hostname, port);  //ISTANZIO CLIENT SOCKET
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientBoss = new ClientBoss(clientSocket,this); //nuovo clientBoss, gli passo il client socket e il client main
        clientBoss.start();
    }

    public void sendMessageToServer(String msg){
        try {
           clientBoss.send(msg);
        } catch (Exception e) {e.printStackTrace();}

    }
    public void setWriter(BufferedWriter outClient){
        this.outClient=outClient;
    }

    public void airplaineChanged(boolean airplaine){
        Gson gson = new Gson();
        String json = gson.toJson(airplaine);
        clientBoss.airplaneChanged(json);
    }
    public void registerUser(String nickname, String password, String name, String surname){

        /*
        String nick=null;
        Connection conn = null;
        Statement stat = null;
        ResultSet rs=null;
        String driver = "com.mysql.jdbc.Driver";
        String url ="jdbc:mysql://localhost:3307/pubblicacomunicazione?useSSL=false"; //url for jdbc connection
        String dbUSR ="root";

        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, dbUSR, SecurityClass.DBPASS);
            stat = conn.createStatement();
            rs= stat.executeQuery("SELECT nickname FROM utente WHERE nickname ='"+nickname+"'");
            while (rs.next()){
                nick = rs.getString("nickname");
            }
            if (nick == null){
                System.out.println("Utente valido!");
            } else {
                System.out.println("Utente "+nick +"già in uso");
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (!clientBoss.isNicknameAlredyTaked(nickname)) {
            User user = new User(nickname, password, name, surname);
            clientBoss.setUser(user);
            System.out.println("user: " + user.toString());
            Gson gson = new Gson();
            String json = gson.toJson(user);
            clientBoss.sendUserToServer(json);
        } else System.out.println("Nickname già in uso"); */

        User user = new User(nickname,password, name, surname);
        Gson gson= new Gson();
        String json = gson.toJson(user);
        clientBoss.sendUserToServer(json);
    }

    public void CreateRegisterScreen() {
        Parent root=null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ClientPack/register.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("EdoClient");
        // primaryStage.getIcons().add(new Image("/Images/server.png"));
        stage.setScene(new Scene(root, 500, 500));
        registerController = loader.getController();
        registerController.setMain(this);
        stage.show();

    }
}
