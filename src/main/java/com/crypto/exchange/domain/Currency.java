package com.crypto.exchange.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Currency {
    BTC, USDT, ETH,
    USD, EUR, RUB;



    public static Set<Currency> getFiatCurrencies() {
        return Set.of(USD, EUR, RUB);
    }

    public static Set<Currency> getCryptoCurrencies() {
        return Set.of(BTC, USDT, ETH);
    }

    public boolean isFiat() {
        boolean contains = getFiatCurrencies().contains(this);
        return contains;
    }
}
