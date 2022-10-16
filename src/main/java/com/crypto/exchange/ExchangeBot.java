package com.crypto.exchange;

import com.crypto.exchange.events.CreationEvent;
import com.crypto.exchange.events.SendMessageCreationEvent;
import com.crypto.exchange.events.SendMessageEditionEvent;
import com.crypto.exchange.events.UpdateCreationEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

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

    @Override
    public void onUpdateReceived(Update update) {
        publisher.publishEvent(new UpdateCreationEvent(update));
    }

    @EventListener({SendMessageCreationEvent.class, SendMessageEditionEvent.class})
    public void executeSafe(CreationEvent<? extends BotApiMethod<Serializable>> event) {
        try {
            execute(event.get());
        } catch (TelegramApiException e) {
            log.error("Exception while sending message {} to user: {}", event.get(), e.getMessage());
        }
    }
}
