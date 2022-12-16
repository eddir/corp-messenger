package com.example.backend.services;

import com.example.backend.entities.Chat;
import com.example.backend.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class ChatService
{

    protected ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository)
    {
        this.chatRepository = chatRepository;
    }

    public List<Chat> getAllChats()
    {
        return chatRepository.findAll();
    }

    public Chat createNewChat(Chat chat)
    {
        return chatRepository.save(chat);
    }

    public List<Chat> getChatsByUserId(Long userId)
    {
        List<Chat> result = this.chatRepository.getChatByUser(userId);
        if(result == null)
            return new LinkedList<>();
        return new ArrayList<>(result);
    }
}
