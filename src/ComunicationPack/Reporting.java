package ComunicationPack;

import UserPack.User;

/**
 * Created by edoar on 11/02/2016.
 */
public class Reporting {
    private String msg;
    private User user;

    public Reporting(String msg, User user) {
        this.user=user;
        this.msg=msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
