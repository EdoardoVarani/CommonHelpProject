package ServerPack;

import ComunicationPack.Code;
import ComunicationPack.Signals;
import UserPack.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


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
                            user = sig.getUser();
                            System.out.println("Era un user! Eccolo: "+ user);
                            break;
                        }
                        case (Code.AIRPLANESETTED):{
                            airplane =sig.getAirplane();
                            System.out.println("Airplane: " +airplane);
                        }
                    }//CASE END
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
