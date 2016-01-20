package ServerPack;

import java.io.*;
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
              /* if(line.startsWith("test")) {
                System.out.println("E' UN TEEEEEEEEEEEEEEEEEEEEEEEEEEEEST");
            } */ //TODO: SWITCHONE
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
}
