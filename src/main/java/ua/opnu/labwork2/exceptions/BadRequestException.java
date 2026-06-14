package ua.opnu.labwork2.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException (String entityName, String param) {
        super(String.format("%s with parametr '%s' does not suit this operation", entityName, param));
    }
}
