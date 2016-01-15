package ClientPack;

import UserPack.User;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by edoar on 11/01/2016.
 */
public class Client {
    Socket clientSocket;
    String host="localhost";
    BufferedReader input;
    BufferedWriter output;
    User user;


    public void connect(){
        clientSocket = new Socket();
        try {
            clientSocket.connect(new InetSocketAddress(host, 4321));
            System.out.println("Client connected to server: "+ host);
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output= new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            System.out.println("buffers opened on "+this);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void sendMessageToServer(String text){

        try {

            output.write(text, 0, text.length());
            output.newLine();
            output.flush();
        }
        catch (IOException ioExc){
            ioExc.printStackTrace();
        }

    }



    public void authenticate(String token){

        //TODO: do al server user e pass. Se sono Utenti veri, abilito il client

    }
}
