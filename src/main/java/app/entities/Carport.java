package app.entities;

public class Carport {
    private int carport_id;
    private int carport_bredde;
    private int carport_laengde;
    private  double pris;
    private String status;
    private int stykliste_id;
    private int bruger_id;

    public Carport(int carport_id, int carport_bredde, int carport_laengde, double pris, String status, int bruger_id, int stykliste_id) {
        this.carport_id = carport_id;
        this.carport_bredde = carport_bredde;
        this.carport_laengde = carport_laengde;
        this.pris = pris;
        this.status = status;
        this.bruger_id = bruger_id;
        this.stykliste_id = stykliste_id;
    }

    public Carport(int carport_id, int carport_bredde, int carport_laengde, double pris, String status, int stykliste_id) {
        this.carport_id = carport_id;
        this.carport_bredde = carport_bredde;
        this.carport_laengde = carport_laengde;
        this.pris = pris;
        this.status = status;
        this.stykliste_id = stykliste_id;
    }

    public int getCarport_id() {
        return carport_id;
    }

    public void setCarport_id(int carport_id) {
        this.carport_id = carport_id;
    }

    public int getCarport_bredde() {
        return carport_bredde;
    }

    public void setCarport_bredde(int carport_bredde) {
        this.carport_bredde = carport_bredde;
    }

    public int getCarport_laengde() {
        return carport_laengde;
    }

    public void setCarport_laengde(int carport_laengde) {
        this.carport_laengde = carport_laengde;
    }

    public double getPris() {
        return pris;
    }

    public void setPris(double pris) {
        this.pris = pris;
    }

    public String getStatus() {
        return status;
    }


    public int getStykliste_id() {
        return stykliste_id;
    }

    public void setStykliste_id(int stykliste_id) {
        this.stykliste_id = stykliste_id;
    }

    public int getBruger_id() {
        return bruger_id;
    }

    public void setBruger_id(int bruger_id) {
        this.bruger_id = bruger_id;
    }
}
