package com.example.backend.services;

import com.example.backend.entities.Chat;
import com.example.backend.entities.Member;
import com.example.backend.entities.User;
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
    protected MemberService memberService;

    @Autowired
    public ChatService(ChatRepository chatRepository, MemberService memberService)
    {
        this.chatRepository = chatRepository;
        this.memberService = memberService;
    }

    public List<Chat> getAllChats()
    {
        return chatRepository.findAll();
    }

    //member - как OUT в c#
    public Chat createNewChat(Chat chat, User creator)
    {
        chatRepository.save(chat);
        Member member = new Member(chat,creator,true,false,false);
        memberService.addUserIntoChat(member);
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
