package io.github.matheushenriquereiter.project.exceptions;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorResponse {
    private final List<Violation> errors = new ArrayList<>();

    public void addError(String field, String message) {
        errors.add(new Violation(field, message));
    }
}