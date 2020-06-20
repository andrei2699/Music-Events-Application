package models.cards;

import models.ArtistModel;
import models.UserModel;
import models.other.UserType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.IUserService;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArtistCardModelTest {

    @Mock
    IUserService userService;

    @Test
    public void testContainsFilterForGenre() {
        when(userService.getUser(13)).thenReturn(new UserModel(13, "email@yahoo.com", "password", "Name", UserType.Artist));

        ArtistModel artistModel = new ArtistModel(13, true, "progressive rock");
        ArtistCardModel artistCardModel = new ArtistCardModel(artistModel, userService);

        assertTrue(artistCardModel.containsFilter("progressive rock"));
        assertTrue(artistCardModel.containsFilter("progressive"));
        assertTrue(artistCardModel.containsFilter("rock"));
        assertTrue(artistCardModel.containsFilter("ROCK"));
        assertTrue(artistCardModel.containsFilter("Rock"));
        assertTrue(artistCardModel.containsFilter("gressive"));
        assertTrue(artistCardModel.containsFilter("ro"));

        assertFalse(artistCardModel.containsFilter("progressiverock"));
        assertFalse(artistCardModel.containsFilter("rOk"));
        assertFalse(artistCardModel.containsFilter("Progresive"));
        assertFalse(artistCardModel.containsFilter("PRGRES"));
    }

    @Test
    public void testContainsFilterForName() {
        when(userService.getUser(13)).thenReturn(new UserModel(13, "email@yahoo.com", "password", "Band Name", UserType.Artist));

        ArtistModel artistModel = new ArtistModel(13, true, "progressive rock");
        ArtistCardModel artistCardModel = new ArtistCardModel(artistModel, userService);

        assertTrue(artistCardModel.containsFilter("Band Name"));
        assertTrue(artistCardModel.containsFilter("band"));
        assertTrue(artistCardModel.containsFilter("BAND NAME"));
        assertTrue(artistCardModel.containsFilter("Name"));
        assertTrue(artistCardModel.containsFilter("AND"));
        assertTrue(artistCardModel.containsFilter("ame"));
        assertTrue(artistCardModel.containsFilter("band n"));

        assertFalse(artistCardModel.containsFilter("bandname"));
        assertFalse(artistCardModel.containsFilter("BandName"));
        assertFalse(artistCardModel.containsFilter("BNDNAME"));
        assertFalse(artistCardModel.containsFilter("nme"));
    }

    @Test
    public void testContainsFilterCombinationsOfGenreAndName() {
        when(userService.getUser(13)).thenReturn(new UserModel(13, "email@yahoo.com", "password", "Band Name", UserType.Artist));

        ArtistModel artistModel = new ArtistModel(13, true, "progressive rock");
        ArtistCardModel artistCardModel = new ArtistCardModel(artistModel, userService);

        assertFalse(artistCardModel.containsFilter("Band Progressive"));
        assertFalse(artistCardModel.containsFilter("Rock Band"));
        assertFalse(artistCardModel.containsFilter("BAND Rock"));
        assertFalse(artistCardModel.containsFilter("bandrock"));
        assertFalse(artistCardModel.containsFilter("PRoGreSSIVEBand"));

        assertTrue(artistCardModel.containsFilter(""));
        assertTrue(artistCardModel.containsFilter(null));
    }
}