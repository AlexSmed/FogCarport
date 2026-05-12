package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;

public class CarportController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/carportSkaber", ctx -> ctx.render("carportSkaber.html"));

        app.post("/order", ctx -> orderCarport(ctx));
    }
    public static void orderCarport(Context ctx){
        int width = Integer.parseInt(ctx.formParam("width"));
        int length = Integer.parseInt(ctx.formParam("length"));

        System.out.println(width);
        System.out.println(length);
        ctx.render("carportSkaber.html");
    }
}
