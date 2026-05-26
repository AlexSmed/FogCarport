package app.controllers;

import app.entities.Carport;
import app.entities.Materiale;
import app.entities.Users;
import app.persistence.AdminMapper;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import static app.Main.connectionPool;

public class AdminController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/allCarports", ctx ->
                AdminController.showAllCarports(ctx, connectionPool));

        app.get("unpaidCarports", ctx ->
                AdminController.getAllOrdersWithUnpaidStatus(ctx, connectionPool));

        app.get("showCustomers", ctx ->
                AdminController.getAllCustomers(ctx, connectionPool));

        app.post("updateBalance", ctx ->
                AdminController.updateCustomerBalance(ctx, connectionPool));

        app.get("showAllMaterials", ctx ->
                alleMaterialer(ctx, connectionPool));

        app.post("tilfoejNyMateriale", ctx ->
                tilfoejNyMateriale(ctx, connectionPool));

        app.get("/materialer", ctx -> {
            alleMaterialer(ctx, connectionPool);
        });
    }

    static AdminMapper adminMapper = new AdminMapper(connectionPool);

    public static void showAllCarports(Context ctx, ConnectionPool connectionPool) {

        List<Carport> carports = adminMapper.getAllCarports(connectionPool);

        ctx.attribute("carports", carports);
        ctx.render("seeAllCarports.html");
    }

    private static void getAllOrdersWithUnpaidStatus(Context ctx, ConnectionPool connectionPool) {

        List<Carport> unpaidCarports = adminMapper.getAllCarportsWithUnpaidStatus(connectionPool);

        ctx.attribute("carports", unpaidCarports);
        ctx.render("seeAllCarports.html");
    }

    private static void getAllCustomers(Context ctx, ConnectionPool connectionPool) {
        List<Users> allCustomers = adminMapper.getAllCustomers(connectionPool);

        ctx.attribute("customers", allCustomers);
        ctx.render("seeAllCustomers.html");

    }

    public static void updateCustomerBalance(Context ctx, ConnectionPool connectionPool){
        int saldo = Integer.parseInt(ctx.formParam("amount"));
        int brugerId = Integer.parseInt(ctx.formParam("brugerId"));

        int currentBalance = adminMapper.getCustomerBalance(brugerId, connectionPool);

        int newBalance = currentBalance + saldo;

        adminMapper.updateCustomerBalance(newBalance, brugerId, connectionPool);

        ctx.redirect("/showCustomers");
    }

    public static void alleMaterialer(Context ctx, ConnectionPool connectionPool){
        List<Materiale> alleMaterialer = adminMapper.seAlleMaterialer(connectionPool);

        ctx.attribute("materialer", alleMaterialer);
        ctx.render("materialeListe.html");
    }

    public static void tilfoejNyMateriale(Context ctx, ConnectionPool connectionPool){
        String navn = ctx.formParam("navn");
        String beskrivelse = ctx.formParam("beskrivelse");
        String hjaelpeTekst = ctx.formParam("hjaelpeTekst");
        int laengde = Integer.parseInt(ctx.formParam("laengde"));
        int bredde = Integer.parseInt(ctx.formParam("bredde"));
        int hoejde = Integer.parseInt(ctx.formParam("hoejde"));
        double kostpris = Double.parseDouble(ctx.formParam("kostpris"));
        double salgspris = Double.parseDouble(ctx.formParam("salgspris"));

        adminMapper.tilfoejNyMateriale(navn, beskrivelse, hjaelpeTekst, laengde, bredde, hoejde, kostpris, salgspris, connectionPool);
        ctx.redirect("/materialer");

    }
}

