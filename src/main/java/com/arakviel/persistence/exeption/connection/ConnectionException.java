package com.arakviel.persistence.exeption.connection;

import java.sql.SQLException;

public class ConnectionException extends SQLException {
    public ConnectionException(String reason) {
        super(reason);
    }
}
