package com.vilim.leapwise.exception;

import lombok.Getter;

@Getter
public class ExpressionEvaluationException extends RuntimeException {

    public ExpressionEvaluationException(final String message) {
        super(message);
    }
}
