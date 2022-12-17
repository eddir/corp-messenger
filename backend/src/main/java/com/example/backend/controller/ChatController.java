package com.example.backend.controller;

import com.example.backend.dto.chat.ChatRequestDto;
import com.example.backend.dto.chat.PersonalChatResponseDto;
import com.example.backend.dto.member.MemberResponseDto;
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

import javax.persistence.PersistenceException;
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
    public ResponseEntity<?> getListOfMessagesIntoChat(@RequestBody IntervalMessagesRequest intervalMessagesRequest, @PathVariable Long chatId)
    {
        Chat chat = chatService.getChatById(chatId);
        if(chat == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Не удалось найти данный чат (не существует).");
        Message message = messageService.getMessageById(intervalMessagesRequest.getStartMessageId());
        if(message == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Не удалось найти стартовое сообщение (не существует).");
        Long count = intervalMessagesRequest.getCount();
        List<Message> listMes = messageService.getMessageByChatByCountAntIntervalDefaultOrd(chat, count, message);
        List<MessageResponseDto> mesResponseList = new LinkedList<>();
        for(Message mes : listMes)
        {
            mesResponseList.add(new MessageResponseDto(mes));
        }
        return ResponseEntity.ok().body(mesResponseList);
    }

    @PostMapping("/{chatId}/member/{userId}")
    public ResponseEntity<?> invateNewMemberIntoChat(@PathVariable Long chatId, @PathVariable Long userId)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User invitingUser = userService.findUserByLogin(username);
        Chat chat = chatService.getChatById(chatId);
        if(chat == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Чат с id = \'" + chatId + "\' не найден.");
        Member member = memberService.getMemberByPK(new PrimaryKey(chatId,userId));
        if(member != null)
            ResponseEntity.status(400).body("Пользователь уже состоит в чате.");
        User invitedUser = userService.findUserById(userId);
        if(!chatService.userHasAdminOrOwnerRightsIntoChat(invitingUser,chat))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("У вас нет прав добавлять новых участников в чат с id = " + chatId);
        if(invitedUser == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User с id = \'" + userId + "\' не найден.");
        member = new Member(chat, invitedUser,false,false,false);
        memberService.addUserIntoChat(member);
        return ResponseEntity.ok().body(new MemberResponseDto(member));
    }
}
