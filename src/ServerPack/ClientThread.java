package ServerPack;

import ComunicationPack.Code;
import ComunicationPack.Signals;
import UserPack.Preferences;
import UserPack.SecurityClass;
import UserPack.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;


/**
 * Created by edoar on 11/01/2016.
 */
/** IL SERVER GESTISCE IL SINGOLO CLIENT CONNESSO IN QUESTO THREAD*/
public class ClientThread extends Thread {

    private Socket socket;
    private BufferedReader in;
    private String line;
    private PrintWriter output;
    private Signals sig;
    private  String checked;
    private ConnectedClient connectedClient;

    private User user;
    private Preferences prefs;
    private boolean airplane;

    //CONSTRUCTOR
    public ClientThread(Socket clientSocket, ConnectedClient connectedClient) {
        this.socket = clientSocket;
        this.connectedClient=connectedClient;
    }
    public ClientThread(Socket clientsocket){
        this.socket=clientsocket;
    }
    public void run() {
        try { //APRO I BUFFERS PER OGNI NUOVO CLIENT
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(),true);
        } catch (Exception e) {
            System.out.println(e);
        }
        try{
            if (in != null){
                while ((line=in.readLine()) != null) //Read from InputStream (Client OutputStream)
                {
                    System.out.println(line+ " on socket:"+socket);
                    Connection conn = null;
                    Statement stat = null;
                    ResultSet rs=null;
                    String driver = "com.mysql.jdbc.Driver";
                    String url ="jdbc:mysql://localhost:3307/pubblicacomunicazione?useSSL=false"; //url for jdbc connection
                    String dbUSR ="root";
                    Gson gson =new Gson();
                    sig=gson.fromJson(line, Signals.class);
                    switch (sig.getCode()) {

                        case (Code.USERTOREGISTRATE): {
                            Gson gUser=new Gson();
                            User userToCheck =gUser.fromJson(sig.getInfos(),User.class);
                            String nameToCheck = userToCheck.getNickname();

                            /** INTERROGA IL DATABASE: C'è GIà UN USER CON QUEL NOME? */
                            try {
                                Class.forName(driver).newInstance();
                                conn = DriverManager.getConnection(url, dbUSR, SecurityClass.DBPASS);//Mysql password masked by SecurityClass
                                stat = conn.createStatement();
                                rs= stat.executeQuery("SELECT nickname FROM utente WHERE nickname ='"+nameToCheck+"'"); //CHECK DB FOR FREE NICKNAME
                                while (rs.next()){
                                   checked = rs.getString("nickname");
                                }
                                if (checked==null){
                                    System.out.println("Utente valido!");
                                    this.user=userToCheck; //SE Valido, inizializzo il mio user
                                   // connectedClient.setUser(user);
                                    String insert="INSERT INTO utente(nickname, password, nome, cognome) " +
                                            "VALUES ('"+user.getNickname() + "','" +user.getPassword() + "','" + user.getUsername() + "','" + user.getSurname() +"')";
                                    stat.executeUpdate(insert);//inserisce il nuovo utente
                                    Signals oknick= new Signals(Code.NICKNAMEFREE, sig.getInfos());
                                    Gson okgson = new Gson();
                                    String json = okgson.toJson(oknick);
                                    output.println(json); //Sendo al client che il nick è libero
                                } else {
                                    System.out.println("Utente "+nameToCheck +"già in uso");
                                    Signals nickBusy = new Signals(Code.NICKNAMEBUSY);
                                    Gson busygson = new Gson();
                                    String json = busygson.toJson(nickBusy);
                                    output.println(json);//Sendo al Client che il nome è occupato.
                                    checked=null;
                                }
                            } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e){
                                e.printStackTrace();
                            }
                            System.out.println("Era un user! Eccolo: "+ user);
                            break;
                        }
                        case (Code.AIRPLANESETTED): {
                            Gson gAirp = new Gson();
                            airplane=gAirp.fromJson(sig.getInfos(),boolean.class);
                            System.out.println("Airplane: " +airplane);
                            //TODO: update aereo in database
                            break;
                        }
                        case (Code.UPDATEPREFS): {
                            Gson gPrefs = new Gson();
                            prefs = gPrefs.fromJson(sig.getInfos(),Preferences.class);
                            System.out.println("ECCO LE PREFERENZE AGGIORNATE di" +prefs.getUser());
                            break;
                        }
                        case (Code.USERTOLOGIN): {
                            Gson glog= new Gson();
                            User usrTologin = glog.fromJson(sig.getInfos(), User.class);
                            System.out.println("USR TO LOGIN: "+usrTologin);
                            try {
                                Class.forName(driver).newInstance();
                                conn = DriverManager.getConnection(url, dbUSR, SecurityClass.DBPASS);//Mysql password masked by SecurityClass
                                stat = conn.createStatement();
                                rs = stat.executeQuery("SELECT nickname FROM utente WHERE nickname='"+usrTologin.getNickname()+"' AND password='"+usrTologin.getPassword()+"'");
                                while (rs.next()){
                                    checked = rs.getString("nickname");
                                }
                                if (checked==usrTologin.getNickname()){
                                    System.out.println("utente"+checked+ "riconosciuto.");
                                    //TODO: RACCOGLI GLI ALTRI DATI UTENTE E LE PREFERENZE E MANDALE AL CLIENT
                                }
                                else {
                                    Signals noUserSig = new Signals(Code.WRONGUSER);
                                    Gson wrongGson = new Gson();
                                    String json= wrongGson.toJson(noUserSig, Signals.class);
                                    output.println(json);

                                }
                            } catch (SQLException e){e.printStackTrace();}
                            break;
                        }
                    }//switch END
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void send(String msg){
        try {
            output.println(msg);
            System.out.println("message "+msg +"sent to client on socket: "+socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  boolean getAirplane() {
        System.out.println("aeroplano: "+airplane);
        return airplane;
    }

}
