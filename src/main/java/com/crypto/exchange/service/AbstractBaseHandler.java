package com.crypto.exchange.service;

import com.crypto.exchange.events.SendMessageCreationEvent;
import com.crypto.exchange.events.SendMessageEditionEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@NoArgsConstructor
public abstract class AbstractBaseHandler {

    @Autowired
    protected ApplicationEventPublisher publisher;

    public final void publish(SendMessage message) {
        this.publisher.publishEvent(new SendMessageCreationEvent(message));
    }

    public final void publish(EditMessageReplyMarkup editMessage) {
        this.publisher.publishEvent(new SendMessageEditionEvent(editMessage));
    }

    public abstract void handle(Long userId, Long chatId, Integer messageId, String text);

}
