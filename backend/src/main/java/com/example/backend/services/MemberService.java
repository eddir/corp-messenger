package com.example.backend.services;

//import com.example.backend.repositories.MemberRepository;
import com.example.backend.entities.Member;
import com.example.backend.entities.PrimaryKey;
import com.example.backend.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
public class MemberService
{

    protected MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository)
    {
        this.memberRepository = memberRepository;
    }

    public Member getMemberByPK(PrimaryKey primaryKey)
    {
        return memberRepository.getMemberByPK(primaryKey.getChatId(),primaryKey.getUserId());
    }
}
