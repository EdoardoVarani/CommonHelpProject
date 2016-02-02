package ServerPack;

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

    //CONSTRUCTOR
    public AcceptorThread( ServerSocket serverSocket) {
        this.serverSocket=serverSocket;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Server is listening...");
            try {
                Socket socket = serverSocket.accept();//Establish a Socket conection with client;
                System.out.println("connection accepted from :" + socket);
               // connectedClients.add(new ConnectedClient(new ClientThread(socket,this.connectedClients.get(connectedClients.size()-1)),socket)); //add to my mapped connected clients;
                  connectedClients.add(new ConnectedClient(new ClientThread(socket),socket));
                   connectedClients.get(connectedClients.size()-1).getClientThread().start(); //start single ClientThread
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendToClients(String msg){
        for (int i=0; i<connectedClients.size(); i++) {
            if (!connectedClients.get(i).getAirplane()) { //
                connectedClients.get(i).sendmsg(msg);
            }
        }
    }

    //todo: ADESSO ho connectedclient.getuser! Posso scrivere ai client che voglio
   /* public void sendToNotAirplane(String msg){
        for (int i=0;i<connectedClients.size()-1;i++){
          if (connectedClients.get(i).getClientThread().getAirplane()==false){
           connectedClients.get(i).sendmsg(msg);
           }
        }
    } */
}