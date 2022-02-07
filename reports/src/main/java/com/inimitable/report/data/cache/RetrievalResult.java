package com.inimitable.report.data.cache;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import reactor.core.publisher.Flux;

import java.util.Collection;

@Getter
@Builder
public class RetrievalResult<K, V> {
    private final Flux<V> hits;
    private final Flux<K> misses;
}