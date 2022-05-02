package com.crypto.exchange.events;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;

public class SendMessageEditionEvent extends CreationEvent<EditMessageReplyMarkup> {
    public SendMessageEditionEvent(EditMessageReplyMarkup object) {
        super(object);
    }
}
