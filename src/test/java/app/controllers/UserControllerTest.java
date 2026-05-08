package app.controllers;

import app.controllers.UserController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserControllerTest {

    @Test
    void shouldRejectBlankFirstname() {
        String error = UserController.validateUser("","EHE", "1234Aaa.", "ben@kunde.com");
        assertEquals("Fornavn skal udfyldes", error);
    }
    @Test
    void shouldRejectBlankLastName() {
        String error = UserController.validateUser("EJE","", "1234Aaa.", "ben@kunde.com");
        assertEquals("Efternavn skal udfyldes", error);
    }


    @Test
    void shouldRejectBlankPassword() {
        String error = UserController.validateUser("tim", "lars","" , "ben@kunde.com");
        assertEquals("Password skal udfyldes", error);
    }


    @Test
    void shouldAcceptValidUsernameAndPassword() {
        String error = UserController.validateUser("test", "lars","testtest1@", "ben@kunde.com");
        assertTrue(error.isEmpty());

    }

    @Test
    void shouldRejectTooShortPassword() {
        String error = UserController.validateUser("lars", "lars","1e!1111", "ben@kunde.com");
        assertEquals("password skal være mindst 8 tegn", error);
    }

    @Test
    void shouldRejectNoNumberPassword() {
        String error = UserController.validateUser("lars", "lars","bassemand", "ben@kunde.com");
        assertEquals("password skal have mindst 1 tal", error);
    }
    @Test
    void shouldRejectSpecialPassword() {
        String error = UserController.validateUser("lars", "lars","bassemand1", "ben@kunde.com");
        assertEquals("password skal have mindst 1 special tegn", error);
    }
}
