package com.example.backend.repositories;

import com.example.backend.entities.Company;
import com.example.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>
{
    //public User addUserIntoCompany();

    public Company getCompanyById(Long id);

    @Query("select c from Company c where c.name = :name")
    public Company getCompanyByName(String name);
}
