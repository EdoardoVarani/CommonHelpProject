package ClientPack;

import ComunicationPack.Code;
import ComunicationPack.Signals;
import UserPack.User;
import com.google.gson.Gson;

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
    private Gson gson;
    private Signals sig;

    boolean airp;

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

        while (true){ //TODO: usare un flag bool da aggiornare su risposta del server alla query per il nuovo utente
            try {
                letto =bufReader.readLine();
                System.out.println("ricevuto server message: "+letto);
                Gson gson =new Gson();
                /** Segnale a singolo parametro per le risposte secche: es. userOccupato, a dopio parametro per passaggi di dati.*/
                 sig = gson.fromJson(letto, Signals.class);
                String code= sig.getCode();
                System.out.println("IL CODICE: "+code);
                switch (code){
                    case (Code.NICKNAMEFREE): {
                        System.out.println("il nick è libero");
                        clientMain.setNicknameFree(true);
                        break;
                    }
                    case (Code.NICKNAMEBUSY): {
                        System.out.println("nickname già in uso.");
                        clientMain.setNicknameFree(false);
                        break;
                    }
                }
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
    public void airplaneChanged(String airp){

        Signals sig = new Signals(Code.AIRPLANESETTED ,airp);
        Gson gson = new Gson();
        String json = gson.toJson(sig);
        buffWriter.println(json);

    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void sendUserToServer(String user){

        Signals sig = new Signals(Code.USERTOREGISTRATE, user);
        Gson gson = new Gson();
        String json = gson.toJson(sig);
       // System.out.println("from clientBoss: " +user.getUsername()+user.getPassword());
        buffWriter.println(json);
    }
    boolean isNicknameAlredyTaked(String nickname){
        Signals sig = new Signals(Code.CHECKIFNICKAMETAKED, nickname);
        return false; //TODO: SISTEMARE

    }

}
