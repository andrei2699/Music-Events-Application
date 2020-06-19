package controllers.components;

import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VideoPlayerComponentControllerTest extends ApplicationTest {

    private VideoPlayerComponentController videoPlayerComponentController;

    @Before
    public void setUp() {
        videoPlayerComponentController = new VideoPlayerComponentController();
        videoPlayerComponentController.videoMediaView = new MediaView();
    }

    @Test(expected = MediaException.class)
    public void testSetVideoEmptyPath() {
        videoPlayerComponentController.setVideo("");
        assertNull("Not Empty media Player", videoPlayerComponentController.videoMediaView.getMediaPlayer());
    }

    @Test
    public void testSetVideoNullPath() {
        videoPlayerComponentController.setVideo(null);
        assertNull("Not Empty media Player", videoPlayerComponentController.videoMediaView.getMediaPlayer());
    }

    @Test(expected = MediaException.class)
    public void testSetVideoNotExistingPath() {
        String pathToVideo = "C:/Video/video1.mp4";
        videoPlayerComponentController.setVideo(pathToVideo);
        assertNull("Not Empty media Player", videoPlayerComponentController.videoMediaView.getMediaPlayer());
    }
}