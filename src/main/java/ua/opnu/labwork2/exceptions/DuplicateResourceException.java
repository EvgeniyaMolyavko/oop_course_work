package ua.opnu.labwork2.exceptions;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String entityName, String param) {
        super(String.format("%s with parametr '%s' already exists", entityName, param));
    }
}
