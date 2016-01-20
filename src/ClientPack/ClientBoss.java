package ClientPack;

import UserPack.Signals;
import UserPack.User;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

/**
 * Created by edoar on 20/01/2016.
 */

//CIAO
public class ClientBoss extends Thread {
    private Socket clientSocket;
    private ClientMain clientMain;
    private BufferedReader bufReader;
    private PrintWriter buffWriter;
    private String letto;
    private Gson gson;


    private User user;

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


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void sendUserToServer(User user){
        this.user=user;
        Signals signal = new Signals("TiDoUser", user);
        Gson gson = new Gson();
        String json = gson.toJson(signal);
        System.out.println("from clientBoss: " +user.getUsername()+user.getPassword());
        buffWriter.println(json);
    }

}
