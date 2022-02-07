package com.inimitable.report.data.cache;

import org.apache.commons.lang3.tuple.Pair;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractInMemoryCache<K, V> {
    private final Map<K, V> cache = new ConcurrentHashMap<>();

    public void put(V source) {
        cache.put(getKey(source), source);
    }

    public abstract K getKey(V source);

    public RetrievalResult<K, V> get(Collection<K> keys) {
        // Create flux of cache result (key, value) where value == null if it is a cache miss
        Flux<Pair<K, V>> fetchResultFlux = Flux.fromIterable(keys)
                .map(key -> Pair.of(key, cache.get(key)));
        return RetrievalResult.<K, V>builder()
                .hits(fetchResultFlux.mapNotNull(Pair::getValue))
                .misses(fetchResultFlux.filter(pair -> pair.getValue() == null).map(Pair::getKey))
                .build();
    }
}
