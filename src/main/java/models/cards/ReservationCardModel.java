package models.cards;

import models.EventModel;
import models.ReservationModel;
import services.IEventService;
import services.ServiceProvider;
import services.IUserService;

public class ReservationCardModel implements TableCardModel {
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
        return getEventModel().getName().contains(filter) ||
                getArtistName().contains(filter) ||
                getBarName().contains(filter);
    }

    public ReservationCardModel getReservationCardModel() {
        return this;
    }
}
