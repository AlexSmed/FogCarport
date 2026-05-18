package app.controllers;

import app.entities.Materiale;

import java.util.ArrayList;

public class StyklisteController {


    public static int antalStolper(int lengthInCm){
        int antalStolper = 0;
        if(lengthInCm < 430){
            antalStolper = 4;
        }
        else{
            antalStolper = 6;
        }

        return antalStolper;
    }

    public static int antalSpær(int lengthInCm){
        double antalSpærInDouble = lengthInCm / 50.5;
        int antalSpær = 0;
        //Denne metode runder op
        antalSpær = (int) Math.ceil(antalSpærInDouble);
        return antalSpær;
    }

    public static ArrayList<Integer> laengdenAfRemmen(int lengthInCm){
        ArrayList<Integer> closestRemmeLengths = new ArrayList<>();
        switch (lengthInCm){
            case 240:
                closestRemmeLengths.add(480);
                break;
            case 270:
                closestRemmeLengths.add(540);
                break;
            case 300:
                closestRemmeLengths.add(300);
                closestRemmeLengths.add(300);
                break;
            case 330:
                closestRemmeLengths.add(360);
                closestRemmeLengths.add(360);
                break;
            case 360:
                closestRemmeLengths.add(360);
                closestRemmeLengths.add(360);
                break;
            case 390:
                closestRemmeLengths.add(420);
                closestRemmeLengths.add(420);
                break;
            case 420:
                closestRemmeLengths.add(420);
                closestRemmeLengths.add(420);
                break;
            case 450:
                closestRemmeLengths.add(480);
                closestRemmeLengths.add(480);
                break;
            case 480:
                closestRemmeLengths.add(480);
                closestRemmeLengths.add(480);
                break;
            case 510:
                closestRemmeLengths.add(540);
                closestRemmeLengths.add(540);
                break;
            case 540:
                closestRemmeLengths.add(600);
                closestRemmeLengths.add(600);
                break;
            case 570:
                closestRemmeLengths.add(600);
                closestRemmeLengths.add(600);
                break;
            case 600:
                closestRemmeLengths.add(600);
                closestRemmeLengths.add(600);
                break;
            case 630:
                closestRemmeLengths.add(600);
                closestRemmeLengths.add(600);
                closestRemmeLengths.add(300);
                break;
            case 660:
                closestRemmeLengths.add(600);
                closestRemmeLengths.add(600);
                closestRemmeLengths.add(300);
                break;
            case 690:
                closestRemmeLengths.add(600);
                closestRemmeLengths.add(600);
                closestRemmeLengths.add(300);
                break;
            case 720:
                closestRemmeLengths.add(600);
                closestRemmeLengths.add(600);
                closestRemmeLengths.add(300);
                break;
            case 750:
                closestRemmeLengths.add(600);
                closestRemmeLengths.add(600);
                closestRemmeLengths.add(360);
                break;
            case 780:
                closestRemmeLengths.add(600);
                closestRemmeLengths.add(600);
                closestRemmeLengths.add(420);
                break;
        }
        return closestRemmeLengths;
    }
    public static Materiale udregningAfStolper(int lengthInCm){
        int antalStolper = antalStolper(lengthInCm);
        int længdeAfStolper = 300;
        int kostPrisStolpe = antalStolper * 69;
        int salgsPrisStolpe = antalStolper * 99;
        Materiale stolper = new Materiale(
                1,"Stolpe", "100x100 mm. trykimp. Stolpe",
                "Stolper nedgraves 90 cm. i jord", længdeAfStolper, 30,
                0, kostPrisStolpe, salgsPrisStolpe, antalStolper
        );
        return stolper;

    }
    public static Materiale udregningAfSpær(int lengthInCm){
        int antalSpær = antalSpær(lengthInCm);
        double kostPrisSpær = (lengthInCm / 100) * 37 * antalSpær;
        double salgsPrisSpær = (lengthInCm / 100) * 44 * antalSpær;
        Materiale spær  = new Materiale(2,"Spær",
                "45x195 mm. spærtræ ubh.",
                "Spær, monteres på rem",lengthInCm,
                45,0,kostPrisSpær,salgsPrisSpær, antalSpær);

        return spær;
    }
    public static ArrayList<Materiale> udregningAfRemme(int lengthInCm){
        ArrayList<Integer> remmeLengths = laengdenAfRemmen(lengthInCm);
        ArrayList<Materiale> remmeMaterialer = new ArrayList<>();
        for(int remmeLength : remmeLengths){
            double kostPrisRem = (remmeLength / 100) * 37;
            double salgsPrisRem = (remmeLength / 100) * 44;
            Materiale rem = new Materiale(
                    3 + remmeLengths.size(), "Rem",
                    "45x195 mm. spærtræ ubh.",
                    "Remme i sider,sadles ned i stolper",
                    remmeLength,
                    45, 0, kostPrisRem, salgsPrisRem, 1);
        }
        return remmeMaterialer;
    }

    public static double udregnDækprocent(int lengthInCm, int widthInCm){


        ArrayList<Materiale> materiales =
                StyklisteController.udregningAfRemme(540);
        double kostPris = 0;
        double salgsPris = 0;
        for (Materiale materiale: materiales){
            kostPris = materiale.getKost_pris() + kostPris;

            salgsPris = materiale.getSalgs_pris() + salgsPris;
        }
        salgsPris = salgsPris +
                StyklisteController.udregningAfStolper(540).getSalgs_pris() +
                StyklisteController.udregningAfSpær(540).getSalgs_pris();
        kostPris = kostPris +
                StyklisteController.udregningAfStolper(540).getKost_pris() +
                StyklisteController.udregningAfSpær(540).getKost_pris();

        double dækProcent = salgsPris / kostPris * 100 - 100;

        return dækProcent;
    }
}
