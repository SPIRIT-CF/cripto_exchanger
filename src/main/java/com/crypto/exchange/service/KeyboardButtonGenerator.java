package com.crypto.exchange.service;

import com.crypto.exchange.Command;
import com.crypto.exchange.domain.Bank;
import com.crypto.exchange.domain.City;
import com.crypto.exchange.domain.Country;
import com.crypto.exchange.domain.Currency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

import static com.crypto.exchange.Command.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeyboardButtonGenerator {

    private final CurrencyModeService currencyModeService;

    public List<List<InlineKeyboardButton>> generateDefaultCurrenciesButtons(Long chatId, List<Currency> originalCurrencies, List<Currency> targetCurrencies) {
        Currency currentOriginalCurrency = currencyModeService.getOriginalCurrency(chatId);
        Currency currentTargetCurrency = currencyModeService.getTargetCurrency(chatId);

        List<List<InlineKeyboardButton>> buttons = generateButtons(originalCurrencies, targetCurrencies, currentOriginalCurrency, currentTargetCurrency);

        Command command;
        if (currentOriginalCurrency.isFiat() || currentTargetCurrency.isFiat()) command = SET_PAYMENT_METHOD;
        else command = DONE;

        buttons.add(
                List.of(InlineKeyboardButton.builder()
                        .text("Done!")
                        .callbackData(String.valueOf(command))
                        .build())
        );
        return buttons;
    }

    public List<List<InlineKeyboardButton>> generateExchangedCurrenciesButtons(Long chatId) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        Currency originalCurrency = currencyModeService.getOriginalCurrency(chatId);
        Currency targetCurrency = currencyModeService.getTargetCurrency(chatId);
        buttons.add(
                Arrays.asList(
                        InlineKeyboardButton.builder()
                                .text(originalCurrency.name())
                                .callbackData(SET_CHOSEN + " " + originalCurrency)
                                .build(),
                        InlineKeyboardButton.builder()
                                .text(targetCurrency.name())
                                .callbackData(SET_CHOSEN + " " + targetCurrency)
                                .build()));
        return buttons;
    }

    public List<List<InlineKeyboardButton>> generateChosenCurrenciesButtons(Long chatId) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        Currency originalCurrency = currencyModeService.getOriginalCurrency(chatId);
        Currency targetCurrency = currencyModeService.getTargetCurrency(chatId);
        Currency chosenCurrency = currencyModeService.getChosenCurrency(chatId);
        buttons.add(
                Arrays.asList(
                        InlineKeyboardButton.builder()
                                .text(getCurrencyButton(originalCurrency, chosenCurrency))
                                .callbackData(SET_CHOSEN + " " + originalCurrency)
                                .build(),
                        InlineKeyboardButton.builder()
                                .text(getCurrencyButton(targetCurrency, chosenCurrency))
                                .callbackData(SET_CHOSEN + " " + targetCurrency)
                                .build()));
        return buttons;
    }

    public List<List<InlineKeyboardButton>> generatePaymentMethodsButtons(Long chatId) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        //TODO записать в свою мапу
        buttons.add(
                Arrays.asList(
                        InlineKeyboardButton.builder()
                                .text("Online bank transaction")
                                .callbackData(SET_BANK + "")
                                .build(),
                        InlineKeyboardButton.builder()
                                .text("Cash")
                                .callbackData(SET_COUNTRY + "")
                                .build()));
        return buttons;
    }

    public List<List<InlineKeyboardButton>> generateBanksListButtons(Long chatId) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        for (Bank bank : Bank.values()) {
            buttons.add(
                    Collections.singletonList(
                            InlineKeyboardButton.builder()
                                    .text(bank.name())
                                    .callbackData(SET_CHOSEN + " " + "Online")
                                    .build()));
        }
        return buttons;
    }

    public List<List<InlineKeyboardButton>> generateCountriesButtons() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        for (Country country : Country.values()) {
            buttons.add(
                    Collections.singletonList(
                            InlineKeyboardButton.builder()
                                    .text(country.name())
                                    .callbackData(SET_CITY + " " + country.name())
                                    .build()));
        }
        return buttons;
    }

    public List<List<InlineKeyboardButton>> generateCitiesButtons(String countryName) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        Country country = Country.valueOf(countryName);
        for (City city : country.getCities()) {
            buttons.add(
                    Collections.singletonList(
                            InlineKeyboardButton.builder()
                                    .text(city.name())
                                    .callbackData(SET_CHOSEN + " " + city.name())
                                    .build()));
        }
        return buttons;
    }

    private List<List<InlineKeyboardButton>> generateButtons(List<Currency> originalCurrencies, List<Currency> targetCurrencies, Currency currentOriginalCurrency, Currency currentTargetCurrency) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        for (int buttonPairNum = 0; buttonPairNum < originalCurrencies.size(); buttonPairNum++) {
            List<InlineKeyboardButton> inlineButtons = new ArrayList<>();
            Currency originalCurrency = originalCurrencies.get(buttonPairNum);

            Currency targetCurrency = null;
            if (buttonPairNum < targetCurrencies.size()) targetCurrency = targetCurrencies.get(buttonPairNum);

            inlineButtons.add(createButton(SET_ORIGINAL, originalCurrency, currentOriginalCurrency));
            inlineButtons.add(createButton(SET_TARGET, targetCurrency, currentTargetCurrency));

            buttons.add(inlineButtons);
        }
        return buttons;
    }

    private InlineKeyboardButton createButton(Command command, Currency currency, Currency currentCurrency) {
        String text = currency != null
                ? getCurrencyButton(currency, currentCurrency)
                : "Currency not available";

        String callbackData = currency != null
                ? command + " " + currency
                : " ";

        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callbackData)
                .build();
    }

    private String getCurrencyButton(Currency saved, Currency chosen) {
        if (chosen == null) return " ";
        return saved == chosen ? saved + " ✅" : saved.name();
    }
}
