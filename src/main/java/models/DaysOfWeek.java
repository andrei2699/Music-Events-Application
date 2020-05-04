package models;

public enum DaysOfWeek {
        Luni(0),
        Marti(1),
        Miercuri(2),
        Joi(3),
        Vineri(4),
        Sambata(5),
        Duminica(6);

        private final int value;

        DaysOfWeek(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value + "";
        }

        public static final int NumberOfDays = 7;
    }