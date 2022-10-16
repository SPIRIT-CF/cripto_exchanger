package com.crypto.exchange.service;


import com.crypto.exchange.domain.Currency;

import java.util.Set;

public interface CurrencyModeService {

  Currency getOriginalCurrency(long chatId);

  Currency getTargetCurrency(long chatId);

  Currency getChosenCurrency(long chatId);

  void setOriginalCurrency(long chatId, Currency currency);

  void setTargetCurrency(long chatId, Currency currency);

  void setChosenCurrency(long chatId, Currency currency);

  void deleteFromOriginal(long chatId, Set<Currency> currencies);

  void deleteFromTarget(long chatId, Set<Currency> currencies);
}
