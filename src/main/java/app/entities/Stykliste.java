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
    public void addToStykliste(Materiale materiale){
        materialer.add(materiale);
    }


    public int antalStolper(int lengthInCm){
        int antalStolper = 0;
        if(lengthInCm < 310){
            antalStolper = 4;
        }
        if(lengthInCm > 310 && lengthInCm < 620){
            antalStolper = 6;
        }
        else{
            antalStolper = 8;
        }

        return antalStolper;
    }

    public double antalSpær(int lengthInCm){
        double antalSpær = lengthInCm % 50.5;
        antalSpær = Math.ceil(antalSpær);
        return antalSpær;
    }

    public int laengdenAfRemmen(int lengthInCm){

        int wastedTree = lengthInCm - 300;
        int closestRemmeLength = 300;

        if(lengthInCm - 360 > wastedTree && (lengthInCm - 360) <= 0){
            wastedTree = lengthInCm - 360;
            closestRemmeLength = 360;
        }
        if(lengthInCm - 420 > wastedTree && (lengthInCm - 420) <= 0){
            wastedTree = lengthInCm - 420;
            closestRemmeLength = 420;
        }
        if(lengthInCm - 480 > wastedTree && (lengthInCm - 480) <= 0){
            wastedTree = lengthInCm - 480;
            closestRemmeLength = 480;
        }
        if(lengthInCm - 540 > wastedTree && (lengthInCm - 540) <= 0){
            wastedTree = lengthInCm - 540;
            closestRemmeLength = 540;

        }
        if(lengthInCm - 600 > wastedTree && (lengthInCm - 600) <= 0){
            wastedTree = (lengthInCm - 600) * 2;
            closestRemmeLength = 600;
        }
        return closestRemmeLength;

    }
    public int wastedTree(int lengthInCm){

        int wastedTree = (lengthInCm - 300) * 2;
        int closestRemmeLength = 300;

        if(lengthInCm - 360 > wastedTree && (lengthInCm - 360) <= 0){
            wastedTree = (lengthInCm - 360) * 2;
            closestRemmeLength = 360;
        }
        if(lengthInCm - 420 > wastedTree && (lengthInCm - 420) <= 0){
            wastedTree = (lengthInCm - 420) * 2;
            closestRemmeLength = 420;
        }
        if(lengthInCm - 480 > wastedTree && (lengthInCm - 480) <= 0){
            wastedTree = (lengthInCm - 480) * 2;
            closestRemmeLength = 480;
        }
        if(lengthInCm - 540 > wastedTree && (lengthInCm - 540) <= 0){
            wastedTree = (lengthInCm - 540) * 2;
            closestRemmeLength = 540;

        }
        if(lengthInCm - 600 > wastedTree && (lengthInCm - 600) <= 0){
            wastedTree = (lengthInCm - 600) * 2;
            closestRemmeLength = 600;
        }
        return wastedTree;

    }
    public ArrayList<Integer> remmeOver600cm(int lengthInCm){
        ArrayList<Integer> remmeLengths = new ArrayList<>();
        // skal laves så der kommer to længder træ med mindst muligt spildttræ
        return remmeLengths;
    }


}
