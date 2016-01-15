package ServerPack;

import Threads.AcceptorThread;

import java.io.IOException;
import java.net.ServerSocket;



/**
 * Created by edoar on 11/01/2016.
 */
public class Server {
    private ServerSocket serverSocket;
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void connect() {

        Server server = new Server(port);

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread acceptorThread;
        acceptorThread = new AcceptorThread(server, serverSocket);
        acceptorThread.start();

    }

}

