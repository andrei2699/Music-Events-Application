package models;

import java.util.Arrays;
import java.util.Objects;

public class CrashServiceModel extends EntityModel {
    private final String exceptionMessage;
    private final StackTraceElement[] exceptionStackTrace;
    private final String date;

    public CrashServiceModel(int id, String exceptionMessage, StackTraceElement[] exceptionStackTrace, String date) {
        super(id);
        this.exceptionMessage = exceptionMessage;
        this.exceptionStackTrace = exceptionStackTrace;
        this.date = date;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public StackTraceElement[] getExceptionStackTrace() {
        return exceptionStackTrace;
    }

    public String getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrashServiceModel that = (CrashServiceModel) o;
        return Objects.equals(exceptionMessage, that.exceptionMessage) &&
                Arrays.equals(exceptionStackTrace, that.exceptionStackTrace) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(exceptionMessage, date);
        result = 31 * result + Arrays.hashCode(exceptionStackTrace);
        return result;
    }
}
