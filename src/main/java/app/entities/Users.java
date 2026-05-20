package app.entities;

import java.util.Objects;

public class Users {
    private String fornavn;
    private String lastname;
    private String email;
    private String kodeord;
    private int bruger_id;
    private int saldo;
    private boolean er_admin;
    private String addresse;

    public Users(String fornavn, String lastname, String email, String kodeord,
                 int bruger_id, int saldo, boolean er_admin, String addresse) {
        this.fornavn = fornavn;
        this.lastname = lastname;
        this.email = email;
        this.kodeord = kodeord;
        this.bruger_id = bruger_id;
        this.saldo = saldo;
        this.er_admin = er_admin;
        this.addresse = addresse;
    }

    public Users(String firstname, String lastname, String email, String password, int bruger_id, int saldo, boolean er_admin) {
        this.fornavn = firstname;
        this.lastname = lastname;
        this.email = email;
        this.kodeord = password;
        this.bruger_id = bruger_id;
        this.saldo = saldo;
        this.er_admin = er_admin;
    }


    public String getUseremail() {
        return email;
    }

    public void setUser_email(String user_email) {
        this.email = user_email;
    }

    public String getPassword() {
        return kodeord;
    }

    public void setPassword(String password) {
        this.kodeord = password;
    }

    public int getBruger_id() {
        return bruger_id;
    }

    public void setBruger_id(int bruger_id) {
        this.bruger_id = bruger_id;
    }

    public int getbalance() {
        return saldo;
    }

    public boolean isAdmin() {
        return er_admin;
    }

    public void setEr_admin(boolean er_admin) {
        this.er_admin = er_admin;
    }
    public int getUserId() {
        return bruger_id;
    }

    public String getEmail() {
        return email;
    }



    @Override
    public int hashCode() {
        return Objects.hash(fornavn, lastname, email, kodeord, bruger_id);
    }
}

