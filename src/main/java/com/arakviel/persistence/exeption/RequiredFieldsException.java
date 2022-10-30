package com.arakviel.persistence.exeption;

public class RequiredFieldsException extends NullPointerException {

    public RequiredFieldsException() {
        super(
                "Помилка при створенні об'єкта класу сутності %s."
                        .formatted(
                                Thread.currentThread().getStackTrace().getClass().getSimpleName()));
    }

    public RequiredFieldsException(String reason) {
        super(reason);
    }
}
