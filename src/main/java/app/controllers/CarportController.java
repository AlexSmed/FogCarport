package app.controllers;

import app.entities.Carport;
import app.persistence.CarportMapper;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

import static app.Main.connectionPool;

public class CarportController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/carportSkaber", ctx -> ctx.render("carportSkaber.html"));
        app.post("/order", ctx -> orderCarport(ctx));
        app.get("/myOrders", ctx -> getCustomeOrders(ctx, connectionPool));
        app.post("/payOrder", ctx -> updateStatus(ctx, connectionPool));

    }
    public static void orderCarport(Context ctx){
        int width = Integer.parseInt(ctx.formParam("width"));
        int length = Integer.parseInt(ctx.formParam("length"));

        System.out.println(width);
        System.out.println(length);
        ctx.render("carportSkaber.html");
    }

    public static void getCustomeOrders(Context ctx, ConnectionPool connectionPool){
        int brugerId = ctx.sessionAttribute("bruger_id");

        List<Carport> myOrders= CarportMapper.getAllOrdersByUserId(brugerId, connectionPool);


        ctx.attribute("orders", myOrders);
        ctx.render("showCustomerOrders.html");

    }

    public static void updateStatus(Context ctx, ConnectionPool connectionPool){
        int carportId = Integer.parseInt(ctx.formParam("orderId"));

        CarportMapper.updateStatus(carportId, connectionPool);

        ctx.redirect("/myOrders");
    }
}
