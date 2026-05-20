package app.controllers;

import app.entities.Carport;
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
    }

    static AdminMapper mapper = new AdminMapper(connectionPool);

    public static void showAllCarports(Context ctx, ConnectionPool connectionPool) {

        List<Carport> carports = mapper.getAllCarports(connectionPool);

        ctx.attribute("carports", carports);
        ctx.render("seeAllCarports.html");
    }

    private static void getAllOrdersWithUnpaidStatus(Context ctx, ConnectionPool connectionPool) {

        List<Carport> unpaidCarports = mapper.getAllCarportsWithUnpaidStatus(connectionPool);

        ctx.attribute("carports", unpaidCarports);
        ctx.render("seeAllCarports.html");
    }

    private static void getAllCustomers(Context ctx, ConnectionPool connectionPool) {
        List<Users> allCustomers = mapper.getAllCustomers(connectionPool);

        ctx.attribute("customers", allCustomers);
        ctx.render("seeAllCustomers.html");

    }

    public static void updateCustomerBalance(Context ctx, ConnectionPool connectionPool){
        int saldo = Integer.parseInt(ctx.formParam("amount"));
        int brugerId = Integer.parseInt(ctx.formParam("brugerId"));

        AdminMapper mapper = new AdminMapper(connectionPool);

        int currentBalance = mapper.getCustomerBalance(brugerId, connectionPool);

        int newBalance = currentBalance + saldo;

        mapper.updateCustomerBalance(newBalance, brugerId, connectionPool);

        ctx.redirect("/showCustomers");
    }

}
