package com.arakviel.persistence.exeption.persistence;

/**
 * Thrown by the persistence layer when a problem occurs. All instances of <code>
 * PersistenceException</code> except for instances of * {@link EntityNotFoundException}
 */
public class PersistenceException extends RuntimeException {

    public PersistenceException() {
        super();
    }

    public PersistenceException(String reason) {
        super(reason);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceException(Throwable cause) {
        super(cause);
    }
}
