package app.controllers;

import java.util.ArrayList;

public class StyklisteController {

    public int antalStolper(int lengthInCm){
        int antalStolper = 0;
        if(lengthInCm < 430){
            antalStolper = 4;
        }
        else{
            antalStolper = 6;
        }

        return antalStolper;
    }

    public double antalSpær(int lengthInCm){
        double antalSpær = lengthInCm / 50.5;
        //Denne metode runder op
        antalSpær = Math.ceil(antalSpær);
        return antalSpær;
    }

    public ArrayList<Integer> laengdenAfRemmen(int lengthInCm){
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
}
