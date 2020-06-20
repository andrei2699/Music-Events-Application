package controllers.components;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class VideoPlayerComponentController {
    @FXML
    public MediaView videoMediaView;

    private MediaPlayer mediaPlayer;

    public void onVideoMouseClicked(MouseEvent mouseEvent) {
        if (mediaPlayer != null) {
            if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.play();
            }
        }
    }

    public void setVideo(String pathToVideo) {
        if (pathToVideo == null) return;
        Media media = new Media(new File(pathToVideo).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(false);

        videoMediaView.setMediaPlayer(mediaPlayer);
    }

    public void stopVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
