package com.example.backend.repositories;

import com.example.backend.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long>
{
    @Query("select c from Chat c where c.id = :userId")
    public List<Chat> getChatByUser(Long userId);
}
