package ComunicationPack;

/**
 * Created by edoar on 09/02/2016.
 */
public class Post { //racchiude il messaggio e la preferenza associata.
    private String toWho;
    private String title;
    private String message;


    public Post(String toWho,String title, String message){
        this.toWho=toWho;
        this.title=title;
        this.message= message;
    }

    //GETTERS AND SETTERS
    public String getToWho() {
        return toWho;
    }

    public void setToWho(String toWho) {
        this.toWho = toWho;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }
}
