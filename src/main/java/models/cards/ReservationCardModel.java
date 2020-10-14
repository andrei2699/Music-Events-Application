package models.cards;

import export.ExportRow;
import export.IConvertCardToExportRow;
import main.LoggedUserData;
import models.BarModel;
import models.EventModel;
import models.ReservationModel;
import services.IBarService;
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
    private final BarModel barModel;

    public ReservationCardModel(ReservationModel reservationModel) {
        this(reservationModel, ServiceProvider.getUserService(), ServiceProvider.getEventService(), ServiceProvider.getBarService());
    }

    public ReservationCardModel(ReservationModel reservationModel, IUserService userService, IEventService eventService, IBarService barService) {
        this.reservationModel = reservationModel;
        eventModel = eventService.getEventUsingEventId(reservationModel.getEvent_id());
        barName = userService.getUser(eventModel.getBar_manager_id()).getName();
        artistName = userService.getUser(eventModel.getArtist_id()).getName();
        barModel = barService.getBar(eventModel.getBar_manager_id());
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
        if (filter == null)
            return true;
        return getEventModel().getName().toLowerCase().contains(filter.toLowerCase()) ||
                getArtistName().toLowerCase().contains(filter.toLowerCase()) ||
                getBarName().toLowerCase().contains(filter.toLowerCase());
    }

    public ReservationCardModel getReservationCardModel() {
        return this;
    }

    @Override
    public List<ExportRow> convertCardModelToExportRowList() {
        List<ExportRow> rowList = new ArrayList<>();

        rowList.add(new ExportRow(EVENT_NAME_TEXT, eventModel.getName()));
        rowList.add(new ExportRow(BAR_NAME_TEXT, barName));
        rowList.add(new ExportRow(BAR_ADRESS_TEXT, barModel.getAddress()));
        rowList.add(new ExportRow(ARTIST_NAME_TEXT, artistName));
        rowList.add(new ExportRow(DATE_TEXT, eventModel.getDate()));
        rowList.add(new ExportRow(TICKET_PRICE_TEXT, eventModel.getPrice() + ""));
        rowList.add(new ExportRow(HOUR_TEXT, eventModel.getStart_hour() + ""));
        rowList.add(new ExportRow(SEAT_NUMBER_TEXT, reservationModel.getReserved_seats() + ""));
        if (LoggedUserData.getInstance().isUserLogged())
            rowList.add(new ExportRow(RESERVATION_MADE_BY_TEXT, LoggedUserData.getInstance().getUserModel().getName()));
        else
            return null;

        return rowList;
    }
}
