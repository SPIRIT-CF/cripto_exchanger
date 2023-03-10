package com.crypto.exchange.events;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreationEvent<T> {
    private final T object;

    public T get() {
        return object;
    }
}
