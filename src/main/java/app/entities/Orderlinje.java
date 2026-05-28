package app.entities;

public class Orderlinje {

    private int orderlinje_id;
    private int stykliste_id;
    private int vare_nummer;
    private int antal;

    private Materiale materiale;

    public Orderlinje(int orderlinje_id, int stykliste_id, int vare_nummer, int antal, Materiale materiale) {
        this.orderlinje_id = orderlinje_id;
        this.stykliste_id = stykliste_id;
        this.vare_nummer = vare_nummer;
        this.antal = antal;
        this.materiale = materiale;
    }
    public Orderlinje(int orderlinje_id, int stykliste_id, int vare_nummer, int antal) {
        this.orderlinje_id = orderlinje_id;
        this.stykliste_id = stykliste_id;
        this.vare_nummer = vare_nummer;
        this.antal = antal;
    }

    public int getOrderlinje_id() {
        return orderlinje_id;
    }


    public int getStykliste_id() {
        return stykliste_id;
    }

    public void setStykliste_id(int stykliste_id) {
        this.stykliste_id = stykliste_id;
    }

    public int getVare_nummer() {
        return vare_nummer;
    }


    public int getAntal() {
        return antal;
    }

    public Materiale getMateriale() {
        return materiale;
    }
}
