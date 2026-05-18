package app.controllers;

import app.entities.Users;
import app.exception.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.eclipse.jetty.server.Authentication;

import java.util.List;


public class UserController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/login", ctx -> ctx.render("index.html"));
        app.post("/login", ctx -> login(ctx, connectionPool));


        app.post("/createAccount", ctx -> createAccount(ctx, connectionPool));
        app.get("/createAccount", ctx -> ctx.render("createAccount.html"));

        app.get("/logout", ctx -> ctx.render("index.html"));
        app.post("/logout", ctx -> logout(ctx, connectionPool));

    }


    public static void createAccount(Context ctx, ConnectionPool connectionPool) {
        String firstname = ctx.formParam("firstname");
        String lastname = ctx.formParam("lastname");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");


        String error = validateUser(firstname, lastname, email, password);
        if (!error.isEmpty()) {
            ctx.attribute("msg", error);
            ctx.render("createAccount.html");
        } else {

            try {
                UserMapper.createUser(firstname, lastname, email, password, connectionPool);
                ctx.render("index.html");
            } catch (DatabaseException e) {
                ctx.attribute("msg", e.getMessage());
                ctx.render("createAccount.html");
            }
        }
    }

    public static void login(Context ctx, ConnectionPool connectionPool) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            Users user = UserMapper.login(email, password, connectionPool);

            if (user == null) {
                ctx.attribute("msg", "Forkert email eller password");
                ctx.render("index.html");
                return;
            }

            ctx.sessionAttribute("currentUser", user);
            ctx.sessionAttribute("bruger_id", user.getUser_id());

            if (user.isAdmin()) {
                ctx.render("adminPage.html");
            } else {
                user = ctx.sessionAttribute("currentUser");
                ctx.attribute("user", user);
                ctx.redirect("/carportSkaber");
            }

        } catch (DatabaseException e) {
            ctx.attribute("msg", e.getMessage());
            ctx.render("index.html");
        }
    }

    public static void logout(Context ctx, ConnectionPool connectionPool) {
        ctx.req().getSession().invalidate();
        ctx.sessionAttribute("currentUser", null);
        ctx.redirect("/index");
    }


    public static String validateUser(String firstname, String lastname,  String email, String password) {
        if (firstname.isEmpty()) {
            return "Fornavn skal udfyldes";
        }  else if (lastname.isEmpty()) {
            return "Efternavn skal udfyldes";}
        else if (email.isEmpty()) {
            return "email skal udfyldes";
        } else if (!email.contains("@")) {
            return "email mangler @";
        } else if (password.isEmpty()) {
            return "Password skal udfyldes";
        } else if (password.length() < 8) {
            return "password skal være mindst 8 tegn";
        } else if (!password.matches(".*\\d.*")) {
            return "password skal have mindst 1 tal";
        } else if (!password.matches(".*[^a-zA-Z0-9].*")) {
            return "password skal have mindst 1 special tegn";
        } else return "";
    }
}