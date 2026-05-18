package app.controllers;

import app.entities.Materiale;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StyklisteControllerTest {
    StyklisteController styklisteController = new StyklisteController();
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
    void testUdregningAfStolper(){
        Materiale stolper = StyklisteController.udregningAfStolper(240);
        assertEquals("Stolpe", stolper.getNavn());
        assertEquals("100x100 mm. trykimp. Stolpe", stolper.getVare_beskrivelse());
        assertEquals(StyklisteController.antalStolper(240), stolper.getAntal());
        assertEquals("Stolper nedgraves 90 cm. i jord",stolper.getHjaelpe_tekst());


    }
    @Test
    void testUdregningAfSpær(){
        Materiale spær = StyklisteController.udregningAfSpær(540);
        assertEquals("Spær", spær.getNavn());
        assertEquals("45x195 mm. spærtræ ubh.", spær.getVare_beskrivelse());
        assertEquals(StyklisteController.antalSpær(540),spær.getAntal());
        assertEquals("Spær, monteres på rem",spær.getHjaelpe_tekst());
    }
    @Test
    void testUdregningAfRemme(){
        ArrayList<Materiale> remme = StyklisteController.udregningAfRemme(540);
        for (Materiale rem: remme){
            assertEquals("Rem", rem.getNavn());
            assertEquals("45x195 mm. spærtræ ubh.", rem.getVare_beskrivelse());
            assertEquals(1, rem.getAntal());
            assertEquals("Remme i sider,sadles ned i stolper", rem.getHjaelpe_tekst());
        }
    }



    @Test
    void testDækprocent(){
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
        System.out.println(dækProcent);
        assertEquals(dækProcent, StyklisteController.udregnDækprocent(540,540));
    }

}
