package controllers.components;

import javafx.scene.control.TableCell;
import models.cards.TableCardModel;

public abstract class DetailsTableConfigData {

    public abstract String getTableColumnText();

    public abstract String getPropertyValueFactory();

    public abstract String getNoContentLabelText();

    public abstract TableCell<TableCardModel, TableCardModel> getCellFactory();


    public static DetailsTableConfigData getEventTableColumnData() {
        return new DetailsTableConfigData() {
            @Override
            public String getTableColumnText() {
                return "Evenimente Dispnibile";
            }

            @Override
            public String getPropertyValueFactory() {
                return "eventCardModel";
            }

            @Override
            public String getNoContentLabelText() {
                return "Fara evenimente";
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
                return "Localuri";
            }

            @Override
            public String getPropertyValueFactory() {
                return "barCardModel";
            }

            @Override
            public String getNoContentLabelText() {
                return "Fara localuri";
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
                return "Artisti";
            }

            @Override
            public String getPropertyValueFactory() {
                return "artistCardModel";
            }

            @Override
            public String getNoContentLabelText() {
                return "Fara artisti";
            }

            @Override
            public TableCell<TableCardModel, TableCardModel> getCellFactory() {
                return new ArtistDetailsCardController();
            }
        };
    }
}
