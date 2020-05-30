package me.jiaklor.starter.rest.logging;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class SimpleThreadId extends ClassicConverter {

    private static final AtomicInteger pid = new AtomicInteger();

    private static final ThreadLocal<String> threadId = ThreadLocal.withInitial(() -> String.format("%03d", pid.incrementAndGet()));

    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        return threadId.get();
    }
}
