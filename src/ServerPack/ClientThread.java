package ServerPack;

import ComunicationPack.Code;
import ComunicationPack.Post;
import ComunicationPack.Reporting;
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
    private Connection conn= null;
    private Statement stat;
    private ServerMain serverMain;

    private User user;


    private Preferences prefs;
    private boolean airplane;
    private AcceptorThread acceptorThread;

    //CONSTRUCTOR
    public ClientThread(Socket clientSocket, AcceptorThread acceptorThread, ServerMain serverMain) {
        this.socket = clientSocket;

        this.acceptorThread=acceptorThread;
        this.serverMain=serverMain;
    }
    public void run() {
        try { //APRO I BUFFERS PER OGNI NUOVO CLIENT
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(),true);
            String driver = "com.mysql.jdbc.Driver";
            String url ="jdbc:mysql://localhost:3307/pubblicacomunicazione?useSSL=false"; //url for jdbc connection
            String dbUSR ="root";
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, dbUSR, SecurityClass.DBPASS);//Mysql password masked by SecurityClass
            stat = conn.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
        try{
            if (in != null){
                while ((line=in.readLine()) != null) //Read from InputStream (Client OutputStream)
                {
                    System.out.println(line+ " on socket:"+socket);

                    ResultSet rs=null;
                    stat = conn.createStatement();
                    Gson gson =new Gson();
                    sig=gson.fromJson(line, Signals.class);
                    System.out.println("CODICE: "+sig.getCode());
                    switch (sig.getCode()) {

                        case (Code.USERTOREGISTRATE): {
                            Gson gUser=new Gson();
                            User userToCheck =gUser.fromJson(sig.getInfos(),User.class);
                            String nameToCheck = userToCheck.getNickname();

                            /** INTERROGA IL DATABASE: C'è GIà UN USER CON QUEL NOME? */
                            try {

                                rs= stat.executeQuery("SELECT nickname FROM utente WHERE nickname ='"+nameToCheck+"'"); //CHECK DB FOR FREE NICKNAME
                                while (rs.next()){
                                    checked = rs.getString("nickname");
                                }
                                if (checked==null){
                                    System.out.println("Utente valido!");
                                    this.user=userToCheck; //SE Valido, inizializzo il mio user
                                    // connectedClient.setUser(user);
                                    String insert="INSERT INTO utente(nickname, password, nome, cognome) " +
                                            "VALUES ('"+user.getNickname() + "','" +user.getPassword() + "','" + user.getUsername() + "','" + user.getSurname()+"')";
                                    stat.executeUpdate(insert);//inserisce il nuovo utente
                                    stat.executeUpdate("INSERT INTO preferenze(nickname,scuola,making,religione,promozione_territorio,donazione_sangue,anziani,tasse)"+
                                            "VALUES ('"+user.getNickname()+"',FALSE ,FALSE ,FALSE ,FALSE ,FALSE,FALSE ,FALSE )");
                                    System.out.println("utente "+userToCheck.getNickname()+" inserito nel database.");
                                    Signals oknick= new Signals(Code.NICKNAMEFREE, sig.getInfos());
                                    Gson okgson = new Gson();
                                    String json = okgson.toJson(oknick);
                                    output.println(json); //Sendo al client che il nick è libero
                                    checked=null;
                                } else {
                                    System.out.println("Utente "+nameToCheck +"già in uso");
                                    Signals nickBusy = new Signals(Code.NICKNAMEBUSY);
                                    Gson busygson = new Gson();
                                    String json = busygson.toJson(nickBusy);
                                    output.println(json);//Sendo al Client che il nome è occupato.
                                    checked=null;
                                }
                            } catch (SQLException e){
                                e.printStackTrace();
                            }
                            System.out.println("Era un user! Eccolo: "+ user);
                            break;
                        }
                        case (Code.AIRPLANESETTED): {
                            Gson gAirp = new Gson();
                            airplane=gAirp.fromJson(sig.getInfos(),boolean.class);
                            System.out.println("Airplane: " +airplane);
                            break;
                        }
                        case (Code.UPDATEPREFS): {
                            Gson gPrefs = new Gson();
                            prefs = gPrefs.fromJson(sig.getInfos(),Preferences.class);
                            System.out.println("ECCO LE PREFERENZE AGGIORNATE di" +prefs.getUser().getNickname());
                            stat.execute("UPDATE preferenze SET scuola ="+prefs.isScuola()+",making="+prefs.isMaking()+",religione="+prefs.isReligione()+",promozione_territorio="+prefs.isPromozione_territorio()+",donazione_sangue="+prefs.isDonazione_sangue()+",anziani="+prefs.isAnziani()+",tasse="+prefs.isTasse()+" WHERE nickname='"+prefs.getUser().getNickname()+"' ");
                            break;
                        }
                        /** Ricevo dal client user e password del login.
                         * se riconosco l'usr nel Database, gli fornisco anche nome e cognome e tutte le preferenze.
                         * Altrimenti, rejecto la richiesta.*/

                        case (Code.USERTOLOGIN): {
                            Gson glog= new Gson();
                            User usrTologin = glog.fromJson(sig.getInfos(), User.class);
                            System.out.println("USR TO LOGIN: "+usrTologin.getNickname());
                            try {
                                checked="";


                                rs = stat.executeQuery("SELECT nickname FROM utente WHERE nickname='"+usrTologin.getNickname()+"' AND password='"+usrTologin.getPassword()+"'");
                                while (rs.next()){
                                    checked = rs.getString("nickname");
                                }
                                if (checked.equals(usrTologin.getNickname())){
                                    System.out.println("utente "+checked+ " riconosciuto.");
                                    this.user=usrTologin;

                                    System.out.println("Sto per eseguire la query. user= "+user.getNickname());
                                    ResultSet resultSet= stat.executeQuery("SELECT * FROM utente JOIN preferenze ON utente.nickname = preferenze.nickname WHERE preferenze.nickname='"+user.getNickname()+"'");

                                    prefs = new Preferences();
                                    prefs.setUser(user);
                                    while (resultSet.next()){
                                        prefs.setScuola(resultSet.getBoolean("scuola"));
                                        prefs.setMaking(resultSet.getBoolean("making"));
                                        prefs.setReligione(resultSet.getBoolean("religione"));
                                        prefs.setPromozione_territorio(resultSet.getBoolean("promozione_territorio"));
                                        prefs.setDonazione_sangue(resultSet.getBoolean("donazione_sangue"));
                                        prefs.setAnziani(resultSet.getBoolean("anziani"));
                                        prefs.setTasse(resultSet.getBoolean("tasse"));
                                    }

                                    System.out.println("USR:"+prefs.getUser().getNickname());
                                    System.out.println("anziani: "+prefs.isAnziani());
                                    System.out.println(prefs.toString());
                                    Gson gprefs= new Gson();
                                    String SerPrefs= gprefs.toJson(prefs);
                                    Signals allprefsSig = new Signals(Code.SENDALLPREFS,SerPrefs);
                                    Gson gsig= new Gson();
                                    String json = gsig.toJson(allprefsSig);
                                    output.println(json); //sendo al client tutte le prefs
                                    /*System.out.println("USER: "+user);
                                    System.out.println("Connclient: "+connectedClient);
                                    connectedClient.setUser(this.user);
*/

                                }
                                else {
                                    System.out.println("utente" +checked+" non trovato.");
                                    Signals noUserSig = new Signals(Code.WRONGUSER);
                                    Gson wrongGson = new Gson();
                                    String json= wrongGson.toJson(noUserSig, Signals.class);
                                    output.println(json);
                                    checked=null;

                                }
                            } catch (SQLException e){e.printStackTrace();}
                            break;
                        }
                        case (Code.SENDREPOTOSERVER):{
                            Gson grep= new Gson();
                            Reporting report=gson.fromJson(sig.getInfos(), Reporting.class);
                            serverMain.updateListView(report);

                        }
                    }//switch END
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void send(Post post){

        Gson gpost= new Gson();
        String serpost= gpost.toJson(post);
        Signals sigPost= new Signals(Code.SENDMESSAGE, serpost);
        Gson gson= new Gson();
        String json= gson.toJson(sigPost);
        try {
            output.println(json);
            System.out.println("Message sent.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  boolean getAirplane() {
        System.out.println("aeroplano: "+airplane);
        return airplane;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Preferences getPrefs() {
        return prefs;
    }

    public void setPrefs(Preferences prefs) {
        this.prefs = prefs;
    }

}
