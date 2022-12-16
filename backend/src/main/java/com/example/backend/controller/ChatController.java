package com.example.backend.controller;

import com.example.backend.dto.chat.ChatRequestDto;
import com.example.backend.dto.chat.PersonalChatResponseDto;
import com.example.backend.dto.message.MessageResponseDto;
import com.example.backend.dto.message.interval.IntervalMessagesRequest;
import com.example.backend.entities.*;
import com.example.backend.security.jwt.user.JwtUser;
import com.example.backend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/chats")
public class ChatController
{

    private ChatService chatService;
    private MemberService memberService;
    private UserService userService;
    private CompanyService companyService;
    private MessageService messageService;
    private UserCompanyService userCompanyService;

    @Autowired
    public ChatController(ChatService chatService, MemberService memberService,UserService userService,CompanyService companyService, MessageService messageService, UserCompanyService userCompanyService) {
        this.chatService = chatService;
        this.memberService = memberService;
        this.userService = userService;
        this.companyService = companyService;
        this.messageService = messageService;
        this.userCompanyService = userCompanyService;
    }


    @GetMapping
    public ResponseEntity<List<PersonalChatResponseDto>> getMyChats()
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

    @Transactional
    @PostMapping
    public ResponseEntity<?> createChat(@RequestBody ChatRequestDto chatRequestDto)
    {
        //Проверка прав пользователя
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser)auth.getPrincipal();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserById(jwtUser.getId());
        Company company = companyService.getCompanyById(chatRequestDto.getCompanyId());
        if(!userCompanyService.existsUserIntoCompany(user,company))
        {
            if(user.getApplicationRole().equals(ApplicationRole.USER))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("У вас нет прав создавать чат в данной компании");
        }
        else
        {
            UserCompany userCompany = userCompanyService.getUserCompanyByPK(user, company);
            if(!userCompany.isApproved())
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Для создания чатов в компании необходимо быть подтвержденным сотрудником. Пожалуйста, подождите...");
        }

        if(company == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Компания с заданным id = \'" + chatRequestDto.getCompanyId() + "\' не найдена");
        Chat chat = new Chat();
        chat.setTitle(chatRequestDto.getName());
        chat.setCompanyId(company);
        ChatType chatType;
        try{
            chatType = ChatType.valueOf(chatRequestDto.getType().toUpperCase(Locale.ROOT));
        }
        catch (RuntimeException err)
        {
            return ResponseEntity.badRequest().body("\'type\' чата задано некорректно.");
        }
        chat.setType(chatType);
        // TODO: 16.12.2022 Что за "description"?
        chat.setDescription(null);
        chat.setImgUrl(chatRequestDto.getImgUrl());
        chat.setPinned(chatRequestDto.isPinned());
        chat.setPrivate(chatRequestDto.getPrivate());
        chat = chatService.createNewChat(chat, user);
        Member member = memberService.getMemberByPK(new PrimaryKey(chat.getId(), user.getId()));
        return ResponseEntity.ok().body(new PersonalChatResponseDto(chat, null, member));
        //return ResponseEntity.ok().build();
    }

    @GetMapping("/{chatId}/messages")
    public List<MessageResponseDto> getListOfMessagesIntoChat(@RequestBody IntervalMessagesRequest intervalMessagesRequest)
    {
        return null;
    }
}
