package app.controllers;

import app.entities.Orderlinje;
import app.exception.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderlinjeMapper;
import io.javalin.http.Context;

public class OrderlinjeController {

    public static void createOrderlinje(int stykliste_id, ConnectionPool connectionPool, Context ctx) throws DatabaseException {
        try{
            int vare_nummer  = Integer.parseInt(ctx.formParam("lastname"));
            int antal = Integer.parseInt(ctx.formParam("antal"));

            OrderlinjeMapper.createOrderlinje(stykliste_id,vare_nummer,antal,connectionPool);

        } catch (DatabaseException e) {
        }

    }

}
