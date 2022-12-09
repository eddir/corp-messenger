package com.example.backend.repositories;

import com.example.backend.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberReposritry extends JpaRepository<Member, Member.PrimaryKey>
{
}
