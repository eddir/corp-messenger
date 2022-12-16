package com.example.backend.services;

import com.example.backend.repositories.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageService
{
    protected MessageRepository messageRepo;

    public MessageService(MessageRepository messageRepo)
    {
        this.messageRepo = messageRepo;
    }
}
