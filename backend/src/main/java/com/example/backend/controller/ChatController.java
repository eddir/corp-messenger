package com.example.backend.controller;

import com.example.backend.dto.chat.PersonalChatResponseDto;
import com.example.backend.entities.*;
import com.example.backend.security.jwt.user.JwtUser;
import com.example.backend.services.ChatService;
import com.example.backend.services.MemberService;
import com.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController
{

    private ChatService chatService;
    private MemberService memberService;
    private UserService userService;

    @Autowired
    public ChatController(ChatService chatService, MemberService memberService,UserService userService) {
        this.chatService = chatService;
        this.memberService = memberService;
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<?> getMyChats()
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByLogin(username);
        List<Chat> chats = chatService.getChatsByUserId(user.getId()); //В этих чатах User ТОЧНО состоит
        List<PersonalChatResponseDto> chatsToUser = new LinkedList<>();
        for(Chat chat : chats)
        {
            Member member = memberService.getMemberByPK(new PrimaryKey(chat.getId(),user.getId()));
            Message lastMessage = chat.getLastMessage();
            chatsToUser.add(new PersonalChatResponseDto(chat,lastMessage, member));
        }
        return ResponseEntity.ok().body(chatsToUser);
    }
}
