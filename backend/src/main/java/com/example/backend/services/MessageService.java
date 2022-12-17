package com.example.backend.services;

import com.example.backend.entities.Chat;
import com.example.backend.entities.Message;
import com.example.backend.repositories.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageService
{
    protected MessageRepository messageRepo;

    public MessageService(MessageRepository messageRepo)
    {
        this.messageRepo = messageRepo;
    }

    public List<Message> getMessageByChatByCountAntIntervalDefaultOrd(Chat chat, Long count,Message startMessage)
    {
        System.out.println("ТРИ ГЕРОЯ!");
        if(count > 0)
            return messageRepo.getMessageByChatByCountAntIntervalDefaultOrd(chat.getId(), count,startMessage.getId());
        System.out.println("Три БОГАТЫРЯ!");
        return messageRepo.getMessageByChatByCountAntIntervalInverseOrd(chat.getId(), Math.abs(count),startMessage.getId());
    }

    public Message getMessageById(Long id)
    {
        return messageRepo.getMessageById(id);
    }

    public Message createMessage(Message message)
    {
        return messageRepo.save(message);
    }
}
