package controllers;

import javafx.scene.control.TableCell;

public abstract class DetailsTableConfigData {

    private static final String NO_EVENTS_TABLE_VIEW_LABEL = "Fara evenimente";
    private static final String NO_BARS_TABLE_VIEW_LABEL = "Fara localuri";
    private static final String NO_ARTISTS_TABLE_VIEW_LABEL = "Fara artisti";


    private DetailsTableConfigData() {
    }

    public abstract String getTableColumnText();

    public abstract String getPropertyValueFactory();

    public abstract String getNoContentLabelText();

    public abstract TableCell<TableCardModel, TableCardModel> getCellFactory();


    public static DetailsTableConfigData getEventTableColumnData(){
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
                return NO_EVENTS_TABLE_VIEW_LABEL;
            }

            @Override
            public TableCell<TableCardModel, TableCardModel> getCellFactory() {
                return new EventDetailsCardController();
            }

        };
    }

    public static DetailsTableConfigData getBarTableColumnData(){
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
                return NO_BARS_TABLE_VIEW_LABEL;
            }

            @Override
            public TableCell<TableCardModel, TableCardModel> getCellFactory() {
                return new BarDetailsCardController();
            }

        };
    }

    public static DetailsTableConfigData getArtistTableColumnData(){
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
                return NO_ARTISTS_TABLE_VIEW_LABEL;
            }

            @Override
            public TableCell<TableCardModel, TableCardModel> getCellFactory() {
                return new ArtistDetailsCardController();
            }

        };
    }
}
