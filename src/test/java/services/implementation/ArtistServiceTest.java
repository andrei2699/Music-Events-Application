package services.implementation;

import models.ArtistModel;
import models.other.DaysOfWeek;
import models.other.Interval;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repository.IRepository;
import services.IArtistService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArtistServiceTest {

    @Mock
    private IRepository<ArtistModel> repository;

    private IArtistService artistService;

    private List<ArtistModel> artistModels;

    @Before
    public void setUp() {
        artistService = new ArtistServiceImpl(repository);

        artistModels = new ArrayList<>();
        artistModels.add(new ArtistModel(14, false, "rock"));

        ArtistModel artistModel1 = new ArtistModel(23, true, "jazz");
        artistModel1.setMembers("Tania, Ioana, Alina, Mihai");
        artistModel1.setPath_to_image("C:/Imagini/Artisti/poza1.png");
        artistModels.add(artistModel1);

        ArtistModel artistModel2 = new ArtistModel(55, false, "pop");
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(DaysOfWeek.Duminica, 13, 19));
        intervals.add(new Interval(DaysOfWeek.Miercuri, 9, 12));
        intervals.add(new Interval(DaysOfWeek.Joi, 17, 23));
        intervals.add(new Interval(DaysOfWeek.Sambata, 12, 0));
        artistModel1.setIntervals(intervals);
        artistModel1.setPath_to_image("C:/Imagini/Artisti/poza2.png");
        artistModels.add(artistModel2);

        ArtistModel artistModel3 = new ArtistModel(89, true, "folk");
        artistModel3.setPath_to_image("C:/Imagini/Artisti/poza1.png");
        List<Interval> intervals2 = new ArrayList<>();
        intervals2.add(new Interval(DaysOfWeek.Luni, 14, 18));
        intervals2.add(new Interval(DaysOfWeek.Marti, 10, 14));
        intervals2.add(new Interval(DaysOfWeek.Miercuri, 14, 18));
        intervals2.add(new Interval(DaysOfWeek.Joi, 10, 14));
        intervals2.add(new Interval(DaysOfWeek.Vineri, 14, 18));
        artistModel3.setIntervals(intervals2);
        artistModel3.setMembers("Mary, John, David, Lili");
        artistModels.add(artistModel3);

        ArtistModel artistModel4 = new ArtistModel(76, true, "classical");
        artistModel4.setMembers("Sophie, Marc, Louise, Deborah");
        artistModel4.setPath_to_image("C:/Imagiini/Artisti/poza15.png");
        artistModels.add(artistModel4);
    }

    @After
    public void tearDown() {
        artistService = null;
    }

    @Test
    public void testGetAllArtists() {
        when(repository.getAll()).thenReturn(artistModels);
        List<ArtistModel> allArtists = artistService.getAllArtists();
        assertEquals("Not the same", artistModels, allArtists);
    }

    @Test
    public void testGetArtistUsingId() {
        when(repository.getAll()).thenReturn(artistModels);
        assertEquals(artistModels.get(0), artistService.getArtist(14));
        assertEquals(artistModels.get(2), artistService.getArtist(55));
        assertEquals(artistModels.get(4), artistService.getArtist(76));
        assertNull(artistService.getArtist(963));

        when(repository.getAll()).thenReturn(new ArrayList<>());
        assertNull(artistService.getArtist(55));

        when(repository.getAll()).thenReturn(null);
        assertNull(artistService.getArtist(23));
    }

    @Test
    public void testGetArtistUsingGenre() {
        when(repository.getAll()).thenReturn(artistModels);

        //search for o
        List<ArtistModel> expected1 = new ArrayList<>();
        expected1.add(artistModels.get(0));
        expected1.add(artistModels.get(2));
        expected1.add(artistModels.get(3));

        assertEquals("Found wrong artists when looking for genre", expected1, artistService.getArtists("o"));

        //search for classical
        List<ArtistModel> expected2 = new ArrayList<>();
        expected2.add(artistModels.get(4));

        assertEquals("Found wrong artist when looking for genre", expected2, artistService.getArtists("classical"));

        assertEquals(0, artistService.getArtists("punk").size());

        when(repository.getAll()).thenReturn(new ArrayList<>());

        assertEquals(0, artistService.getArtists("rock").size());

        when(repository.getAll()).thenReturn(null);

        assertEquals(0, artistService.getArtists("pop").size());
    }

    @Test
    public void testCreateNewArtist() {
        when(repository.getAll()).thenReturn(null);
        try {
            artistService.createArtist(new ArtistModel(99, false, "latino"));
        } catch (NullPointerException e) {
            fail("Created Artist in Null List of Artists");
        }
    }
}
