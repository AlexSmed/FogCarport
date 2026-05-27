package app.entities;

import java.util.Objects;

public class Users {
    private String fornavn;
    private String efternavn;
    private String email;
    private String kodeord;
    private int bruger_id;
    private double saldo;
    private boolean er_admin;
    private String addresse;



    public Users(String firstname, String efternavn, String email, String password, int bruger_id, double saldo, boolean er_admin) {
        this.fornavn = firstname;
        this.efternavn = efternavn;
        this.email = email;
        this.kodeord = password;
        this.bruger_id = bruger_id;
        this.saldo = saldo;
        this.er_admin = er_admin;
    }

    public Users(int bruger_id, String fornavn, String efternavn, String email, double saldo) {
        this.bruger_id = bruger_id;
        this.fornavn = fornavn;
        this.efternavn =efternavn;
        this.email = email;
        this.saldo = saldo;
    }

    public String getFornavn() {
        return fornavn;
    }

    public String getEfternavn() {
        return efternavn;
    }

    public String getEmail() {
        return email;
    }

    public String getKodeord() {
        return kodeord;
    }

    public int getBruger_id() {
        return bruger_id;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean isEr_admin() {
        return er_admin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fornavn, efternavn, email, kodeord, bruger_id);
    }
}

