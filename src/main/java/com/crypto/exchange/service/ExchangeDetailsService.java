package com.crypto.exchange.service;


import com.crypto.exchange.domain.City;
import com.crypto.exchange.domain.Country;

public interface ExchangeDetailsService {

/*  String getPaymentMethod(long chatId);
  void setPaymentMethod(long chatId, String paymentMethod);*/


  Country getCountry(long chatId);
  void setCountry(long chatId, Country country);

  City getCity(long chatId);
  void setCity(long chatId, City city);

/*  String getOriginalCurrency(long chatId);

  Currency getTargetCurrency(long chatId);

  Currency getChosenCurrency(long chatId);

  void setOriginalCurrency(long chatId, Currency currency);

  void setTargetCurrency(long chatId, Currency currency);

  void setChosenCurrency(long chatId, Currency currency);

  void deleteFromOriginal(long chatId, Set<Currency> currencies);

  void deleteFromTarget(long chatId, Set<Currency> currencies);*/
}
