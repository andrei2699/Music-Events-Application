package controllers.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.ArtistModel;
import models.BarModel;
import models.UserModel;
import models.cards.ArtistCardModel;
import models.cards.BarCardModel;
import models.other.UserType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import services.IUserService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BarDetailsCardControllerTest extends ApplicationTest {
    @Mock
    private BarCardModel dummyBarCardModel;
    @Mock
    private IUserService userService;

    private BarDetailsCardController barDetailsCardController;

    private BarModel dummyBarModel;
    private static final String BAR_NAME = "BAR NAME";
    private static final String ADDRESS = "my address";

    @Before
    public void setUp() {
        barDetailsCardController = new BarDetailsCardController();
        barDetailsCardController.addressLabel = new Label();
        barDetailsCardController.barDetailsCardVBox = new VBox();
        barDetailsCardController.barNameLabel = new Label();
        barDetailsCardController.goToProfilePageButton = new Button();

        dummyBarModel = new BarModel(4, ADDRESS);
    }

    @Test
    public void testUpdateItemEmpty() {
        barDetailsCardController.updateItem(null, true);
        assertNull(barDetailsCardController.getGraphic());

        barDetailsCardController.updateItem(dummyBarCardModel, true);
        assertNull(barDetailsCardController.getGraphic());

        barDetailsCardController.updateItem(null, false);
        assertNull(barDetailsCardController.getGraphic());

        when(userService.getUser(2)).thenReturn(new UserModel(2, "email@yahoo.com", "psw", "name", UserType.Artist));
        barDetailsCardController.updateItem(new ArtistCardModel(new ArtistModel(2, true, "Punk"), userService), false);
        assertNull("Invalid TableCardModel", barDetailsCardController.getGraphic());
    }


    @Test
    public void testUpdateItem() {
        when(dummyBarCardModel.getBarModel()).thenReturn(dummyBarModel);
        when(dummyBarCardModel.getBarName()).thenReturn(BAR_NAME);

        barDetailsCardController.updateItem(dummyBarCardModel, false);

        assertEquals(ADDRESS, barDetailsCardController.addressLabel.getText());
        assertEquals(BAR_NAME, barDetailsCardController.barNameLabel.getText());

        assertNotNull(barDetailsCardController.getGraphic());
    }
}