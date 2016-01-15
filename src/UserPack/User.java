package UserPack;

import java.time.chrono.ChronoLocalDate;

/**
 * Created by edoar on 14/01/2016.
 */
public class User {
    private String nickname;
    private String password;
    private String username;
    private String surname;
    private ChronoLocalDate born;
    User user;

    //PREFERENCES


    public User(String nickname, String password, String username, String surname, ChronoLocalDate born ){
        this.user=user;
    }


    public void printUser(){
        System.out.println("from printuser");
        System.out.println(user);
    }
//GETTERS & SETTERS

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
