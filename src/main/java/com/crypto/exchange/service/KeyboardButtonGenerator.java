package com.crypto.exchange.service;

import com.crypto.Currency;
import com.crypto.exchange.CurrencyModeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.crypto.exchange.Command.SET_ORIGINAL;
import static com.crypto.exchange.Command.SET_TARGET;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeyboardButtonGenerator {

    private final CurrencyModeService currencyModeService;

    public List<List<InlineKeyboardButton>> generateDefaultCurrenciesButtons(Long chatId) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        Currency originalCurrency = currencyModeService.getOriginalCurrency(chatId);
        Currency targetCurrency = currencyModeService.getTargetCurrency(chatId);
        for (Currency currency : Currency.values()) {
            buttons.add(
                    Arrays.asList(
                            InlineKeyboardButton.builder()
                                    .text(getCurrencyButton(originalCurrency, currency))
                                    .callbackData(SET_ORIGINAL + " " + currency)
                                    .build(),
                            InlineKeyboardButton.builder()
                                    .text(getCurrencyButton(targetCurrency, currency))
                                    .callbackData(SET_TARGET + " " + currency)
                                    .build()));
        }
        buttons.add(
                List.of(InlineKeyboardButton.builder()
                        .text("Done!")
                        .callbackData("DONE")
                        .build())
        );
        return buttons;
    }

    public List<List<InlineKeyboardButton>> generateChosenCurrenciesButtons(Long chatId) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        Currency originalCurrency = currencyModeService.getOriginalCurrency(chatId);
        Currency targetCurrency = currencyModeService.getTargetCurrency(chatId);
        buttons.add(
                Arrays.asList(
                        InlineKeyboardButton.builder()
                                .text(originalCurrency.name())
                                .callbackData(SET_ORIGINAL + " " + originalCurrency)
                                .build(),
                        InlineKeyboardButton.builder()
                                .text(targetCurrency.name())
                                .callbackData(SET_ORIGINAL + " " + targetCurrency)
                                .build()));
        return buttons;
    }

    private String getCurrencyButton(Currency saved, Currency current) {
        return saved == current ? current + " ✅" : current.name();
    }
}
