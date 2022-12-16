package com.example.backend.repositories;

import com.example.backend.entities.Company;
import com.example.backend.entities.User;
import com.example.backend.entities.UserCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCompanyRepository extends JpaRepository<UserCompany, UserCompany.Id>
{
    @Query("select uc from UserCompany uc where uc.user = :user AND uc.company = :company")
    public User existsUserIntoCompany(User user, Company company);
}
