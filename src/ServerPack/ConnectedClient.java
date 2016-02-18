package ServerPack;

import ComunicationPack.Post;
import UserPack.User;

import java.net.Socket;

/**
 * Created by edoar on 14/01/2016.
 */
public class ConnectedClient {

    //ATTRIBUTES
    private ServerThread serverThread;
    private Socket clientSocket;
    private User user=null;

    //CONSTRUCTORS
    public ConnectedClient(ServerThread serverThread, Socket clientSocket){
        this.serverThread = serverThread;
        this.clientSocket=clientSocket;
        //Non posso inizializzare l'usr perchè me lo deve comunicare via buffer
    }


    public void sendmsg(Post post){
        serverThread.send(post);
    }
    public  boolean getAirplane(){
       return serverThread.getAirplane();
    }

    //GETTERS & SETTERS

    public ServerThread getServerThread() {
        return serverThread;
    }

    public void setServerThread(ServerThread serverThread) {
        this.serverThread = serverThread;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
