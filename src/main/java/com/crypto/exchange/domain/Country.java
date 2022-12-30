package com.crypto.exchange.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public enum Country {
    RUSSIA(Set.of(City.MOSCOW, City.SPB)),
    OAE(Set.of(City.DUBAI)),
    USA(Set.of(City.NY));

    private Set<City> cities;


}
