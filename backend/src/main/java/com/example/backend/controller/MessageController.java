package com.example.backend.controller;

import com.example.backend.dto.message.MessageRequestDto;
import com.example.backend.dto.message.MessageResponseDto;
import com.example.backend.entities.*;
import com.example.backend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chats")
public class MessageController
{
    //public ResponseEntity<?> getAllMessageInChat
    protected MessageService messageService;
    protected UserService userService;
    protected MemberService memberService;
    protected ChatService chatService;
    protected UserCompanyService userCompanyService;
    protected CompanyService companyService;

    @Autowired
    public MessageController(MessageService messageService, UserService userService, MemberService memberService, ChatService chatService, UserCompanyService userCompanyService,CompanyService companyService) {
        this.messageService = messageService;
        this.userService = userService;
        this.memberService = memberService;
        this.chatService = chatService;
        this.userCompanyService = userCompanyService;
        this.companyService = companyService;
    }

    @PostMapping("/{chatId}/messages")
    public ResponseEntity<?> createMessage(@RequestBody MessageRequestDto messageRequestDto)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userSender = userService.findUserByLogin(username);
        Chat chat = chatService.getChatById(messageRequestDto.getChatId());
        if(chat == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Данного чата не существует");
        UserCompany userCompany = userCompanyService.getUserCompanyByPK(userSender,chat.getCompanyId());
        if(!userCompany.isApproved())
            ResponseEntity.status(HttpStatus.FORBIDDEN).body("Вы являетесь неподтвержеденным сотрудником компании. Отправка сообщений запрещена. Ждите ответа от HR-сотрудника компании");

        if(chat.getType().equals(ChatType.CHANNEL))
        {
            //если User не является админом / владельцем канала - доступ к публикации сообщений запрещен
            if(!chatService.userHasAdminOrOwnerRightsIntoChat(userSender,chat))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("У вас нет прав публикации для данного канала.");
        }
        //Есть ли отправитель в данном чате
        if(memberService.getMemberByPK(new PrimaryKey(messageRequestDto.getChatId(),userSender.getId())) == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Вы не имеете доступ к данному чату!");
        Message message = new Message();
        message.setChat(chat);
        message.setDeleted(false);
        message.setPinned(false);
        message.setText(messageRequestDto.getText());
        message.setSender(userSender);
        messageService.createMessage(message);

        chat.getMembers().forEach(member -> {
            if(!member.getUser().equals(userSender))
            {
                pusher.trigger("messages-" + member.id, "message_added", Collections.singletonMap("message", "Hello World"));
            }
        });

        return ResponseEntity.ok().body(new MessageResponseDto(message));
    }
}
