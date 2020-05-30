package me.jiaklor.starter.rest.operations.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

public interface DeferredResultHandler {
    Object run() throws Exception;
    default <T> void onError(Throwable t, DeferredResult<ResponseEntity<T>> deferredResult) {
        deferredResult.setErrorResult(t);
    }
    default <T> Runnable onTimeout(DeferredResult<ResponseEntity<T>> deferredResult) {
        return () -> deferredResult.setResult(new ResponseEntity<>(HttpStatus.PROCESSING));
    }
    default <T> void config(DeferredResult<ResponseEntity<T>> deferredResult) {}
    default <T> Runnable onComplete(DeferredResult<ResponseEntity<T>> deferredResult) {
        return () -> {};
    }
    default <T> void onFailure(Exception e, DeferredResult<ResponseEntity<T>> deferredResult) {
        onError(e, deferredResult);
    }
}
