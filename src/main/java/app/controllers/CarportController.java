package app.controllers;

import app.entities.*;
import app.exception.DatabaseException;
import app.persistence.CarportMapper;
import app.persistence.ConnectionPool;
import app.persistence.OrderlinjeMapper;
import app.persistence.StyklisteMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CarportController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/carportSkaber", ctx -> ctx.render("carportSkaber.html"));

        app.post("/order", ctx -> orderCarport(ctx, connectionPool));
        app.get("/myOrders", ctx -> getCustomeOrders(ctx, connectionPool));
        app.post("/payOrder", ctx -> updateStatus(ctx, connectionPool));
        app.get("/seStykliste", ctx -> seStykliste(ctx, connectionPool));
        app.post("/seStykliste", ctx -> seStykliste(ctx, connectionPool));
        app.get("/showOrder", ctx -> CarportController.showOrder(ctx, connectionPool));


    }

    public static void orderCarport(Context ctx, ConnectionPool connectionPool){

      try {
          int width = Integer.parseInt(ctx.formParam("width"));
          int length = Integer.parseInt(ctx.formParam("length"));
          double pris = 0;
          String status = "forspørglse afsendt";
          Users user = ctx.sessionAttribute("currentUser");
          int bruger_id = user.getBruger_id();
          int stykliste_id = StyklisteMapper.getHighestStyklistId()+1;
          StyklisteMapper.createStykliste(stykliste_id, bruger_id, connectionPool);
          ArrayList<Materiale> materialerIOrderen = StyklisteController.stykListeMaterialer(length, width, connectionPool);
          for(Materiale materiale : materialerIOrderen){
              pris = pris + materiale.getSalgs_pris() * materiale.getAntal();
              OrderlinjeMapper.createOrderlinje(stykliste_id, materiale.getVareNummer(), materiale.getAntal(), connectionPool);
          }


          CarportMapper.createCarport(width, length, pris, status, bruger_id, stykliste_id, connectionPool);
          ctx.sessionAttribute("width", width);
          ctx.sessionAttribute("length", length);
          ctx.attribute("successMessage", "Forespørgsel afsendt");
          ctx.render("carportSkaber");

      }catch (DatabaseException e) {
          ctx.result("Fejl: " + e.getMessage());
        }
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

    public static void seStykliste(Context ctx, ConnectionPool connectionPool) {

        String param = ctx.queryParam("stykliste_id");

        if (param == null) {
            ctx.status(400);
            ctx.result("Mangler stykliste_id");
            return;
        }

        int stykliste_id = Integer.parseInt(param);

        List<Orderlinje> stykliste =
                OrderlinjeMapper.getStykliste(stykliste_id, connectionPool);

        ctx.attribute("stykliste", stykliste);
        ctx.render("stykliste.html");
    }

    public static void showOrder(Context ctx, ConnectionPool connectionPool)
    {
        Integer width = ctx.sessionAttribute("width");
        Integer length = ctx.sessionAttribute("length");



        Locale.setDefault(new Locale("US"));
            CarportSvg svg = new CarportSvg(width, length);
        if (width == null || length == null) {
            ctx.status(400).result("Missing session data");
            return;
        }

        ctx.attribute("svg", svg.toString());
        ctx.render("showOrder.html");
    }

    }



