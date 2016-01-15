package ClientPack;

import ServerPack.ClientThread;
import UserPack.User;

import java.net.Socket;

/**
 * Created by edoar on 14/01/2016.
 */
public class ConnectedClient {

    //ATTRIBUTES
    private ClientThread clientThread;
    private Socket clientSocket;
    private User user=null;



    public ConnectedClient(ClientThread clientThread, Socket clientSocket){
        this.clientThread=clientThread;
        this.clientSocket=clientSocket;
        //Non posso inizializzare l'usr perchè me lo deve comunicare via buffer
    }

    //GETTERS & SETTERS

    public ClientThread getClientThread() {
        return clientThread;
    }

    public void setClientThread(ClientThread clientThread) {
        this.clientThread = clientThread;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }



}
