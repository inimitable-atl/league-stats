package com.inimitable.report.data.cache;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractInMemoryCache<K, V> {
    private final Map<K, V> cache = new ConcurrentHashMap<>();

    public void put(V source) {
        cache.put(getKey(source), source);
    }

    public abstract K getKey(V source);

    public RetrievalResult<K, V> get(Collection<K> keys) {
        RetrievalResult.RetrievalResultBuilder<K, V> builder = RetrievalResult.builder();
        keys.forEach(id -> {
            V hit = cache.get(id);
            if (hit == null) {
                builder.miss(id);
            } else {
                builder.hit(hit);
            }
        });
        return builder.build();
    }
}
