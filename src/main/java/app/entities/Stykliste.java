package app.entities;

import java.util.ArrayList;

public class Stykliste {
    private int styklist_id;
    private int bruger_id;
    ArrayList<Materiale> materialer = new ArrayList<>();


    public Stykliste(int styklist_id,int bruger_id, ArrayList<Materiale> materialer){
        this.styklist_id = styklist_id;
        this.bruger_id = bruger_id;
        this.materialer = materialer;
    }
    public Stykliste(int styklist_id,int bruger_id){
        this.styklist_id = styklist_id;
        this.bruger_id = bruger_id;
    }

    public int getStyklist_id() {
        return styklist_id;
    }

    public void setStyklist_id(int styklist_id) {
        this.styklist_id = styklist_id;
    }

    public int getBruger_id() {
        return bruger_id;
    }

    public void setBruger_id(int bruger_id) {
        this.bruger_id = bruger_id;
    }
}
