package me.jiaklor.starter.rest.operations.handlers;

import me.jiaklor.starter.rest.exceptions.domain.ApiError;
import org.springframework.http.HttpStatus;

public interface ResponseWithCatchHandler {
    Object run ();
    default ApiError onFailure(Exception e, HttpStatus status) {
        return new ApiError(status, e);
    }
}
