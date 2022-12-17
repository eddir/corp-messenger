package com.example.backend.services;

import com.example.backend.entities.Chat;
import com.example.backend.entities.Member;
import com.example.backend.entities.PrimaryKey;
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

    public Chat createNewChat(Chat chat, User creator)
    {
        chatRepository.save(chat);
        Member member = new Member(chat,creator,true,false,false);
        memberService.addUserIntoChat(member);
        return chat;
    }

    public List<Chat> getChatsByUserId(Long userId)
    {
        List<Chat> result = this.chatRepository.getChatByUser(userId);
        if(result == null)
            return new LinkedList<>();
        return new ArrayList<>(result);
    }

    public Chat getChatById(Long id)
    {
        return chatRepository.getChatById(id);
    }

    public boolean userHasAdminRightIntoChat(User user, Chat chat)
    {
        if(memberService.getMemberByPK(new PrimaryKey(chat.getId(), user.getId())).getAdmin())
            return true;
        return false;
    }

    public boolean userHasOwnerRightIntoChat(User user, Chat chat)
    {
        if(memberService.getMemberByPK(new PrimaryKey(chat.getId(), user.getId())).getOwner())
            return true;
        return false;
    }

    public boolean userHasAdminOrOwnerRightsIntoChat(User user, Chat chat)
    {
        Member member = memberService.getMemberByPK(new PrimaryKey(chat.getId(), user.getId()));
        if(member == null)
            return false;
        if(member.getAdmin() || member.getOwner())
            return true;
        return false;
    }
}
