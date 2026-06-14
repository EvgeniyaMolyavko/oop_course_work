package ua.opnu.labwork2.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String entityName, Long id) {
        super(String.format("%s with id=%d is absent", entityName, id));
    }

    public ResourceNotFoundException(String entityName, Long id, String entityName2, Long id2) {
        super(String.format("Data for %s with id=%d and %s with id=%d is absent", entityName, id, entityName2, id2));
    }
}
