package app.controllers;

import app.entities.Materiale;
import app.exception.DatabaseException;
import app.persistence.ConnectionPool;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StyklisteControllerTest {
    private final static String USER = "postgres";
    private final static String PASSWORD = "postgres";
    private final static String URL = "jdbc:postgresql://localhost:5432/Fog?currentSchema=public";
    private static final String DB = "Fog";
    private static ConnectionPool connectionPool
            = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
    StyklisteController styklisteController = new StyklisteController();

    @Test
    void testConnection() throws SQLException {
        assertNotNull(connectionPool.getConnection());

    }
    @Test
    void antalStolper() {
        int antalStolper = styklisteController.antalStolper(420);
        assertEquals(4, antalStolper);

        int antalStolper6 = styklisteController.antalStolper(540);
        assertEquals(4, antalStolper);
    }

    @Test
    void antalSpær() {
        int antalSpær = styklisteController.antalSpær(540);
        assertEquals(11,antalSpær);

    }

    @Test
    void laengdenAfRemmen() {
        ArrayList<Integer> actualRemmeLengths = styklisteController.laengdenAfRemmen(540);
        ArrayList<Integer> expectedRemmeLengths = new ArrayList<>();
        expectedRemmeLengths.add(600);
        expectedRemmeLengths.add(600);
        assertEquals(expectedRemmeLengths, actualRemmeLengths);
    }
    @Test
    void testUdregningAfStolper() throws DatabaseException {
        Materiale stolper = StyklisteController.udregningAfStolper(240, connectionPool);
        assertEquals("Stolpe", stolper.getNavn());
        assertEquals("100x100 mm. trykimp. Stolpe", stolper.getVare_beskrivelse());
        assertEquals(StyklisteController.antalStolper(240), stolper.getAntal());
        assertEquals("Stolper nedgraves 90 cm. i jord",stolper.getHjaelpe_tekst());


    }
    @Test
    void testUdregningAfSpær() throws DatabaseException {
        Materiale spær = StyklisteController.udregningAfSpær(540, connectionPool);
        assertEquals("spær", spær.getNavn());
        assertEquals("45x195 mm spærtræ", spær.getVare_beskrivelse());
        assertEquals(StyklisteController.antalSpær(540),spær.getAntal());
        assertEquals("Spær, monteres på rem, monteres oven på remmen",spær.getHjaelpe_tekst());
    }
    @Test
    void testUdregningAfRemme() throws DatabaseException {
        ArrayList<Materiale> remme = StyklisteController.udregningAfRemme(540, connectionPool);
        for (Materiale rem: remme){
            assertEquals("rem", rem.getNavn());
            assertEquals("45x195 mm spærtræ", rem.getVare_beskrivelse());
            assertEquals(2, rem.getAntal());
            assertEquals("Remme i sider,sadles ned i stolper", rem.getHjaelpe_tekst());
        }
    }



    @Test
    void testDækprocent() throws DatabaseException {
        ArrayList<Materiale> materiales =
                StyklisteController.udregningAfRemme(540, connectionPool);
        double kostPris = 0;
        double salgsPris = 0;
        for (Materiale materiale: materiales){
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
        System.out.println(salgsPris);
        assertEquals(dækProcent, StyklisteController.udregnDækprocent(540,540, connectionPool));
    }

}
