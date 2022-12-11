package com.example.backend.repositories;

import com.example.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    @Query("select u from User u where u.login = :login")
    public User findUserByLogin(String login);

    @Query("select u from User u where u.id = :id")
    public User findUserById(Long id);
}
