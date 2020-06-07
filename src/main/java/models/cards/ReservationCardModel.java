package models.cards;

import export.ExportRow;
import export.IConvertCardToExportRow;
import main.LoggedUserData;
import models.BarModel;
import models.EventModel;
import models.ReservationModel;
import services.IEventService;
import services.IUserService;
import services.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

import static main.ApplicationResourceStrings.*;

public class ReservationCardModel implements TableCardModel, IConvertCardToExportRow {
    private final String barName;
    private final String artistName;
    private final EventModel eventModel;
    private final ReservationModel reservationModel;

    public ReservationCardModel(ReservationModel reservationModel) {
        this.reservationModel = reservationModel;

        IUserService userService = ServiceProvider.getUserService();
        IEventService eventService = ServiceProvider.getEventService();

        eventModel = eventService.getEventUsingEventId(reservationModel.getEvent_id());
        barName = userService.getUser(eventModel.getBar_manager_id()).getName();
        artistName = userService.getUser(eventModel.getArtist_id()).getName();
    }

    public String getBarName() {
        return barName;
    }

    public String getArtistName() {
        return artistName;
    }

    public EventModel getEventModel() {
        return eventModel;
    }

    public ReservationModel getReservationModel() {
        return reservationModel;
    }

    public boolean containsFilter(String filter) {
        return getEventModel().getName().toLowerCase().contains(filter) ||
                getArtistName().toLowerCase().contains(filter) ||
                getBarName().toLowerCase().contains(filter);
    }

    public ReservationCardModel getReservationCardModel() {
        return this;
    }

    @Override
    public List<ExportRow> convertCardModelToExportRowList() {
        List<ExportRow> rowList = new ArrayList<>();

        BarModel barModel = ServiceProvider.getBarService().getBar(eventModel.getBar_manager_id());

        rowList.add(new ExportRow(EVENT_NAME_TEXT, eventModel.getName()));
        rowList.add(new ExportRow(BAR_NAME_TEXT, barName));
        rowList.add(new ExportRow(BAR_ADRESS_TEXT, barModel.getAddress()));
        rowList.add(new ExportRow(ARTIST_NAME_TEXT, artistName));
        rowList.add(new ExportRow(DATE_TEXT, eventModel.getDate()));
        rowList.add(new ExportRow(HOUR_TEXT, eventModel.getStart_hour() + ""));
        rowList.add(new ExportRow(SEAT_NUMBER_TEXT, reservationModel.getReserved_seats() + ""));
        rowList.add(new ExportRow(RESERVATION_MADE_BY_TEXT, LoggedUserData.getInstance().getUserModel().getName()));

        return rowList;
    }
}
