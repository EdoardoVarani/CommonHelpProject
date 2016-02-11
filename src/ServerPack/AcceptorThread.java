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
               // connectedClients.add(new ConnectedClient(new ClientThread(socket,this.connectedClients.get(connectedClients.size()-1)),socket)); //add to my mapped connected clients;
                  connectedClients.add(new ConnectedClient(new ClientThread(socket,this, serverMain),socket));
                   connectedClients.get(connectedClients.size()-1).getClientThread().start(); //start single ClientThread
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
                    if (connectedClients.get(i).getClientThread().getPrefs().isScuola() && !connectedClients.get(i).getClientThread().getAirplane()){
                        connectedClients.get(i).sendmsg(post);
                    }
                }
           break; }
            case ("Making"):{
                System.out.println("Messaggio per i makers");
                Post post= new Post(Code.MAKING,title, msg);
                for (int i=0; i<connectedClients.size(); i++) {
                    if (connectedClients.get(i).getClientThread().getPrefs().isMaking() && !connectedClients.get(i).getClientThread().getAirplane()){
                        connectedClients.get(i).sendmsg(post);
                    }
                }
                break;
            }
            case ("Religione"):{
                System.out.println("Messaggio per i religiosi");
                Post post= new Post(Code.RELIGIONE,title, msg);
                for (int i=0; i<connectedClients.size(); i++) {
                    if (connectedClients.get(i).getClientThread().getPrefs().isReligione() && !connectedClients.get(i).getClientThread().getAirplane()){
                        connectedClients.get(i).sendmsg(post);
                    }
                }
                break;
            }
            case ("Attività locali"):{
                Post post= new Post(Code.PROMOZIONE_TERRITORIO,title, msg);
                for (int i=0; i<connectedClients.size(); i++) {
                    if (connectedClients.get(i).getClientThread().getPrefs().isPromozione_territorio() && !connectedClients.get(i).getClientThread().getAirplane()){
                        connectedClients.get(i).sendmsg(post);
                    }
                }
            break;}
            case ("Donazioni sangue"):{
                Post post= new Post(Code.DONAZIONE_SANGUE,title, msg);
                for (int i=0; i<connectedClients.size(); i++) {
                    if (connectedClients.get(i).getClientThread().getPrefs().isDonazione_sangue() && !connectedClients.get(i).getClientThread().getAirplane()){
                        connectedClients.get(i).sendmsg(post);
                    }
                }
           break; }
            case ("Anziani"):{
                Post post= new Post(Code.ANZIANI,title, msg);
                for (int i=0; i<connectedClients.size(); i++) {
                    if (connectedClients.get(i).getClientThread().getPrefs().isAnziani() && !connectedClients.get(i).getClientThread().getAirplane()){
                        connectedClients.get(i).sendmsg(post);
                    }
                }
                break;}
            case ("Tasse"):{
                Post post= new Post(Code.TASSE,title, msg);
                for (int i=0; i<connectedClients.size(); i++) {
                    if (connectedClients.get(i).getClientThread().getPrefs().isTasse() && !connectedClients.get(i).getClientThread().getAirplane()){
                        connectedClients.get(i).sendmsg(post);
                    }
                }
            }
        }
        System.out.println(toWho);
/*
        for (int i=0; i<connectedClients.size(); i++) {
            System.out.println(connectedClients.get(i).getClientThread().getUser().getNickname()); //TODO: ;)
            System.out.println(connectedClients.get(i).getClientThread().getPrefs().);
            if (!connectedClients.get(i).getAirplane()) { //
                connectedClients.get(i).sendmsg(msg);
            }
        } */
    }

   /* public void sendToNotAirplane(String msg){
        for (int i=0;i<connectedClients.size()-1;i++){
          if (connectedClients.get(i).getClientThread().getAirplane()==false){
           connectedClients.get(i).sendmsg(msg);
           }
        }
    } */
}