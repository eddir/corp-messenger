package com.example.backend.repositories;

import com.example.backend.entities.Member;
import com.example.backend.entities.PrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends JpaRepository<Member, PrimaryKey>
{
}



