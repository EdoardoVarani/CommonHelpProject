package ServerPack;

import ServerPack.Server;

import java.io.*;
import java.net.Socket;


/**
 * Created by edoar on 11/01/2016.
 */
public class ClientThread extends Thread {

    private Socket socket;
    private Server serv;
    private BufferedReader in;
    private BufferedWriter out;
    private String line;

    public ClientThread(Server theServer, Socket clientSocket) {
        this.serv = theServer;
        this.socket = clientSocket;
    }

    public void run() {
        try { //APRO I BUFFERS PER OGNI NUOVO CLIENT
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.flush();
            System.out.println("buffers opened. Server:"+serv +"socket: "+socket);
        } catch (Exception e) {
            System.out.println(e);
        }

        try{

        if (in != null){
            String msg;
            while ((line=in.readLine()) != null)
            {
                System.out.println(line+ "on socket:"+socket);
            }
        }}
        catch (Exception e){e.printStackTrace();}
    }
}
