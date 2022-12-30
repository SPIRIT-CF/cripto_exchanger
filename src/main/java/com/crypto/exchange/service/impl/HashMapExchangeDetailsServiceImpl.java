package com.crypto.exchange.service.impl;

import com.crypto.exchange.domain.City;
import com.crypto.exchange.domain.Country;
import com.crypto.exchange.domain.Currency;
import com.crypto.exchange.service.ExchangeDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HashMapExchangeDetailsServiceImpl implements ExchangeDetailsService {

    //private final Map<Long, String> paymentMethodMap = new HashMap<>();
    private final Map<Long, City> cityMap = new HashMap<>();
    private final Map<Long, Country> countryMap = new HashMap<>();

/*    @Override
    public String getPaymentMethod(long chatId) {
        return null;
    }

    @Override
    public void setPaymentMethod(long chatId, String paymentMethod) {

    }*/

    @Override
    public Country getCountry(long chatId) {
        return countryMap.get(chatId);
    }

    @Override
    public void setCountry(long chatId, Country country) {
        countryMap.put(chatId, country);
    }

    @Override
    public City getCity(long chatId) {
        return cityMap.get(chatId);
    }

    @Override
    public void setCity(long chatId, City city) {
        cityMap.put(chatId, city);
    }
}
