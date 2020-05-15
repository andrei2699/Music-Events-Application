package models.cards;

public class ReservationCardModel  implements TableCardModel  {
    @Override
    public boolean containsFilter(String filter) {
        return false;
    }
}
