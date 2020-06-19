package controllers.components;

import controllers.scenes.ISceneResponseCall;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import main.LoggedUserData;
import models.ArtistModel;
import models.BarModel;
import models.DiscussionModel;
import models.UserModel;
import models.cards.DiscussionHeaderCardModel;
import models.cards.TableCardModel;
import models.other.UserType;
import services.IArtistService;
import services.IBarService;
import services.IUserService;
import services.ServiceProvider;
import utils.StringValidator;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static main.ApplicationResourceStrings.DISCUSSION_HEADER_CHAT_CARD_FXML_PATH;

public class DiscussionChatHeaderCardController extends TableCell<TableCardModel, TableCardModel> {
    @FXML
    public ImageView profilePictureImage;
    @FXML
    public Label nameLabel;
    @FXML
    public HBox chatHeaderHBox;

    private ISceneResponseCall<DiscussionHeaderCardModel> onCardModelSet;

    private ISceneResponseCall<DiscussionHeaderCardModel> onClickResponseCall;
    private DiscussionHeaderCardModel discussionHeaderCardModel;

    private final IUserService userService;
    private final IArtistService artistService;
    private final IBarService barService;

    // for testing
    protected DiscussionChatHeaderCardController(IUserService userService, IArtistService artistService, IBarService barService) {
        this.userService = userService;
        this.artistService = artistService;
        this.barService = barService;
    }

    // for FXML
    public DiscussionChatHeaderCardController() {
        this(ServiceProvider.getUserService(), ServiceProvider.getArtistService(), ServiceProvider.getBarService());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(DISCUSSION_HEADER_CHAT_CARD_FXML_PATH));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(TableCardModel tableCardModel, boolean empty) {
        super.updateItem(tableCardModel, empty);

        if (!empty && tableCardModel instanceof DiscussionHeaderCardModel) {

            discussionHeaderCardModel = (DiscussionHeaderCardModel) tableCardModel;
            if (onCardModelSet != null)
                onCardModelSet.onResponseCall(discussionHeaderCardModel);

            DiscussionModel discussionModel = discussionHeaderCardModel.getDiscussionModel();

            int currentLoggedUserId = LoggedUserData.getInstance().getUserModel().getId();
            Optional<Integer> otherPersonId = discussionModel.getIds().stream().filter(id -> id != currentLoggedUserId).findFirst();

            if (otherPersonId.isPresent()) {

                UserModel otherUser = userService.getUser(otherPersonId.get());
                if (otherUser != null) {

                    nameLabel.setText(otherUser.getName());

                    if (otherUser.getType() == UserType.Artist) {
                        ArtistModel artistModel = artistService.getArtist(otherPersonId.get());
                        if (artistModel != null) {
                            loadImage(artistModel.getPath_to_image());
                        }
                    } else if (otherUser.getType() == UserType.Manager) {
                        BarModel barModel = barService.getBar(otherPersonId.get());
                        if (barModel != null) {
                            loadImage(barModel.getPath_to_image());
                        }
                    }
                }
            }

            chatHeaderHBox.setOnMouseClicked(this::onMouseClicked);

            setGraphic(chatHeaderHBox);
        } else {
            setGraphic(null);
        }
    }

    @FXML
    public void onMouseClicked(MouseEvent mouseEvent) {
        if (onClickResponseCall != null) {
            onClickResponseCall.onResponseCall(discussionHeaderCardModel);
        }
    }

    public void setOnClickResponseCall(ISceneResponseCall<DiscussionHeaderCardModel> onClickResponseCall) {
        this.onClickResponseCall = onClickResponseCall;
    }

    public void setOnCardModelSet(ISceneResponseCall<DiscussionHeaderCardModel> onCardModelSet) {
        this.onCardModelSet = onCardModelSet;
    }

    public boolean hasModelId(Integer id) {
        if (discussionHeaderCardModel == null)
            return false;
        return discussionHeaderCardModel.getDiscussionModel().getIds().contains(id);
    }

    private void loadImage(String pathToImage) {
        if (StringValidator.isStringNotEmpty(pathToImage)) {
            File file = new File(pathToImage);
            profilePictureImage.setImage(new Image(file.toURI().toString()));
        }
    }

    public DiscussionHeaderCardModel getDiscussionHeaderCardModel() {
        return discussionHeaderCardModel;
    }
}
