package com.example.backend.services;

//import com.example.backend.repositories.MemberRepository;
import com.example.backend.entities.Chat;
import com.example.backend.entities.Member;
import com.example.backend.entities.PrimaryKey;
import com.example.backend.entities.User;
import com.example.backend.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;

@Service
public class MemberService
{

    protected MemberRepository memberRepository;
    protected UserCompanyService userCompanyService;

    @Autowired
    public MemberService(MemberRepository memberRepository, UserCompanyService userCompanyService)
    {
        this.memberRepository = memberRepository;
        this.userCompanyService = userCompanyService;
    }

    public Member getMemberByPK(PrimaryKey primaryKey)
    {
        return memberRepository.getMemberByPK(primaryKey.getChatId(),primaryKey.getUserId());
    }

    public Member addUserIntoChat(Member member) throws PersistenceException
    {
        //Проверка, состоит ли юзер в компании, к которой относится чат
        //if(!userCompanyService.existsUserIntoCompany(member.getUser(), member.getChat().getCompanyId()))
           // throw new PersistenceException("Пользователь не состоит в компании чата!");
        return memberRepository.save(member);
    }
}
