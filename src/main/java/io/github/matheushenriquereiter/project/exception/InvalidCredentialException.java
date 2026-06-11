package io.github.matheushenriquereiter.project.exception;

import lombok.Getter;

@Getter
public class InvalidCredentialException extends RuntimeException {
    private final String field;

    public InvalidCredentialException(String field, String message) {
        super(message);
        this.field = field;
    }
}
