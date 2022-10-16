/*
package com.crypto.exchange.service.impl;

import com.crypto.exchange.service.CurrencyModeService;
import com.crypto.exchange.service.AbstractBaseHandler;
import com.crypto.exchange.service.KeyboardButtonGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextHandler extends AbstractBaseHandler {

    private final KeyboardButtonGenerator buttonGenerator;
    private final CurrencyModeService currencyModeService;

    public void handle(Long userId, Long chatId, Integer messageId, String text) {
        Double value = null;
        try {
            value = Double.valueOf(text.trim());
        } catch (NumberFormatException e) {
            log.error("NumberFormatException. Error at parsing currency value");
        }
        System.out.println(value);
    }
}
*/
