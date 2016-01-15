package Threads;

import ClientPack.ConnectedClient;
import ServerPack.Server;

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
    Server server;

    public AcceptorThread(Server server, ServerSocket serverSocket) {
        this.server=server;
        this.serverSocket=serverSocket;
    }
    @Override
    public void run() {
        while (true) {
            System.out.println("Server is listening");
            try {
                Socket socket = serverSocket.accept();//Establish a Socket conection with client;
                System.out.println("connection accepted from :" + socket);
                connectedClients.add(new ConnectedClient(new ClientThread(server,socket),socket)); //add to my mapped connected clients;
                   connectedClients.get(connectedClients.size()-1).getClientThread().start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}