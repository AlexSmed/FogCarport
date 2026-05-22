package app.entities;

import java.util.Objects;

public class Materiale {
    private int vareNummer;
    private String navn;
    private String vare_beskrivelse;
    private String hjaelpe_tekst;
    private int laengde;
    private int antal;
    private double kost_pris;

    public Materiale() {
    }

    private double salgs_pris;

    public Materiale(int vareNummer, String navn, String vare_beskrivelse, String hjaelpe_tekst,
                     int laengde, int bredde, int hoejde, double kost_pris, double salgs_pris, int antal) {

        this.vareNummer = vareNummer;
        this.navn = navn;
        this.vare_beskrivelse = vare_beskrivelse;
        this.hjaelpe_tekst = hjaelpe_tekst;
        this.laengde = laengde;
        this.bredde = bredde;
        this.hoejde = hoejde;
        this.kost_pris = kost_pris;
        this.salgs_pris = salgs_pris;
        this.antal = antal;
    }
    public Materiale(int vareNummer, String navn, String vare_beskrivelse, String hjaelpe_tekst,
                     int laengde, int bredde, int hoejde, double kost_pris, double salgs_pris) {

        this.vareNummer = vareNummer;
        this.navn = navn;
        this.vare_beskrivelse = vare_beskrivelse;
        this.hjaelpe_tekst = hjaelpe_tekst;
        this.laengde = laengde;
        this.bredde = bredde;
        this.hoejde = hoejde;
        this.kost_pris = kost_pris;
        this.salgs_pris = salgs_pris;

    }
    public int getAntal(){
        return antal;
    }
    public void setAntal(int antal){
        this.antal = antal;
    }

    public int getVareNummer() {
        return vareNummer;
    }

    public void setVareNummer(int vareNummer) {
        this.vareNummer = vareNummer;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getVare_beskrivelse() {
        return vare_beskrivelse;
    }

    public void setVare_beskrivelse(String vare_beskrivelse) {
        this.vare_beskrivelse = vare_beskrivelse;
    }

    public String getHjaelpe_tekst() {
        return hjaelpe_tekst;
    }

    public void setHjaelpe_tekst(String hjaelpe_tekst) {
        this.hjaelpe_tekst = hjaelpe_tekst;
    }

    public int getLaengde() {
        return laengde;
    }

    public void setLaengde(int laengde) {
        this.laengde = laengde;
    }

    public int getBredde() {
        return bredde;
    }

    public void setBredde(int bredde) {
        this.bredde = bredde;
    }

    public int getHoejde() {
        return hoejde;
    }

    public void setHoejde(int hoejde) {
        this.hoejde = hoejde;
    }

    public double getKost_pris() {
        return kost_pris;
    }

    public void setKost_pris(int kost_pris) {
        this.kost_pris = kost_pris;
    }

    public double getSalgs_pris() {
        return salgs_pris;
    }

    public void setSalgs_pris(int salgs_pris) {
        this.salgs_pris = salgs_pris;
    }

    private int bredde;
    private int hoejde;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Materiale materiale = (Materiale) o;
        return vareNummer == materiale.vareNummer && laengde == materiale.laengde && antal == materiale.antal && Double.compare(kost_pris, materiale.kost_pris) == 0 && Double.compare(salgs_pris, materiale.salgs_pris) == 0 && bredde == materiale.bredde && hoejde == materiale.hoejde && Objects.equals(navn, materiale.navn) && Objects.equals(vare_beskrivelse, materiale.vare_beskrivelse) && Objects.equals(hjaelpe_tekst, materiale.hjaelpe_tekst);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vareNummer, navn, vare_beskrivelse, hjaelpe_tekst, laengde, antal, kost_pris, salgs_pris, bredde, hoejde);
    }
}
