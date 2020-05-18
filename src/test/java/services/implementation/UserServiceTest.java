package services.implementation;

import models.UserModel;
import models.other.UserType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repository.IRepository;
import services.IUserService;
import utils.StringEncryptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private IRepository<UserModel> repository;

    private IUserService userService;

    @Before
    public void setUp() {
        userService = new UserServiceImpl(repository);
    }

    @After
    public void tearDown() {
        userService = null;
    }

    @Test
    public void testGetAllUsers() {
        List<UserModel> userModels = new ArrayList<>();

        when(repository.getAll()).thenReturn(userModels);
        List<UserModel> allUsers = userService.getAllUsers();

        assertEquals("Size not the same", userModels.size(), allUsers.size());

        for (int i = 0; i < allUsers.size(); i++) {
            UserModel actual = allUsers.get(i);
            UserModel expected = userModels.get(i);

            assertEquals("Not Same model", expected, actual);
        }
    }

    @Test
    public void testExistUserWithEmail() {

        List<UserModel> userModels = new ArrayList<>();
        userModels.add(new UserModel(1, "email1@emial.com", "password1", "Nume 1", UserType.RegularUser));
        userModels.add(new UserModel(3, "emai65@ggaa.ro", "password3", "Nume 3", UserType.Manager));
        userModels.add(new UserModel(4, "email3@ggaa.ro", "password13", "Nume 31", UserType.Artist));
        userModels.add(new UserModel(7, "e124mail4@hhh.ru", "passwor44d4", "Nume 46", UserType.RegularUser));
        userModels.add(new UserModel(44, "emadfghil4@hhh.ww", "passwo44r1d4", "Nume 74", UserType.Artist));
        userModels.add(new UserModel(78, "emaaaail4@hhh.ww", "password4jjf", "Nume 41", UserType.Manager));

        when(repository.getAll()).thenReturn(userModels);

        assertTrue(userService.existsUser("email1@emial.com"));
        assertTrue(userService.existsUser("e124mail4@hhh.ru"));
        assertTrue(userService.existsUser("emaaaail4@hhh.ww"));
        assertFalse(userService.existsUser("nonexisting@email.com"));

        when(repository.getAll()).thenReturn(null);
        assertFalse(userService.existsUser("email@e.com"));
    }

    @Test
    public void testExistUserWithID() {
        List<UserModel> userModels = new ArrayList<>();
        userModels.add(new UserModel(1, "email1@emial.com", "password1", "Nume 1", UserType.RegularUser));
        userModels.add(new UserModel(4, "email3@ggaa.ro", "password13", "Nume 31", UserType.Artist));
        userModels.add(new UserModel(7, "e124mail4@hhh.ru", "passwor44d4", "Nume 46", UserType.RegularUser));
        userModels.add(new UserModel(44, "emadfghil4@hhh.ww", "passwo44r1d4", "Nume 74", UserType.Artist));
        userModels.add(new UserModel(78, "emaaaail4@hhh.ww", "password4jjf", "Nume 41", UserType.Manager));

        when(repository.getAll()).thenReturn(userModels);

        assertTrue(userService.existsUser(1));
        assertTrue(userService.existsUser(7));
        assertTrue(userService.existsUser(78));
        assertFalse(userService.existsUser(120));

        when(repository.getAll()).thenReturn(null);
        assertFalse(userService.existsUser(3));
    }

    @Test(expected = UserExistsException.class)
    public void testCreateUserExisting() throws UserExistsException {
        List<UserModel> userModels = new ArrayList<>();
        userModels.add(new UserModel(1, "email1@emial.com", "password1", "Nume 1", UserType.RegularUser));
        userModels.add(new UserModel(3, "emai65@ggaa.ro", "password3", "Nume 3", UserType.Manager));
        userModels.add(new UserModel(73, "emai65@ggaa.ro", "pa77ssword3", "Nume 3121", UserType.Manager));
        userModels.add(new UserModel(41, "email11@ggaa.ro", "pas22sword13", "Nume 315151", UserType.Artist));
        userModels.add(new UserModel(4, "email3@ggaa.ro", "password13", "Nume 31", UserType.Artist));
        userModels.add(new UserModel(7, "e124mail4@hhh.ru", "passwor44d4", "Nume 46", UserType.RegularUser));
        userModels.add(new UserModel(44, "emadfghil4@hhh.ww", "passwo44r1d4", "Nume 74", UserType.Artist));
        userModels.add(new UserModel(78, "emaaaail4@hhh.ww", "password4jjf", "Nume 41", UserType.Manager));

        when(repository.getAll()).thenReturn(userModels);

        userService.createUser("emai65@ggaa.ro", "newPassword", "My Name", UserType.RegularUser);
    }

    @Test()
    public void testCreateNewUser() {
        List<UserModel> userModels = new ArrayList<>();
        userModels.add(new UserModel(1, "email1@emial.com", "password1", "Nume 1", UserType.RegularUser));
        userModels.add(new UserModel(2, "email3@ggaa.ro", "password13", "Nume 31", UserType.Artist));
        userModels.add(new UserModel(3, "emai65@ggaa.ro", "password3", "Nume 3", UserType.Manager));
        userModels.add(new UserModel(4, "email3@ggaa.ro", "password13", "Nume 3951", UserType.Artist));
        userModels.add(new UserModel(7, "e124mail4@hhh.ru", "passwor44d4", "Nume 46", UserType.RegularUser));
        userModels.add(new UserModel(41, "emadfghil4@hhh.ww", "passwo44r1d4", "Nume 7214", UserType.Manager));
        userModels.add(new UserModel(44, "emadfghil4@hhh.ww", "passwo44r1d4", "Nume 74", UserType.Artist));
        userModels.add(new UserModel(78, "emaaaail4@hhh.ww", "password4jjf", "Nume 41", UserType.Manager));

        when(repository.getAll()).thenReturn(userModels);

        try {
            String email = "newEmail@ggaa.ro";
            String password = "newPassword";
            String name = "My Name";
            UserType type = UserType.RegularUser;
            UserModel createdUser = userService.createUser(email, password, name, type);

            assertNotNull("Null created user", createdUser);
            assertEquals("Not autoincrement ID", 79, createdUser.getId());
            assertEquals("Not same email", email, createdUser.getEmail());
            assertEquals("Not same password", StringEncryptor.encrypt(email, password), createdUser.getPassword());
            assertEquals("Not same name", name, createdUser.getName());
            assertEquals("Not same type", type, createdUser.getType());
        } catch (UserExistsException e) {
            fail("Existing user");
        }
    }

    @Test()
    public void testCreateUserInEmptyFile() {

        when(repository.getAll()).thenReturn(new ArrayList<>());

        try {
            String email = "newEmail@ggaa.ro";
            String password = "newPassword";
            String name = "My Name";
            UserType type = UserType.Manager;
            UserModel createdUser = userService.createUser(email, password, name, type);

            assertNotNull("Null created user", createdUser);
            assertEquals("Not autoincrement ID", 0, createdUser.getId());
            assertEquals("Not same email", email, createdUser.getEmail());
            assertEquals("Not same password", StringEncryptor.encrypt(email, password), createdUser.getPassword());
        } catch (UserExistsException e) {
            fail("Existing user");
        }

        when(repository.getAll()).thenReturn(null);

        try {
            String email = "newEmail@ggaa.ro";
            String password = "newPassword";
            String name = "My Name";
            UserType type = UserType.Artist;
            UserModel createdUser = userService.createUser(email, password, name, type);

            assertNotNull("Null created user", createdUser);
            assertEquals("Not autoincrement ID", 0, createdUser.getId());
        } catch (UserExistsException e) {
            fail("Existing user");
        }
    }

    @Test
    public void testGetUserByEmail() {
        List<UserModel> userModels = new ArrayList<>();
        userModels.add(new UserModel(1, "email1@emial.com", "password1", "Nume 1", UserType.Manager));
        userModels.add(new UserModel(3, "emai65@ggaa.ro", "password3", "Nume 3", UserType.Manager));
        userModels.add(new UserModel(7, "e124mail4@hhh.ru", "passwor44d4", "Nume 46", UserType.RegularUser));
        userModels.add(new UserModel(78, "emaaaail4@hhh.ww", "password4jjf", "Nume 41", UserType.Artist));

        when(repository.getAll()).thenReturn(userModels);

        UserModel user = userService.getUser("email1@emial.com");
        assertEquals(1, user.getId());
        assertEquals("Nume 1", user.getName());
        assertEquals(UserType.Manager, user.getType());

        user = userService.getUser("e124mail4@hhh.ru");
        assertEquals(7, user.getId());
        assertEquals("Nume 46", user.getName());
        assertEquals(UserType.RegularUser, user.getType());

        user = userService.getUser("emaaaail4@hhh.ww");
        assertEquals(78, user.getId());
        assertEquals("Nume 41", user.getName());
        assertEquals(UserType.Artist, user.getType());

        assertNull(userService.getUser("nonexisting@email.com"));

        when(repository.getAll()).thenReturn(null);
        assertNull(userService.getUser("email@e.com"));
    }

    @Test
    public void testGetUserByID() {
        List<UserModel> userModels = new ArrayList<>();
        userModels.add(new UserModel(1, "email1@emial.com", "password1", "Nume 1", UserType.RegularUser));
        userModels.add(new UserModel(3, "emai65@ggaa.ro", "password3", "Nume 3", UserType.Manager));
        userModels.add(new UserModel(7, "e124mail4@hhh.ru", "passwor44d4", "Nume 46", UserType.RegularUser));
        userModels.add(new UserModel(8, "e1ail4@hhh.ru", "losos4", "Nume 48", UserType.Manager));
        userModels.add(new UserModel(51, "e124mail4@hhfgdh.ru", "gfghg", "Nume 46218", UserType.Artist));
        userModels.add(new UserModel(61, "e12h1l4@h765.ru", "passwor4424124", "Nume 42226", UserType.RegularUser));
        userModels.add(new UserModel(78, "emaaaail4@hhh.ww", "password4jjf", "Nume 41", UserType.Artist));

        when(repository.getAll()).thenReturn(userModels);

        UserModel user = userService.getUser(1);
        assertEquals(1, user.getId());
        assertEquals("Nume 1", user.getName());
        assertEquals(UserType.RegularUser, user.getType());

        user = userService.getUser(8);
        assertEquals(8, user.getId());
        assertEquals("Nume 48", user.getName());
        assertEquals(UserType.Manager, user.getType());

        user = userService.getUser(78);
        assertEquals(78, user.getId());
        assertEquals("Nume 41", user.getName());
        assertEquals(UserType.Artist, user.getType());

        assertNull(userService.getUser(104));

        when(repository.getAll()).thenReturn(null);
        assertNull(userService.getUser(661));
    }

    @Test
    public void testGetArtist() {
        List<UserModel> userModels = new ArrayList<>();
        userModels.add(new UserModel(1, "email1@emial.com", "password1", "Nume 1", UserType.Manager));
        userModels.add(new UserModel(2, "email2@emial.com", "password16", "Nume 15", UserType.Artist));
        userModels.add(new UserModel(5, "email5@emial.com", "password188", "Nume 122", UserType.Manager));
        userModels.add(new UserModel(8, "emai6875@ggaa.ro", "passwor2572d3", "Nume 351", UserType.Manager));
        userModels.add(new UserModel(73, "e124mail4@hhh.ru", "passwor412134d4", "Nume 46", UserType.RegularUser));
        userModels.add(new UserModel(78, "emaaaail4@hhh.ww", "password4jjf", "Nume 41", UserType.Artist));

        when(repository.getAll()).thenReturn(userModels);

        assertNull(userService.getArtist("Nume 1"));
        assertNotNull(userService.getArtist("Nume 15"));
        assertNull(userService.getArtist("Nume 351"));
        assertNotNull(userService.getArtist("Nume 41"));

        when(repository.getAll()).thenReturn(null);
        assertNull(userService.getArtist("Nume 11"));
    }

    @Test
    public void testValidateUserCredentials() {
        List<UserModel> userModels = new ArrayList<>();
        userModels.add(new UserModel(1, "email1@emial.com", StringEncryptor.encrypt("email1@emial.com", "password1"), "Nume 1", UserType.Manager));
        userModels.add(new UserModel(2, "email2@emial.com", StringEncryptor.encrypt("email2@emial.com", "password16"), "Nume 15", UserType.Artist));
        userModels.add(new UserModel(5, "email5@emial.com", StringEncryptor.encrypt("email5@emial.com", "password188"), "Nume 122", UserType.Manager));
        userModels.add(new UserModel(8, "emai6875@ggaa.ro", StringEncryptor.encrypt("emai6875@ggaa.ro", "passwor2572d3"), "Nume 351", UserType.Manager));
        userModels.add(new UserModel(73, "e124mail4@hhh.ru", StringEncryptor.encrypt("e124mail4@hhh.ru", "passwor412134d4"), "Nume 46", UserType.RegularUser));
        userModels.add(new UserModel(78, "emaaaail4@hhh.ww", StringEncryptor.encrypt("emaaaail4@hhh.ww", "password4jjf"), "Nume 41", UserType.Artist));

        when(repository.getAll()).thenReturn(userModels);

        assertTrue(userService.validateUserCredentials("email1@emial.com", "password1"));
        assertTrue(userService.validateUserCredentials("emai6875@ggaa.ro", "passwor2572d3"));
        assertTrue(userService.validateUserCredentials("emaaaail4@hhh.ww", "password4jjf"));

        assertFalse(userService.validateUserCredentials("emai6875@ggaa.ro", "password4jjf"));
        assertFalse(userService.validateUserCredentials("emaaaail4@hhh.ww", "password188"));

        when(repository.getAll()).thenReturn(null);
        assertFalse(userService.validateUserCredentials("Nume 11", "parola"));
    }
}