package com.kabq.arquitectura_hexagonal.infrastructure.entrypoint.graphql.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.BindException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;
import org.springframework.beans.TypeMismatchException;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

@Component
public class GraphqlExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        Throwable root = ex.getCause() != null ? ex.getCause() : ex;

        if (ex instanceof IllegalArgumentException
                || ex instanceof ConstraintViolationException
                || ex instanceof BindException
                || ex instanceof TypeMismatchException
                || ex instanceof DateTimeParseException
                || root instanceof IllegalArgumentException
                || root instanceof DateTimeParseException) {
            String message = root.getMessage() != null ? root.getMessage() : ex.getMessage();
            return error(env, message, ErrorType.BAD_REQUEST);
        }

        if (ex instanceof NoSuchElementException) {
            return error(env, ex.getMessage(), ErrorType.NOT_FOUND);
        }

        return error(env, "Error interno del servidor", ErrorType.INTERNAL_ERROR);
    }

    private GraphQLError error(DataFetchingEnvironment env, String message, ErrorType errorType) {
        return GraphqlErrorBuilder.newError(env)
                .message(message)
                .errorType(errorType)
                .build();
    }
}
