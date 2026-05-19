package app.controllers;

import app.entities.Carport;
import app.entities.Materiale;
import app.entities.Stykliste;
import app.entities.Users;
import app.exception.DatabaseException;
import app.persistence.CarportMapper;
import app.persistence.ConnectionPool;
import app.persistence.OrderlinjeMapper;
import app.persistence.StyklisteMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarportController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/carportSkaber", ctx -> ctx.render("carportSkaber.html"));

        app.post("/order", ctx -> orderCarport(ctx, connectionPool));
        app.get("/myOrders", ctx -> getCustomeOrders(ctx, connectionPool));
        app.post("/payOrder", ctx -> updateStatus(ctx, connectionPool));



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
              pris = pris + materiale.getSalgs_pris();
              OrderlinjeMapper.createOrderlinje(stykliste_id, materiale.getVareNummer(), materiale.getAntal(), connectionPool);
          }


          CarportMapper.createCarport(width, length, pris, status, bruger_id, stykliste_id, connectionPool);
          ctx.result("Carport oprettet!");
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

}
