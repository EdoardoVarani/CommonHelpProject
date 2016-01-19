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

    public ClientBoss(Socket clientSocket, ClientMain clientMain){
        this.clientSocket=clientSocket;
        this.clientMain=clientMain;

    }

    public void run(){
        System.out.println("clientboss running.");
        try {
            bufReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            buffWriter = new PrintWriter(this.clientSocket.getOutputStream(),true);
            System.out.println(buffWriter);

        } catch (Exception e){e.printStackTrace();}

        while (true){
            try {
                String letto =bufReader.readLine();
                System.out.println("ricevuto server message: "+letto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void send(String msg)
    {
        try {
            System.out.println("sono in clientBoss nella send, buffWrit: " +buffWriter);
          //  this.buffWriter.write(msg);
            buffWriter.println(msg);

            System.out.println(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
