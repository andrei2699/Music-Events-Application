package utils;

public final class StringValidator {
    public static boolean isStringNotEmpty(String value) {
        return value != null && !value.isEmpty() && !value.isBlank();
    }
}
