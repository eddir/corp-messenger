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
    @Query(value = "select * from message m join chat c on m.chat_id = c.id WHERE c.id = :chatId AND m.id < :startMessage_id ORDER BY m.id LIMIT :count", nativeQuery = true)
    public List<Message> getMessageByChatByCountAntIntervalDefaultOrd(Long chatId, Long count,Long startMessage_id);

    @Query(value = "select * from message m join chat c on m.chat_id = c.id WHERE c.id = :chatId AND m.id > :startMessage_id ORDER BY m.id LIMIT :count", nativeQuery = true)
    public List<Message> getMessageByChatByCountAntIntervalInverseOrd(Long chatId, Long count,Long startMessage_id);

    public Message getMessageById(Long id);
}
