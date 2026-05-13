package app.controllers;

import app.entities.Stykliste;
import app.entities.Users;
import app.exception.DatabaseException;
import app.persistence.CarportMapper;
import app.persistence.ConnectionPool;
import app.persistence.StyklisteMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class CarportController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/carportSkaber", ctx -> ctx.render("carportSkaber.html"));

        app.post("/order", ctx -> orderCarport(ctx, connectionPool));
    }
    public static void orderCarport(Context ctx, ConnectionPool connectionPool){

      try {
          int width = Integer.parseInt(ctx.formParam("width"));
          int length = Integer.parseInt(ctx.formParam("length"));
          double pris = 0;
          String status = "forspørglse afsendt";
          Users user = ctx.sessionAttribute("currentUser");
          int bruger_id = user.getUser_id();

          StyklisteMapper.createStykliste(bruger_id, connectionPool);

          int stykliste_id = 0;


          CarportMapper.createCarport(width, length, pris, status, bruger_id, stykliste_id, connectionPool);
          ctx.result("Carport oprettet!");
      }catch (DatabaseException e) {
          ctx.result("Fejl: " + e.getMessage());
        }
    }
}
