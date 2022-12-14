package com.example.backend.controller;

import com.example.backend.dto.chat.ChatRequestDto;
import com.example.backend.dto.chat.ChatResponseDto;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import java.util.*;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private ChatService chatService;
    private MemberService memberService;
    private UserService userService;
    private CompanyService companyService;
    private MessageService messageService;
    private UserCompanyService userCompanyService;

    @Autowired
    public ChatController(ChatService chatService, MemberService memberService, UserService userService, CompanyService companyService, MessageService messageService, UserCompanyService userCompanyService) {
        this.chatService = chatService;
        this.memberService = memberService;
        this.userService = userService;
        this.companyService = companyService;
        this.messageService = messageService;
        this.userCompanyService = userCompanyService;
    }


    @GetMapping
    public ResponseEntity<List<PersonalChatResponseDto>> getMyChats() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByLogin(username);
        List<Chat> chats = chatService.getChatsByUserId(user.getId()); //?? ???????? ?????????? User ?????????? ??????????????
        List<PersonalChatResponseDto> chatsToUser = new LinkedList<>();
        for (Chat chat : chats) {
            Member member = memberService.getMemberByPK(new PrimaryKey(chat.getId(), user.getId()));
            Message lastMessage = chat.getLastMessage();
            chatsToUser.add(new PersonalChatResponseDto(chat, lastMessage, member));
        }
        return ResponseEntity.ok().body(chatsToUser);
    }

    @PostMapping
    public ResponseEntity<?> createChat(@RequestBody ChatRequestDto chatRequestDto) {
        //???????????????? ???????? ????????????????????????
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserById(jwtUser.getId());
        Company company = companyService.getCompanyById(chatRequestDto.getCompanyId());
        if (!userCompanyService.existsUserIntoCompany(user, company)) {
            if (user.getApplicationRole().equals(ApplicationRole.USER))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("?? ?????? ?????? ???????? ?????????????????? ?????? ?? ???????????? ????????????????");
        } else {
            UserCompany userCompany = userCompanyService.getUserCompanyByPK(user, company);
            if (!userCompany.isApproved())
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("?????? ???????????????? ?????????? ?? ???????????????? ???????????????????? ???????? ???????????????????????????? ??????????????????????. ????????????????????, ??????????????????...");
        }

        if (company == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("???????????????? ?? ???????????????? id = \'" + chatRequestDto.getCompanyId() + "\' ???? ??????????????");
        Chat chat = new Chat();
        chat.setTitle(chatRequestDto.getName());
        chat.setCompanyId(company);
        ChatType chatType;
        try {
            chatType = ChatType.valueOf(chatRequestDto.getType().toUpperCase(Locale.ROOT));
        } catch (RuntimeException err) {
            return ResponseEntity.badRequest().body("\'type\' ???????? ???????????? ??????????????????????.");
        }
        chat.setType(chatType);
        // TODO: 16.12.2022 ?????? ???? "description"?
        chat.setDescription(null);
        chat.setImgUrl(chatRequestDto.getImgUrl());
        chat.setPinned(chatRequestDto.isPinned());
        chat.setPrivate(chatRequestDto.getPrivate());
        chat = chatService.createNewChat(chat, user);
        Member member = memberService.getMemberByPK(new PrimaryKey(chat.getId(), user.getId()));
        return ResponseEntity.ok().body(new PersonalChatResponseDto(chat, null, member));
        //return ResponseEntity.ok().build();
    }

    @PostMapping("/{chatId}/messages")
    public ResponseEntity<?> getListOfMessagesIntoChat(@RequestBody IntervalMessagesRequest intervalMessagesRequest, @PathVariable Long chatId) {
        Chat chat = chatService.getChatById(chatId);
        if (chat == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("???? ?????????????? ?????????? ???????????? ?????? (???? ????????????????????).");

        Message message = messageService.getMessageById(intervalMessagesRequest.getStartMessageId());
        Long count = intervalMessagesRequest.getCount();
        List<Message> listMes = null;
        if (message == null) {
            listMes = messageService.getMessageByChatByCountAntIntervalDefaultOrd(chat, count);
        } else {
            listMes = messageService.getMessageByChatByCountAntIntervalDefaultOrd(chat, count, message);
        }
        List<MessageResponseDto> mesResponseList = new LinkedList<>();
        for (Message mes : listMes) {
            mesResponseList.add(new MessageResponseDto(mes));
        }
        return ResponseEntity.ok().body(mesResponseList);
    }

    // TODO: 18.12.2022 ?????????????????? ???????????????? ?? ?????????? ????????????
    @PostMapping("/{chatId}/member/{userId}")
    public ResponseEntity<?> invateNewMemberIntoChat(@PathVariable Long chatId, @PathVariable Long userId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User invitingUser = userService.findUserByLogin(username);
        Chat chat = chatService.getChatById(chatId);
        if (chat == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("?????? ?? id = \'" + chatId + "\' ???? ????????????.");
        Member member = memberService.getMemberByPK(new PrimaryKey(chatId, userId));
        System.out.println(member == null);
        if (member != null)
            ResponseEntity.status(400).body("???????????????????????? ?????? ?????????????? ?? ????????.");
        User invitedUser = userService.findUserById(userId);
        if (!chatService.userHasAdminOrOwnerRightsIntoChat(invitingUser, chat))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("?? ?????? ?????? ???????? ?????????????????? ?????????? ???????????????????? ?? ?????? ?? id = " + chatId);
        if (invitedUser == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User ?? id = \'" + userId + "\' ???? ????????????.");
        member = new Member(chat, invitedUser, false, false, false);
        memberService.addUserIntoChat(member);
        return ResponseEntity.ok().body(new MemberResponseDto(member));
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<?> getChatById(@PathVariable Long chatId)
    {
        Chat chat = chatService.getChatById(chatId);
        if(chat == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(new ChatResponseDto(chat));
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN','SUPER_ADMIN')")
    @PostMapping("/role")
    public ResponseEntity<?> getRoleIntoChat(@RequestBody Map<String,String> params)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByLogin(username);

        if(!params.containsKey("chat_id"))
            return ResponseEntity.badRequest().body("Not found \'user_id\' parametr!");
        Chat chat = chatService.getChatById(Long.valueOf(params.get("chat_id")));
        if(chat == null)
            return ResponseEntity.notFound().build();
        Member member = memberService.getMemberByPK(new PrimaryKey(chat.getId(), user.getId()));
        if(member == null)
            return ResponseEntity.status(403).body("???? ???? ???????????????? ?? ???????????? ????????!");
        String role = "user";
        if(member.getOwner())
            role = "owner";
        else if(member.getAdmin())
            role = "admin";
        return ResponseEntity.ok().body("\"role\" : " + "\"" + role);
    }
}
