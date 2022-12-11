package com.example.backend.repositories;

import com.example.backend.entities.UserCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCompanyRepository extends JpaRepository<UserCompany, UserCompany.Id>
{
}
