package ClientPack;

/**
 * Created by edoar on 15/01/2016.
 */
public class ClientTask extends Thread {
    ClientMain clientMain;
    Client client;

    public ClientTask(ClientMain clientMain, Client client){
        this.clientMain=clientMain;
        this.client=client;
    }
}
