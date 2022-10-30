package com.arakviel.persistence.exeption.persistence;

import com.arakviel.persistence.Dao;

/**
 * Thrown by the persistence layer when {@link Dao#findOneById(int)} is called and the object no
 * longer exists in the database.
 *
 * @see Dao#findOneById(int)
 */
public class EntityNotFoundException extends PersistenceException {
    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
