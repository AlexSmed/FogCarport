package app.entities;

public class Carport {
    private int carport_id;
    private int carport_bredde;
    private int carport_laengde;
    private  int pris;
    private String status;

    public Carport(int carport_id, int carport_bredde, int carport_laengde, int pris, String status) {
        this.carport_id = carport_id;
        this.carport_bredde = carport_bredde;
        this.carport_laengde = carport_laengde;
        this.pris = pris;
        this.status = status;
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

    public int getPris() {
        return pris;
    }

    public void setPris(int pris) {
        this.pris = pris;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
