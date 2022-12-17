package com.example.backend.repositories;

import com.example.backend.entities.Chat;
import com.example.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long>
{
    @Query("select m.chat from Member m where m.user.id = :userId")
    public List<Chat> getChatByUser(Long userId);

    public Chat getChatById(Long id);
}
