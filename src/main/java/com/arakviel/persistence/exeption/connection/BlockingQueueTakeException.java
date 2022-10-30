package com.arakviel.persistence.exeption.connection;

public class BlockingQueueTakeException extends RuntimeException {

    public BlockingQueueTakeException(String reason) {
        super(reason);
    }
}
