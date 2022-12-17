package com.example.backend.repositories;

import com.example.backend.entities.Chat;
import com.example.backend.entities.Member;
import com.example.backend.entities.PrimaryKey;
import com.example.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends JpaRepository<Member, PrimaryKey>
{
    @Query("select m from Member m where m.id.chatId = :chatId AND m.id.userId = :userId")
    public Member getMemberByPK(Long chatId, Long userId);

    //@Query("select m from Member m where m.chat = ")
    //public Member existsUserIntoChat(User user, Chat chat);
    @Query("select m from Member m where m.id = :id")
    public Member findByPrimaryKey(PrimaryKey id);
}



