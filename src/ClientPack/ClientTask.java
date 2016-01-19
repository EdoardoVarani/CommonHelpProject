package ClientPack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by edoar on 15/01/2016.
 */
public class ClientTask extends Thread {
    ClientMain clientMain;
    Client client;
    private Socket socket;
    public int port;
    private ClientController clientController;
    static PrintWriter out;
    BufferedReader in;
    private String hostname= "localhost";
    private Socket clientsocket;

    public ClientTask(ClientMain clientMain, Client client, int port, ClientController clientController){
        this.clientMain=clientMain;
        this.client=client;
        this.port=port;
        this.clientController=clientController;
    }
    public ClientTask(Socket clientSocket, int port, Client client){
        this.clientsocket=clientSocket;
        this.port=port;
        this.client=client;
    }

    public void run(){
        try {
            socket = new Socket(hostname, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("connection accepted"+socket.getInetAddress()+socket.getPort());

        try {
        in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {e.printStackTrace();}
        System.out.println("CLientTask Buffers online");
        while (true){
            String messaggio = null;
            try {
                messaggio = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (messaggio !=null){
                if (messaggio.startsWith("prova: "))
                    System.out.println("Hei, è un messaggio di tipo prova");
            }
        }
    }
    public static void send(String msg){
        out.println(msg);
    }
    public static void sendToServer(String msg)
    {}
}
