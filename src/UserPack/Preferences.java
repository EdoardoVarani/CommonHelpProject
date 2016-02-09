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

    public Preferences(){}
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


    public boolean isScuola() {
        return scuola;
    }

    public void setScuola(boolean scuola) {
        this.scuola = scuola;
    }

    public boolean isMaking() {
        return making;
    }

    public void setMaking(boolean making) {
        this.making = making;
    }

    public boolean isReligione() {
        return religione;
    }

    public void setReligione(boolean religione) {
        this.religione = religione;
    }

    public boolean isPromozione_territorio() {
        return promozione_territorio;
    }

    public void setPromozione_territorio(boolean promozione_territorio) {
        this.promozione_territorio = promozione_territorio;
    }

    public boolean isDonazione_sangue() {
        return donazione_sangue;
    }

    public void setDonazione_sangue(boolean donazione_sangue) {
        this.donazione_sangue = donazione_sangue;
    }

    public boolean isAnziani() {
        return anziani;
    }

    public void setAnziani(boolean anziani) {
        this.anziani = anziani;
    }

    public boolean isTasse() {
        return tasse;
    }

    public void setTasse(boolean tasse) {
        this.tasse = tasse;
    }

    @Override
    public String toString() {
        return "Preferences{" +
                "user=" + user +
                ", scuola=" + scuola +
                ", making=" + making +
                ", religione=" + religione +
                ", promozione_territorio=" + promozione_territorio +
                ", donazione_sangue=" + donazione_sangue +
                ", anziani=" + anziani +
                ", tasse=" + tasse +
                '}';
    }
}
