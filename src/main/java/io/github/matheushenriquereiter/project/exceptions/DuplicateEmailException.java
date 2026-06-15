package io.github.matheushenriquereiter.project.exceptions;

import lombok.Getter;

@Getter
public class DuplicateEmailException extends RuntimeException {
    private final String field;

    public DuplicateEmailException() {
        super("Email already associated with a user");
        this.field = "email";
    }
}
