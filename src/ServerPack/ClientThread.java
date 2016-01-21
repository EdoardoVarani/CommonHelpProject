package ServerPack;

import ComunicationPack.Code;
import ComunicationPack.Signals;
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
    private User user;
    private Signals sig;
    private  String checked;

    private boolean airplane;

    //CONSTRUCTOR
    public ClientThread(Socket clientSocket) {
        this.socket = clientSocket;
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

                    Gson gson =new Gson();
                    sig=gson.fromJson(line, Signals.class);
                    switch (sig.getCode()) {
                        case (Code.USERTOREGISTRATE): {
                            Gson gUser=new Gson();
                            User userToCheck =gUser.fromJson(sig.getInfos(),User.class);
                            String nameToCheck = userToCheck.getNickname();
                            /** INTERROGA IL DATABASE: C'è GIà UN USER CON QUEL NOME? */


                            Connection conn = null; //TODO: MEGLIO APRIRE LA CONNESSIONE FUORI DAI CASE
                            Statement stat = null;
                            ResultSet rs=null;
                            String driver = "com.mysql.jdbc.Driver";
                            String url ="jdbc:mysql://localhost:3307/pubblicacomunicazione?useSSL=false"; //url for jdbc connection
                            String dbUSR ="root";

                            try {
                                Class.forName(driver).newInstance();
                                conn = DriverManager.getConnection(url, dbUSR, SecurityClass.DBPASS);//Mysql password masked by SecurityClass
                                stat = conn.createStatement();
                                rs= stat.executeQuery("SELECT nickname FROM utente WHERE nickname ='"+nameToCheck+"'");
                                while (rs.next()){
                                   checked = rs.getString("nickname");
                                }
                                if (checked == null){
                                    System.out.println("Utente valido!");
                                    //TODO: sendJSON con il codice "USRVALIDO"
                                } else {
                                    System.out.println("Utente "+nameToCheck +"già in uso");
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
                            System.out.println("Era un user! Eccolo: "+ user);
                            break;
                        }
                        case (Code.AIRPLANESETTED): {
                            Gson gAirp = new Gson();
                            airplane=gAirp.fromJson(sig.getInfos(),boolean.class);
                            System.out.println("Airplane: " +airplane);
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
