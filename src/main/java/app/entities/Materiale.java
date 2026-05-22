package app.entities;

public class Materiale {
    private int vareNummer;
    private String navn;
    private String beskrivelse;
    private String hjaelpeTekst;
    private int laengde;
    private int bredde;
    private int hoejde;
    private int antal;
    private double kostpris;
    private double salgspris;

    public Materiale() {
    }



    public Materiale(int vareNummer, String navn, String beskrivelse, String hjaelpe_tekst,
                     int laengde, int bredde, int hoejde, double kostpris, double salgspris, int antal) {

        this.vareNummer = vareNummer;
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.hjaelpeTekst = hjaelpe_tekst;
        this.laengde = laengde;
        this.bredde = bredde;
        this.hoejde = hoejde;
        this.kostpris = kostpris;
        this.salgspris = salgspris;
        this.antal = antal;
    }

    public Materiale(String navn, String beskrivelse, String hjaelpetekst, int laengde, int bredde, int hoejde, double kostpris, double salgspris) {
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.hjaelpeTekst = hjaelpetekst;
        this.laengde = laengde;
        this.bredde = bredde;
        this.hoejde = hoejde;
        this.kostpris = kostpris;
        this.salgspris = salgspris;
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

    public String getbeskrivelse() {
        return beskrivelse;
    }

    public void setbeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public String getHjaelpeTekst() {
        return hjaelpeTekst;
    }

    public void setHjaelpeTekst(String hjaelpeTekst) {
        this.hjaelpeTekst = hjaelpeTekst;
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

    public double getKostpris() {
        return kostpris;
    }

    public void setKostpris(double kostpris) {
        this.kostpris = kostpris;
    }

    public double getSalgspris() {
        return salgspris;
    }

    public void setSalgspris(double salgspris) {
        this.salgspris = salgspris;
    }


}
