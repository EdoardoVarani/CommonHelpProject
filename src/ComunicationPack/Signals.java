package ComunicationPack;

/**
 * Created by edoar on 20/01/2016.
 */
public class Signals {

    private String code;
    private String infos;



    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    public Signals(String code){
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


}
