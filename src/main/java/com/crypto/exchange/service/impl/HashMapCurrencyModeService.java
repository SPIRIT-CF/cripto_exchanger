package com.crypto.exchange.service.impl;

import com.crypto.exchange.domain.Currency;
import com.crypto.exchange.service.CurrencyModeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HashMapCurrencyModeService implements CurrencyModeService {

  private final Map<Long, Currency> originalCurrency = new HashMap<>();
  private final Map<Long, Currency> targetCurrency = new HashMap<>();
  private final Map<Long, Currency> chosenCurrency = new HashMap<>();


  @Override
  public Currency getOriginalCurrency(long chatId) {
    return originalCurrency.getOrDefault(chatId, Currency.USD);
  }

  @Override
  public Currency getTargetCurrency(long chatId) {
    return targetCurrency.getOrDefault(chatId, Currency.USD);
  }

  @Override
  public Currency getChosenCurrency(long chatId) {
    return chosenCurrency.get(chatId);
  }

  @Override
  public void setOriginalCurrency(long chatId, Currency currency) {
    originalCurrency.put(chatId, currency);
  }

  @Override
  public void setTargetCurrency(long chatId, Currency currency) {
    targetCurrency.put(chatId, currency);
  }

  @Override
  public void setChosenCurrency(long chatId, Currency currency) {
    chosenCurrency.put(chatId, currency);
  }

  @Override
  public void deleteFromOriginal(long chatId, Set<Currency> currencies) {
    currencies.forEach(currency -> originalCurrency.remove(chatId, currency));
  }

  @Override
  public void deleteFromTarget(long chatId, Set<Currency> currencies) {
    currencies.forEach(currency -> targetCurrency.remove(chatId, currency));
  }


}
