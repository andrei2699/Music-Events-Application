package controllers.components.cardsTableView;

import controllers.components.ArtistDetailsCardController;
import controllers.components.BarDetailsCardController;
import controllers.components.EventDetailsCardController;
import controllers.components.ReservationDetailsCardController;
import controllers.scenes.ISceneResponseCall;
import javafx.scene.control.TableCell;
import models.cards.TableCardModel;

import static main.ApplicationResourceStrings.*;

public abstract class DetailsTableConfigData {

    public abstract String getTableColumnText();

    public abstract String getPropertyValueFactory();

    public abstract String getNoContentLabelText();

    public abstract TableCell<TableCardModel, TableCardModel> getCellFactory();


    public static DetailsTableConfigData getEventTableColumnData(ISceneResponseCall<Integer> responseCall) {
        return new DetailsTableConfigData() {
            @Override
            public String getTableColumnText() {
                return AVAILABLE_EVENTS_TEXT;
            }

            @Override
            public String getPropertyValueFactory() {
                return "eventCardModel";
            }

            @Override
            public String getNoContentLabelText() {
                return NO_EVENTS_TEXT;
            }

            @Override
            public TableCell<TableCardModel, TableCardModel> getCellFactory() {
                EventDetailsCardController eventDetailsCardController = new EventDetailsCardController();
                eventDetailsCardController.setResponseCall(responseCall);
                return eventDetailsCardController;
            }
        };
    }

    public static DetailsTableConfigData getBarTableColumnData() {
        return new DetailsTableConfigData() {
            @Override
            public String getTableColumnText() {
                return BARS_TEXT;
            }

            @Override
            public String getPropertyValueFactory() {
                return "barCardModel";
            }

            @Override
            public String getNoContentLabelText() {
                return NO_BARS_TEXT;
            }

            @Override
            public TableCell<TableCardModel, TableCardModel> getCellFactory() {
                return new BarDetailsCardController();
            }

        };
    }

    public static DetailsTableConfigData getArtistTableColumnData() {
        return new DetailsTableConfigData() {
            @Override
            public String getTableColumnText() {
                return ARTISTS_TEXT;
            }

            @Override
            public String getPropertyValueFactory() {
                return "artistCardModel";
            }

            @Override
            public String getNoContentLabelText() {
                return NO_ARTISTS_TEXT;
            }

            @Override
            public TableCell<TableCardModel, TableCardModel> getCellFactory() {
                return new ArtistDetailsCardController();
            }
        };
    }

    public static DetailsTableConfigData getReservationTableColumnData(ISceneResponseCall<Integer> responseCall) {
        return new DetailsTableConfigData() {
            @Override
            public String getTableColumnText() {
                return MY_RESERVATIONS_TEXT;
            }

            @Override
            public String getPropertyValueFactory() {
                return "reservationCardModel";
            }

            @Override
            public String getNoContentLabelText() {
                return NO_RESERVATIONS_TEXT;
            }

            @Override
            public TableCell<TableCardModel, TableCardModel> getCellFactory() {
                ReservationDetailsCardController reservationDetailsCardController = new ReservationDetailsCardController();
                reservationDetailsCardController.setResponseCall(responseCall);
                return reservationDetailsCardController;
            }
        };
    }
}
