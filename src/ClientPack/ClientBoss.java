package ClientPack;

import java.io.*;
import java.net.Socket;

/**
 * Created by edoar on 20/01/2016.
 */
public class ClientBoss extends Thread {
    private Socket clientSocket;
    private ClientMain clientMain;
    private BufferedReader bufReader;
    private PrintWriter buffWriter;
    private String letto;

    public ClientBoss(Socket clientSocket, ClientMain clientMain){
        this.clientSocket=clientSocket;
        this.clientMain=clientMain;

    }

    public void run(){
        try {
            bufReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            buffWriter = new PrintWriter(this.clientSocket.getOutputStream(),true);
            System.out.println("clientboss running and buffers online.");
        } catch (Exception e){e.printStackTrace();}

        while (true){
            try {
                letto =bufReader.readLine();
                System.out.println("ricevuto server message: "+letto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void send(String msg)
    {
        try {
            buffWriter.println(msg);
            System.out.println("sended to server: "+msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
