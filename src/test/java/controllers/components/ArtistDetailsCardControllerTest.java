package controllers.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.ArtistModel;
import models.DiscussionModel;
import models.cards.ArtistCardModel;
import models.cards.DiscussionHeaderCardModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArtistDetailsCardControllerTest extends ApplicationTest {
    @Mock
    private ArtistCardModel dummyArtistCardModel;

    private ArtistDetailsCardController artistDetailsCardController;

    private static final String NAME = "Artist Name";
    private static final String GENRE = "Rock";
    private static final String BAND_MEMBERS = "Member1, Member 2";

    @Before
    public void setUp() {
        artistDetailsCardController = new ArtistDetailsCardController();
        artistDetailsCardController.artistDetailsCardVBox = new VBox();
        artistDetailsCardController.artistNameLabel = new Label();
        artistDetailsCardController.genreLabel = new Label();
        artistDetailsCardController.bandMembersLabel = new Label();
        artistDetailsCardController.bandMembersHBox = new HBox();
        artistDetailsCardController.goToProfilePageButton = new Button();
    }

    @Test
    public void testUpdateItemEmpty() {
        artistDetailsCardController.updateItem(null, true);
        assertNull(artistDetailsCardController.getGraphic());

        artistDetailsCardController.updateItem(dummyArtistCardModel, true);
        assertNull(artistDetailsCardController.getGraphic());

        artistDetailsCardController.updateItem(null, false);
        assertNull(artistDetailsCardController.getGraphic());

        artistDetailsCardController.updateItem(new DiscussionHeaderCardModel(new DiscussionModel(56, 6, 12)), false);
        assertNull("Invalid TableCardModel", artistDetailsCardController.getGraphic());
    }


    @Test
    public void testUpdateItemSoloArtist() {
        ArtistModel dummyArtistModel = new ArtistModel(46, false, GENRE);
        when(dummyArtistCardModel.getArtistName()).thenReturn(NAME);
        when(dummyArtistCardModel.getArtistModel()).thenReturn(dummyArtistModel);

        artistDetailsCardController.updateItem(dummyArtistCardModel, false);

        assertEquals(NAME, artistDetailsCardController.artistNameLabel.getText());
        assertEquals(GENRE, artistDetailsCardController.genreLabel.getText());
        assertTrue(artistDetailsCardController.bandMembersLabel.getText().isEmpty());
        assertFalse(artistDetailsCardController.bandMembersHBox.isVisible());
        assertNotNull(artistDetailsCardController.getGraphic());
    }

    @Test
    public void testUpdateItemBand() {
        ArtistModel dummyArtistModel = new ArtistModel(46, true, GENRE);
        dummyArtistModel.setMembers(BAND_MEMBERS);
        when(dummyArtistCardModel.getArtistName()).thenReturn(NAME);
        when(dummyArtistCardModel.getArtistModel()).thenReturn(dummyArtistModel);

        artistDetailsCardController.updateItem(dummyArtistCardModel, false);

        assertEquals(NAME, artistDetailsCardController.artistNameLabel.getText());
        assertEquals(GENRE, artistDetailsCardController.genreLabel.getText());
        assertEquals(BAND_MEMBERS, artistDetailsCardController.bandMembersLabel.getText());
        assertTrue(artistDetailsCardController.bandMembersHBox.isVisible());
        assertNotNull(artistDetailsCardController.getGraphic());
    }

}