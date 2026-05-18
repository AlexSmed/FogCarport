package app.controllers;

import app.entities.Carport;
import app.persistence.AdminMapper;
import app.persistence.CarportMapper;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class AdminController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/allCarports", ctx ->
                AdminController.showAllCarports(ctx, connectionPool));
    }

    public static void showAllCarports(Context ctx, ConnectionPool connectionPool)
    {
        AdminMapper mapper = new AdminMapper(connectionPool);

        List<Carport> carports = mapper.getAllCarports(connectionPool);

        ctx.attribute("carports", carports);

        ctx.render("seeAllCarports.html");
    }

    private static void getAllOrdersWithUnpaidStatus(Context ctx, ConnectionPool connectionPool) {

        AdminMapper adminMapper = new AdminMapper(connectionPool);

        List<Carport> unPaidCarports = adminMapper.getAllCarportsWithUpaidStatus(connectionPool);

        ctx.attribute("unPaidCarports", unPaidCarports);

        ctx.render("adminPage.html");
    }
}
