package com.example.backend.dto.message.interval;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IntervalMessagesRequest
{
    protected Long chatId;

    protected Long count;

    @JsonProperty("start_message_id")
    protected Long startMessageId;

    public IntervalMessagesRequest(){}

    public IntervalMessagesRequest(Long chatId, Long count, Long startMessageId) {
        this.chatId = chatId;
        this.count = count;
        this.startMessageId = startMessageId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getStartMessageId() {
        return startMessageId;
    }

    public void setStartMessageId(Long startMessageId) {
        this.startMessageId = startMessageId;
    }
}
