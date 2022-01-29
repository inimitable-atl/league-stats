package com.inimitable.report.data.cache;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.Collection;

@Getter
@Builder(toBuilder = true)
public class RetrievalResult<K, V> {
    @Singular
    private final Collection<V> hits;
    @Singular
    private final Collection<K> misses;
}