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
    void testUdregningAfStyklisteNavnOgBeskrivelse(){
        ArrayList<Materiale> materiales = StyklisteController.udregningAfStykliste(540, 540);
        assertEquals("Stolpe", materiales.get(0).getNavn());
        assertEquals("Spær", materiales.get(1).getNavn());
        assertEquals("Rem", materiales.get(2).getNavn());

        assertEquals("97x97 mm. trykimp. Stolpe", materiales.get(0).getVare_beskrivelse());
        assertEquals("45x195 mm. spærtræ ubh.", materiales.get(1).getVare_beskrivelse());
        assertEquals("45x195 mm. spærtræ ubh.", materiales.get(2).getVare_beskrivelse());

    }
    @Test
    void testUdregningAfStyklisteAntal(){
        ArrayList<Materiale> materiales = StyklisteController.udregningAfStykliste(240, 240);
        assertEquals(StyklisteController.antalStolper(240), materiales.get(0).getAntal());

        assertEquals(StyklisteController.antalSpær(240), materiales.get(1).getAntal());

        assertEquals(1, materiales.get(2).getAntal());

    }
    @Test
    void testUdregningAfStyklisteHjaelpeTekst(){
        ArrayList<Materiale> materiales =
                StyklisteController.udregningAfStykliste(540, 540);
        assertEquals("Stolper nedgraves 90 cm. i jord", materiales.get(0).getHjaelpe_tekst());

        assertEquals("Spær, monteres på rem", materiales.get(1).getHjaelpe_tekst());

        assertEquals("Remme i sider,sadles ned i stolper", materiales.get(2).getHjaelpe_tekst());

    }
    @Test
    void testDækprocent(){
        ArrayList<Materiale> materiales =
                StyklisteController.udregningAfStykliste(540, 540);
        double kostPris = 0;
        double salgsPris = 0;

        for (Materiale materiale: materiales){
            kostPris = materiale.getKost_pris() + kostPris;

            salgsPris = materiale.getSalgs_pris() + salgsPris;
        }
        for(int i = 0; i < materiales.size(); i++){
            System.out.println(materiales.get(i).getNavn());
            System.out.println(materiales.get(i).getAntal());
            System.out.println(materiales.get(i).getLaengde());
            System.out.println(materiales.get(i).getKost_pris());
            System.out.println(materiales.get(i).getSalgs_pris());
        }
        System.out.println(kostPris);
        System.out.println(salgsPris);

        double dækProcent = salgsPris / kostPris * 100 - 100;
        System.out.println(dækProcent);
        assertEquals(dækProcent, StyklisteController.udregnDækprocent(540,540));
    }

}
