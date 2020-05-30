package me.jiaklor.starter.rest.operations.handlers;

public interface RetryHandler {
    void run() throws Exception;
    default void onFailure(Exception e) {}
    default void beforeRetry(Exception e) {}
}
