package ComunicationPack;

/**
 * Created by edoar on 20/01/2016.
 */
public class Signals {

   /* private String code;
    private String message;
    private User user;
    private Code myCode;
*/
    private String code;

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }
/*
    public boolean isAirplane() {
        return airplane;
    }

    public void setAirplane(boolean airplane) {
        this.airplane = airplane;
    } */

    private String infos;

    public  Signals(String code, String infos){
        this.code=code;
        this.infos=infos;
    }
/*
    public boolean getAirplane() {
        return airplane;
    }

    private boolean airplane;

    public Signals(String code, User user){
        this.code=code;
        this.user=user;
    }
    public Signals(Code myCode, User user){
        this.myCode=myCode;
        this.user=user;
    }
    public Signals(String code, boolean airplane){
        this.code=code;
        this.airplane = airplane;
    } */
  //  public Signals(String code, String )


/*

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    } */

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
/*
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
*/


}
