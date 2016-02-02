package UserPack;

/**
 * Created by edoar on 28/01/2016.
 */
public class Preferences {


    private User user;
    private boolean scuola;
    private boolean making;
    private boolean religione;
    private boolean promozione_territorio;
    private boolean donazione_sangue;
    private boolean anziani;
    private boolean tasse;

    public Preferences(boolean s, boolean m, boolean r, boolean p_t, boolean d_s, boolean a, boolean t) {
        this.scuola=s;
        this.making=m;
        this.religione=r;
        this.promozione_territorio=p_t;
        this.donazione_sangue=d_s;
        this.anziani=a;
        this.tasse=t;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
