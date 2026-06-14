package ua.opnu.labwork2.exceptions;

public class DatabaseOperationException extends RuntimeException {
    public DatabaseOperationException(String entityName) {
        super(String.format("%s has issues with database connection", entityName));
    }
}
