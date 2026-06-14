package ua.opnu.labwork2.exceptions;

public class ConflictOperationException extends RuntimeException {
    public ConflictOperationException(String entityName, Long id, String reason) {
        super(String.format("you cannot delete %s with id=%d because its records %s", entityName, id, reason));
    }
}
