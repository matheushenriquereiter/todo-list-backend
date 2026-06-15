package io.github.matheushenriquereiter.project.exceptions;

import lombok.Getter;

@Getter
public class InvalidAttributeException extends RuntimeException {
    private final String field;

    public InvalidAttributeException(String field, String message) {
        super(message);
        this.field = field;
    }
}
