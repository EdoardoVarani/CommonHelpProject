package ComunicationPack;

/**
 * Created by edoar on 20/01/2016.
 */
public class Signals { //racchiude il codice e il contenuto informativo
    private String code;
    private String infos;

    public Signals(String code){ //Per i segnali che non richiedono contenuto informativo aggiuntivo
        this.code=code;
    }
    public  Signals(String code, String infos){
        this.code=code;
        this.infos=infos;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {

        this.code = code;
    }

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

}
