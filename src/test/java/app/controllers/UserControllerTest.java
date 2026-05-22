package app.controllers;

import app.controllers.UserController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserControllerTest {

    @Test
    void shouldRejectBlankFirstname() {
        String error = UserController.validateUser("","EHE", "ben@kunde.com","1234Aaa.");
        assertEquals("Fornavn skal udfyldes", error);
    }
    @Test
    void shouldRejectBlankLastName() {
        String error = UserController.validateUser("EJE","", "ben@kunde.com", "1234Aaa.");
        assertEquals("Efternavn skal udfyldes", error);
    }


    @Test
    void shouldRejectBlankPassword() {
        String error = UserController.validateUser("tim", "lars", "1234Aaa¤@gmail.com","");
        assertEquals("Password skal udfyldes", error);
    }


    @Test
    void shouldAcceptValidUsernameAndPassword() {
        String error = UserController.validateUser("test", "lars","ben@kunde.com","testtest1@");
        assertTrue(error.isEmpty());

    }

    @Test
    void shouldRejectTooShortPassword() {
        String error = UserController.validateUser("lars", "lars", "ben@kunde.com","1e!1111");
        assertEquals("password skal være mindst 8 tegn", error);
    }

    @Test
    void shouldRejectNoNumberPassword() {
        String error = UserController.validateUser("lars", "lars", "ben@kunde.com","bassemand");
        assertEquals("password skal have mindst 1 tal", error);
    }
    @Test
    void shouldRejectSpecialPassword() {
        String error = UserController.validateUser("lars", "lars", "ben@kunde.com","bassemand1");
        assertEquals("password skal have mindst 1 special tegn", error);
    }
}
