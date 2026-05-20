package app.controllers;

import app.entities.Materiale;
import app.exception.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderlinjeMapper;
import app.persistence.StyklisteMapper;

import java.util.ArrayList;

public class StyklisteController {
    private final static String USER = "postgres";
    private final static String PASSWORD = "postgres";
    private final static String URL = "jdbc:postgresql://localhost:5432/Fog?currentSchema=public";
    private static final String DB = "Fog";
    private static ConnectionPool connectionPool
            = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);


    public static int antalStolper(int lengthInCm) {
        int antalStolper = 0;
        if (lengthInCm < 430) {
            antalStolper = 4;
        } else {
            antalStolper = 6;
        }

        return antalStolper;
    }

    public static int antalSpær(int lengthInCm) {
        double antalSpærInDouble = lengthInCm / 50.5;
        int antalSpær = 0;
        //Denne metode runder op
        antalSpær = (int) Math.ceil(antalSpærInDouble);
        return antalSpær;
    }

    public static ArrayList<Integer> laengdenAfRemmen(int lengthInCm) {
        ArrayList<Integer> closestRemmeLengths = new ArrayList<>();
        switch (lengthInCm) {
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

    public static Materiale udregningAfStolper(int lengthInCm, ConnectionPool connectionPool) throws DatabaseException {
        int antalStolper = antalStolper(lengthInCm);
        int længdeAfStolper = 300;
        int kostPrisStolpe = antalStolper * StyklisteMapper.getKost_pris(1, connectionPool);
        int salgsPrisStolpe = antalStolper * StyklisteMapper.getSalgs_pris(1, connectionPool);
        Materiale stolper = StyklisteMapper.getMaterialeFromVareNummer(1, connectionPool);
        stolper.setAntal(antalStolper);
        return stolper;

    }

    public static Materiale udregningAfSpær(int widthInCm, ConnectionPool connectionPool) throws DatabaseException {
        int antalSpær = antalSpær(widthInCm);

        Materiale materiale = new Materiale();
        materiale = switch (widthInCm) {
            case 240 -> StyklisteMapper.getMaterialeFromVareNummer(2, connectionPool);
            case 270 -> StyklisteMapper.getMaterialeFromVareNummer(2, connectionPool);
            case 300 -> StyklisteMapper.getMaterialeFromVareNummer(2, connectionPool);
            case 330 -> StyklisteMapper.getMaterialeFromVareNummer(3, connectionPool);
            case 360 -> StyklisteMapper.getMaterialeFromVareNummer(3, connectionPool);
            case 390 -> StyklisteMapper.getMaterialeFromVareNummer(4, connectionPool);
            case 420 -> StyklisteMapper.getMaterialeFromVareNummer(4, connectionPool);
            case 450 -> StyklisteMapper.getMaterialeFromVareNummer(5, connectionPool);
            case 480 -> StyklisteMapper.getMaterialeFromVareNummer(5, connectionPool);
            case 510 -> StyklisteMapper.getMaterialeFromVareNummer(6, connectionPool);
            case 540 -> StyklisteMapper.getMaterialeFromVareNummer(6, connectionPool);
            case 570 -> StyklisteMapper.getMaterialeFromVareNummer(7, connectionPool);
            case 600 -> StyklisteMapper.getMaterialeFromVareNummer(7, connectionPool);
            default -> materiale;
        };
        materiale.setAntal(antalSpær);
        return materiale;
    }

    public static ArrayList<Materiale> udregningAfRemme(int lengthInCm, ConnectionPool connectionPool) throws DatabaseException {
        ArrayList<Integer> remmeLengths = laengdenAfRemmen(lengthInCm);
        ArrayList<Materiale> remmeMaterialer = new ArrayList<>();

        for (int remmeLength : remmeLengths) {
            switch (remmeLength) {
                case 240 -> remmeMaterialer.add(StyklisteMapper.getMaterialeFromVareNummer(8, connectionPool));
                case 270 -> remmeMaterialer.add(StyklisteMapper.getMaterialeFromVareNummer(8, connectionPool));
                case 300 -> remmeMaterialer.add(StyklisteMapper.getMaterialeFromVareNummer(8, connectionPool));
                case 330 -> remmeMaterialer.add(StyklisteMapper.getMaterialeFromVareNummer(9, connectionPool));
                case 360 -> remmeMaterialer.add(StyklisteMapper.getMaterialeFromVareNummer(9, connectionPool));
                case 390 -> remmeMaterialer.add(StyklisteMapper.getMaterialeFromVareNummer(10, connectionPool));
                case 420 -> remmeMaterialer.add(StyklisteMapper.getMaterialeFromVareNummer(10, connectionPool));
                case 450 -> remmeMaterialer.add(StyklisteMapper.getMaterialeFromVareNummer(11, connectionPool));
                case 480 -> remmeMaterialer.add(StyklisteMapper.getMaterialeFromVareNummer(11, connectionPool));
                case 510 -> remmeMaterialer.add(StyklisteMapper.getMaterialeFromVareNummer(12, connectionPool));
                case 540 -> remmeMaterialer.add(StyklisteMapper.getMaterialeFromVareNummer(12, connectionPool));
                case 570 -> remmeMaterialer.add(StyklisteMapper.getMaterialeFromVareNummer(13, connectionPool));
                case 600 -> remmeMaterialer.add(StyklisteMapper.getMaterialeFromVareNummer(13, connectionPool));
            }
        }
        ArrayList<Integer> ids = new ArrayList<>();

        for (Materiale remmeMateriale : remme) {

            if (ids.contains(remmeMateriale.getVareNummer())) {
                remmeMateriale.setAntal(remmeMateriale.getAntal() + 1);
            } else {
                ids.add(remmeMateriale.getVareNummer());
            }

            return remmeMaterialer;
        }
    }

        public static double udregnDækprocent ( int lengthInCm, int widthInCm, ConnectionPool connectionPool) throws
        DatabaseException {


            ArrayList<Materiale> materiales =
                    StyklisteController.udregningAfRemme(540, connectionPool);
            double kostPris = 0;
            double salgsPris = 0;
            for (Materiale materiale : materiales) {
                kostPris = materiale.getKost_pris() + kostPris;

                salgsPris = materiale.getSalgs_pris() + salgsPris;
            }
            salgsPris = salgsPris +
                    StyklisteController.udregningAfStolper(540, connectionPool).getSalgs_pris() +
                    StyklisteController.udregningAfSpær(540, connectionPool).getSalgs_pris();
            kostPris = kostPris +
                    StyklisteController.udregningAfStolper(540, connectionPool).getKost_pris() +
                    StyklisteController.udregningAfSpær(540, connectionPool).getKost_pris();

            double dækProcent = salgsPris / kostPris * 100 - 100;

            return dækProcent;
        }
    }

    public static ArrayList<Materiale> stykListeMaterialer(int lengthInCm, int widthInCm, ConnectionPool connectionPool) throws DatabaseException {
        ArrayList<Materiale> stykListeMaterialer = udregningAfRemme(lengthInCm, connectionPool);
        stykListeMaterialer.add(udregningAfSpær(widthInCm, connectionPool));
        stykListeMaterialer.add(udregningAfStolper(lengthInCm, connectionPool));
        return stykListeMaterialer;
    }
    public void createOrderlines(){

    }
}
