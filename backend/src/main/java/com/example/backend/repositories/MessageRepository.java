package com.example.backend.repositories;

import com.example.backend.entities.Chat;
import com.example.backend.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>
{
    //@Query("select m from Message m where m.chat = :chat order by m.id")
    //public List<Message> getMessageByChatByCountAntIntervalDefaultOrd(Chat chat, Long count,Message startMessage);
}
