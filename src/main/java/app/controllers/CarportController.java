package app.controllers;

import app.entities.Users;
import app.exception.DatabaseException;
import app.persistence.CarportMapper;
import app.persistence.ConnectionPool;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class CarportController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/carportSkaber", ctx -> ctx.render("carportSkaber.html"));
        app.post("/createCarport", ctx -> createCarport(ctx, connectionPool));
    }

    public static void createCarport(Context ctx, ConnectionPool connectionPool) {
        int carport_bredde = Integer.parseInt(ctx.formParam("width"));
        int carport_laengde = Integer.parseInt(ctx.formParam("length"));
        double pris = 0;
        String status = "Forsoørglse sendt";
        Users user = ctx.sessionAttribute("currentUser");
        int bruger_id = user.getUser_id();
        int stykliste_id = Stykliste.getStykliste_id();


            try {
                CarportMapper.createCarport(carport_bredde, carport_laengde, pris, status, bruger_id, stykliste_id , connectionPool);
                ctx.render("index.html");
            } catch (DatabaseException e) {
                ctx.attribute("msg", e.getMessage());
                ctx.render("createAccount.html");
            }
        }
}
