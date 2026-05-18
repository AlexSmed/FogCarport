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



}
