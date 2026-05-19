package app.controllers;

import app.entities.Carport;
import app.entities.Materiale;
import app.entities.Users;
import app.exception.DatabaseException;
import app.persistence.CarportMapper;
import app.persistence.ConnectionPool;
import app.persistence.StyklisteMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

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

          // Carport mål og pris
          StyklisteController styklisteController = new StyklisteController();
          styklisteController.udregningAfStolper(length, connectionPool);
          styklisteController.udregningAfSpær(width, connectionPool);
          ArrayList<Materiale> materialer = new ArrayList<>();
          materialer.add( styklisteController.udregningAfStolper(length, connectionPool));
          materialer.add( styklisteController.udregningAfSpær(width, connectionPool));
          ArrayList<Materiale> remme = new ArrayList<>();
          for(Materiale rem: remme){
              materialer.add(rem);
          }
          for(Materiale materiale : materialer){
              pris = materiale.getSalgs_pris() + pris;
          }

          int stykliste_id = StyklisteMapper.createStykliste(bruger_id, connectionPool);

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
