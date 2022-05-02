package com.crypto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Currency {
    USD, EUR, RUB,

    BTC, USDT, ETH;

    public static Set<Currency> getFiatCurrencies() {
        return Set.of(USD, EUR, RUB);
    }

    public static Set<Currency> getCryptoCurrencies() {
        return Set.of(BTC, USDT, ETH);
    }
}
