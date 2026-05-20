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

    public int getOrderlinje_id() {
        return orderlinje_id;
    }

    public void setOrderlinje_id(int orderlinje_id) {
        this.orderlinje_id = orderlinje_id;
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

    public void setVare_nummer(int vare_nummer) {
        this.vare_nummer = vare_nummer;
    }

    public int getAntal() {
        return antal;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }

    public Materiale getMateriale() {
        return materiale;
    }
}
