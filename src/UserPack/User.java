package UserPack;

/**
 * Created by edoar on 14/01/2016.
 */
public class User {

    private String nickname;
    private String password;
    private String username;
    private String surname;
    User user;

    //PREFERENCES


    public User(String nickname, String password, String username, String surname){
        this.nickname=nickname;
        this.password=password;
        this.username=username;
        this.surname=surname;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
