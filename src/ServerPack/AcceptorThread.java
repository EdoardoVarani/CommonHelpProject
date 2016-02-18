package ServerPack;

import ComunicationPack.Code;
import ComunicationPack.Post;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by edoar on 12/01/2016.
 */
public class AcceptorThread extends Thread {

    public ArrayList<ConnectedClient> connectedClients = new ArrayList<ConnectedClient>();
    public ServerSocket serverSocket;
    private ServerMain serverMain;

    //CONSTRUCTOR
    public AcceptorThread(ServerSocket serverSocket, ServerMain serverMain) {
        this.serverSocket=serverSocket;
        this.serverMain=serverMain;
    }
    @Override
    public void run() {
        while (true) {
            System.out.println("Server is listening...");
            try {
                Socket socket = serverSocket.accept();//Establish a Socket conection with client;
                System.out.println("connection accepted from :" + socket);
                  connectedClients.add(new ConnectedClient(new ServerThread(socket,this, serverMain),socket));
                   connectedClients.get(connectedClients.size()-1).getServerThread().start(); //start single ServerThread
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendToClients(String msg, String toWho, String title){
/** Confeziono il post e lo invio ai client che hanno le preferenza attiva e la modalità aereo spenta.*/
        switch (toWho){
            case ("Scuola"):{
                System.out.println("messaggio scolastico");
                Post post= new Post(Code.SCUOLA,title,msg);
                for (int i=0; i<connectedClients.size(); i++) {
                    if (connectedClients.get(i).getServerThread().getPrefs().isScuola() && !connectedClients.get(i).getServerThread().getAirplane()){
                        connectedClients.get(i).sendmsg(post);
                    }
                }
           break; }
            case ("Making"):{
                System.out.println("Messaggio per i makers");
                Post post= new Post(Code.MAKING,title, msg);
                for (int i=0; i<connectedClients.size(); i++) {
                    if (connectedClients.get(i).getServerThread().getPrefs().isMaking() && !connectedClients.get(i).getServerThread().getAirplane()){
                        connectedClients.get(i).sendmsg(post);
                    }
                }
                break;
            }
            case ("Religione"):{
                System.out.println("Messaggio per i religiosi");
                Post post= new Post(Code.RELIGIONE,title, msg);
                for (int i=0; i<connectedClients.size(); i++) {
                    if (connectedClients.get(i).getServerThread().getPrefs().isReligione() && !connectedClients.get(i).getServerThread().getAirplane()){
                        connectedClients.get(i).sendmsg(post);
                    }
                }
                break;
            }
            case ("Attività locali"):{
                Post post= new Post(Code.PROMOZIONE_TERRITORIO,title, msg);
                for (int i=0; i<connectedClients.size(); i++) {
                    if (connectedClients.get(i).getServerThread().getPrefs().isPromozione_territorio() && !connectedClients.get(i).getServerThread().getAirplane()){
                        connectedClients.get(i).sendmsg(post);
                    }
                }
            break;}
            case ("Donazioni sangue"):{
                Post post= new Post(Code.DONAZIONE_SANGUE,title, msg);
                for (int i=0; i<connectedClients.size(); i++) {
                    if (connectedClients.get(i).getServerThread().getPrefs().isDonazione_sangue() && !connectedClients.get(i).getServerThread().getAirplane()){
                        connectedClients.get(i).sendmsg(post);
                    }
                }
           break; }
            case ("Anziani"):{
                Post post= new Post(Code.ANZIANI,title, msg);
                for (int i=0; i<connectedClients.size(); i++) {
                    if (connectedClients.get(i).getServerThread().getPrefs().isAnziani() && !connectedClients.get(i).getServerThread().getAirplane()){
                        connectedClients.get(i).sendmsg(post);
                    }
                }
                break;}
            case ("Tasse"):{
                Post post= new Post(Code.TASSE,title, msg);
                for (int i=0; i<connectedClients.size(); i++) {
                    if (connectedClients.get(i).getServerThread().getPrefs().isTasse() && !connectedClients.get(i).getServerThread().getAirplane()){
                        connectedClients.get(i).sendmsg(post);
                    }
                }
            }
        }
        System.out.println(toWho);
/*
        for (int i=0; i<connectedClients.size(); i++) {
            System.out.println(connectedClients.get(i).getServerThread().getUser().getNickname()); //TODO: ;)
            System.out.println(connectedClients.get(i).getServerThread().getPrefs().);
            if (!connectedClients.get(i).getAirplane()) { //
                connectedClients.get(i).sendmsg(msg);
            }
        } */
    }

   /* public void sendToNotAirplane(String msg){
        for (int i=0;i<connectedClients.size()-1;i++){
          if (connectedClients.get(i).getServerThread().getAirplane()==false){
           connectedClients.get(i).sendmsg(msg);
           }
        }
    } */
}