package UserPack;

/**
 * Created by edoar on 20/01/2016.
 */
public class Signals {

    private String code;
    private String message;
    private User user;

    public Signals(String code, User user){
        this.code=code;
        this.user=user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
