package controllers.components.cardsTableView;

import controllers.components.*;
import javafx.scene.control.TableCell;
import models.cards.TableCardModel;

import static main.ApplicationResourceStrings.*;

public abstract class DetailsTableConfigData {

    public abstract String getTableColumnText();

    public abstract String getPropertyValueFactory();

    public abstract String getNoContentLabelText();

    public abstract TableCell<TableCardModel, TableCardModel> getCellFactory();


    public static DetailsTableConfigData getEventTableColumnData() {
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
                return new EventDetailsCardController();
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

    public static DetailsTableConfigData getReservationTableColumnData() {
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
                return new ReservationDetailsCardController();
            }
        };
    }

    public static DetailsTableConfigData getMessageTableConfigData() {
        return new DetailsTableConfigData() {
            @Override
            public String getTableColumnText() {
                return "Conversatie";
            }

            @Override
            public String getPropertyValueFactory() {
                return "discussionMessageCardModel";
            }

            @Override
            public String getNoContentLabelText() {
                return "Fara mesaje";
            }

            @Override
            public TableCell<TableCardModel, TableCardModel> getCellFactory() {
                return new DiscussionMessageCardController();
            }
        };
    }
}
