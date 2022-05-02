package com.crypto.exchange;

import com.crypto.Currency;
import com.crypto.exchange.events.CreationEvent;
import com.crypto.exchange.events.SendMessageCreationEvent;
import com.crypto.exchange.events.UpdateCreationEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeBot extends TelegramLongPollingBot {

    @Getter
    @Value("${bot.name}")
    private String botUsername;

    @Getter
    @Value("${bot.token}")
    private String botToken;

    private final ApplicationEventPublisher publisher;

    private final CurrencyModeService currencyModeService;

    @Override
    public void onUpdateReceived(Update update) {
        publisher.publishEvent(new UpdateCreationEvent(update));
        /*if (update.hasMessage()) handleMessage(update.getMessage());
        else if (update.hasCallbackQuery()) handleCallback(update.getCallbackQuery());*/

    }

    @EventListener(SendMessageCreationEvent.class)
    public void executeSafe(CreationEvent<SendMessage> event) {
        final SendMessage message = event.get();
        try {
            execute(message);
            log.debug("Executed {}", message);
        } catch (TelegramApiException e) {
            log.error("Exception while sending message {} to user: {}", message, e.getMessage());
        }
    }

    private void handleCallback(CallbackQuery callbackQuery) {
        try {
        Message message = callbackQuery.getMessage();
        String[] param = callbackQuery.getData().split(":");
        String action = param[0];
        Currency newCurrency = null;
        if (param.length > 1) {
            newCurrency = Currency.valueOf(param[1]);
        }
        switch (action) {
            case "ORIGINAL" -> currencyModeService.setOriginalCurrency(message.getChatId(), newCurrency);
            case "TARGET" -> currencyModeService.setTargetCurrency(message.getChatId(), newCurrency);
            case "DONE" -> execute(SendMessage.builder()
                    .text("Please choose currency to be given/received and enter its quantity")
                    .chatId(message.getChatId().toString())
                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(generateChosenCurrenciesButtons(message)).build())
                    .build()
            );
        }
            List<List<InlineKeyboardButton>> buttons = generateDefaultCurrenciesButtons(message);

            execute(
                    EditMessageReplyMarkup.builder()
                            .chatId(message.getChatId().toString())
                            .messageId(message.getMessageId())
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                            .build());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private List<List<InlineKeyboardButton>> generateDefaultCurrenciesButtons(Message message) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        Currency originalCurrency = currencyModeService.getOriginalCurrency(message.getChatId());
        Currency targetCurrency = currencyModeService.getTargetCurrency(message.getChatId());
        for (Currency currency : Currency.values()) {
            buttons.add(
                    Arrays.asList(
                            InlineKeyboardButton.builder()
                                    .text(getCurrencyButton(originalCurrency, currency))
                                    .callbackData("ORIGINAL:" + currency)
                                    .build(),
                            InlineKeyboardButton.builder()
                                    .text(getCurrencyButton(targetCurrency, currency))
                                    .callbackData("TARGET:" + currency)
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

    private List<List<InlineKeyboardButton>> generateChosenCurrenciesButtons(Message message) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        Currency originalCurrency = currencyModeService.getOriginalCurrency(message.getChatId());
        Currency targetCurrency = currencyModeService.getTargetCurrency(message.getChatId());
        buttons.add(
                Arrays.asList(
                        InlineKeyboardButton.builder()
                                .text(originalCurrency.name())
                                .callbackData("ORIGINAL")
                                .build(),
                        InlineKeyboardButton.builder()
                                .text(targetCurrency.name())
                                .callbackData("TARGET")
                                .build()));
        return buttons;
    }

    private String getCurrencyButton(Currency saved, Currency current) {
        return saved == current ? current + " ✅" : current.name();
    }
}
