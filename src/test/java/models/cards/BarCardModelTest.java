package models.cards;

import models.BarModel;
import models.UserModel;
import models.other.UserType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import services.IUserService;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BarCardModelTest extends ApplicationTest {

    @Mock
    IUserService userService;

    @Test
    public void testContainsFilterForAddress() {
        when(userService.getUser(13)).thenReturn(new UserModel(13, "email@yahoo.com", "password", "Name", UserType.Manager));

        BarModel barModel = new BarModel(13, "Strada Mihai Viteazu Nr 17");
        BarCardModel barCardModel = new BarCardModel(barModel, userService);

        assertTrue(barCardModel.containsFilter("Strada Mihai Viteazu Nr 17"));
        assertTrue(barCardModel.containsFilter("Strada MIHAI VITEAZU Nr 17"));
        assertTrue(barCardModel.containsFilter("Strada"));
        assertTrue(barCardModel.containsFilter("Mihai Viteazu"));
        assertTrue(barCardModel.containsFilter("VITEAZU"));
        assertTrue(barCardModel.containsFilter("Viteazu Nr 17"));
        assertTrue(barCardModel.containsFilter("Nr 17"));

        assertFalse(barCardModel.containsFilter("Strada Viteazu"));
        assertFalse(barCardModel.containsFilter("MIHAI 17"));
        assertFalse(barCardModel.containsFilter("ViTeAzu 17"));
        assertFalse(barCardModel.containsFilter("Strada 17"));
    }

    @Test
    public void testContainsFilterForName() {
        when(userService.getUser(13)).thenReturn(new UserModel(13, "email@yahoo.com", "password", "Bar Name", UserType.Manager));

        BarModel barModel = new BarModel(13, "Strada Mihai Viteazu Nr 17");
        BarCardModel barCardModel = new BarCardModel(barModel, userService);

        assertTrue(barCardModel.containsFilter("Bar Name"));
        assertTrue(barCardModel.containsFilter("BAR"));
        assertTrue(barCardModel.containsFilter("Name"));
        assertTrue(barCardModel.containsFilter("bar name"));
        assertTrue(barCardModel.containsFilter("bar N"));

        assertFalse(barCardModel.containsFilter("BARNAME"));
        assertFalse(barCardModel.containsFilter("BarName"));
        assertFalse(barCardModel.containsFilter("Bra Name"));
        assertFalse(barCardModel.containsFilter("Mane"));
    }

    @Test
    public void testContainsFilterCombinationsOfGenreAndName() {
        when(userService.getUser(13)).thenReturn(new UserModel(13, "email@yahoo.com", "password", "Bar Name", UserType.Manager));

        BarModel barModel = new BarModel(13, "Strada Mihai Viteazu Nr 17");
        BarCardModel barCardModel = new BarCardModel(barModel, userService);

        assertFalse(barCardModel.containsFilter("Name Viteazu"));
        assertFalse(barCardModel.containsFilter("MihaiBar"));
        assertFalse(barCardModel.containsFilter("BAR NR 17"));
        assertFalse(barCardModel.containsFilter("bar miahi viteazu"));
        assertFalse(barCardModel.containsFilter("Name BAR MIHAI viteazu"));

        assertTrue(barCardModel.containsFilter(""));
        assertTrue(barCardModel.containsFilter(null));
    }
}