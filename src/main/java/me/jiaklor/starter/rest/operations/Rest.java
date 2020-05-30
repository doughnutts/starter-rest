package me.jiaklor.starter.rest.operations;

import me.jiaklor.starter.rest.operations.handlers.DeferredResultHandler;
import me.jiaklor.starter.rest.operations.handlers.ResponseHandler;
import me.jiaklor.starter.rest.operations.handlers.ResponseWithCatchHandler;
import me.jiaklor.starter.rest.operations.handlers.RetryHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ForkJoinPool;

public class Rest {

    private Rest() {}

    public static void retry(RetryHandler retryHandler, int attempts) {
        if (attempts < 1) retryHandler.onFailure(new IllegalArgumentException("Minimum retry attempts is 1"));
        for (int count = 0; count < attempts; count++) {
            try {
                retryHandler.run();
                break;
            } catch (Exception e) {
                if (count+1 == attempts) {
                    retryHandler.onFailure(e);
                } else {
                    retryHandler.beforeRetry(e);
                }
            }
        }
    }

    public static <T> ResponseEntity<T> call(ResponseHandler responseHandler) {
        return call(responseHandler, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> call(ResponseHandler responseHandler, HttpStatus status) {
        return new ResponseEntity<>((T) responseHandler.run(), status);
    }

    public static <T> ResponseEntity<T> callWithCatch(ResponseWithCatchHandler responseWithCatchHandler) {
        return callWithCatch(responseWithCatchHandler, HttpStatus.OK, HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<T> callWithCatch(ResponseWithCatchHandler responseWithCatchHandler, HttpStatus success, HttpStatus fail) {
        try {
            return new ResponseEntity<>((T) responseWithCatchHandler.run(), success);
        } catch (Exception e) {
            return new ResponseEntity<>((T) responseWithCatchHandler.onFailure(e, fail), fail);
        }
    }

    public static <T> DeferredResult<ResponseEntity<T>> deferredResult(DeferredResultHandler deferredResultHandler, long... timeout) {
        return deferredResult(deferredResultHandler, HttpStatus.OK, timeout);
    }

    public static <T> DeferredResult<ResponseEntity<T>> deferredResult(DeferredResultHandler deferredResultHandler, HttpStatus success, long... timeout) {
        DeferredResult<ResponseEntity<T>> deferredResult = (timeout.length > 0 && timeout[0] > 0L) ? new DeferredResult<>(timeout[0]) : new DeferredResult<>(30000L);
        deferredResult.onError(throwable -> deferredResultHandler.onError(throwable, deferredResult));
        deferredResult.onTimeout(() -> deferredResultHandler.onTimeout(deferredResult).run());
        deferredResult.onCompletion(() -> deferredResultHandler.onComplete(deferredResult).run());
        deferredResultHandler.config(deferredResult);
        ForkJoinPool.commonPool().submit(() -> {
           try {
               deferredResult.setResult(new ResponseEntity<>((T) deferredResultHandler.run(), success));
           } catch (Exception e) {
               deferredResultHandler.onFailure(e, deferredResult);
           }
        });
        return deferredResult;
    }
}
