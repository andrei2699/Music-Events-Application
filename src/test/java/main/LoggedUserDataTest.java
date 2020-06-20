package main;

import models.UserModel;
import models.other.UserType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoggedUserDataTest {

    private UserModel regularUserModel;
    private UserModel artistModel;
    private UserModel barManagerModel;

    private LoggedUserData nullUser;
    private LoggedUserData regularUser;
    private LoggedUserData artistUser;
    private LoggedUserData barUser;

    @Before
    public void setUp() {
        regularUserModel = new UserModel(13, "regular.user@yahoo.com", "password", "Regular User Namme", UserType.RegularUser);
        artistModel = new UserModel(14, "artist@yahoo.com", "password", "Artist", UserType.Artist);
        barManagerModel = new UserModel(15, "bar@yahoo.com", "password", "Bar Manager", UserType.Manager);

        nullUser = new LoggedUserData();
        nullUser.setUserModel(null);

        regularUser = new LoggedUserData();
        regularUser.setUserModel(regularUserModel);

        artistUser = new LoggedUserData();
        artistUser.setUserModel(artistModel);

        barUser = new LoggedUserData();
        barUser.setUserModel(barManagerModel);
    }

    @Test
    public void testIsUserLogged() {
        assertTrue(regularUser.isUserLogged());
        assertTrue(artistUser.isUserLogged());
        assertTrue(barUser.isUserLogged());
        assertFalse(nullUser.isUserLogged());
    }

    @Test
    public void testIsRegularUser() {
        assertTrue(regularUser.isRegularUser());
        assertFalse(artistUser.isRegularUser());
        assertFalse(barUser.isRegularUser());
        assertFalse(nullUser.isRegularUser());
    }

    @Test
    public void testIsArtist() {
        assertFalse(regularUser.isArtist());
        assertTrue(artistUser.isArtist());
        assertFalse(barUser.isArtist());
        assertFalse(nullUser.isArtist());
    }

    @Test
    public void testIsBarManager() {
        assertFalse(regularUser.isBarManager());
        assertFalse(artistUser.isBarManager());
        assertTrue(barUser.isBarManager());
        assertFalse(nullUser.isBarManager());
    }
}