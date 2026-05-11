package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;

public class CarportController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/carportSkaber", ctx -> ctx.render("carportSkaber.html"));
    }
}
