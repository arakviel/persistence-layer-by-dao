package com.arakviel.persistence.exeption.persistence;

/**
 * Thrown by the persistence layer when EntityBuild is executed on a query and there is no result to
 * return.
 */
public class NoResultException extends PersistenceException {

    public NoResultException() {
        super();
    }

    public NoResultException(String message) {
        super(message);
    }
}
