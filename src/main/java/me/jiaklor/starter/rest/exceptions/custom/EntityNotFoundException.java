package me.jiaklor.starter.rest.exceptions.custom;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class klass, String... searchParamsMap) {
        super(EntityNotFoundException.generateMessage(klass.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) + " was nt found for parameters " + searchParams;
    }

    private static <K, V> Map<K, V> toMap(Class<K> key, Class<V> value, Object... entries) {
        if (entries.length % 2 == 1) throw new IllegalArgumentException("Invalid entries");
        return IntStream.range(0, entries.length/2)
                .map(i -> i * 2)
                .collect(HashMap::new, (k, v) -> k.put(key.cast(entries[v]), value.cast(entries[v + 1])), Map::putAll);
    }
}
