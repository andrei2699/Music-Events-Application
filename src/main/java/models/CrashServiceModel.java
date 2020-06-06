package models;

import java.util.Objects;

public class CrashServiceModel extends EntityModel {
    private final String exceptionMessage;
    private final String date;

    public CrashServiceModel(int id, String exceptionMessage, String date) {
        super(id);
        this.exceptionMessage = exceptionMessage;
        this.date = date;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public String getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrashServiceModel that = (CrashServiceModel) o;
        return Objects.equals(exceptionMessage, that.exceptionMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exceptionMessage);
    }
}
