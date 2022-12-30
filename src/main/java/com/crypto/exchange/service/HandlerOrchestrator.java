package com.crypto.exchange.service;

import com.crypto.exchange.annotations.BotCommand;
import com.crypto.exchange.service.impl.handlers.CurrencyQuantityHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
@Slf4j
@RequiredArgsConstructor
public class HandlerOrchestrator {

    private final List<AbstractBaseHandler> handlers;

    public void operate(Long userId, Long chatId, Integer messageId, String text) {
        try {
            AbstractBaseHandler handler = getHandler(text);
            log.debug("Found handler {} for command {}", handler.getClass().getSimpleName(), text);
            handler.handle(userId, chatId, messageId, text);
        } catch (UnsupportedOperationException e) {
            log.error("Command: {} is unsupported", text);
        }
    }

    protected AbstractBaseHandler getHandler(String text) {
        return handlers.stream()
                .filter(h -> h.getClass()
                        .isAnnotationPresent(BotCommand.class))
                .filter(h -> Stream.of(h.getClass()
                                .getAnnotation(BotCommand.class)
                                .command())
                        .anyMatch(c -> c.toString().equalsIgnoreCase(extractCommand(text))))
                .findAny().orElseGet(() -> handlers.stream()
                        .filter(CurrencyQuantityHandler.class::isInstance)
                        .findAny()
                        .orElseThrow(UnsupportedOperationException::new));
    }

    private static String extractCommand(String text) {
        if (!text.isBlank()) return text.split(" ")[0];
        else return  null;
    }
}
