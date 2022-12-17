package com.example.backend.controller;

import com.example.backend.dto.company.CompanyRequestDto;
import com.example.backend.dto.company.CompanyResponseDto;
import com.example.backend.entities.Company;
import com.example.backend.entities.User;
import com.example.backend.services.CompanyService;
import com.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/companies")
public class CompanyController
{
    CompanyService companyService;
    UserService userService;

    @Autowired
    public CompanyController(CompanyService companyService, UserService userService)
    {
        this.companyService = companyService;
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> createCompany(@RequestBody CompanyRequestDto companyRequestDto)
    {
        Company company = new Company();
        company.setName(companyRequestDto.getName());
        try{
            User user = userService.findUserById(companyRequestDto.getOwner_id());
            if(user == null)
                throw new EntityNotFoundException();
            company.setUserOwner(user);
            companyService.save(company);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CompanyResponseDto(company));
        }
        catch (EntityExistsException err)
        {
            return ResponseEntity.status(409).body("Компания с таким названием уже существует!");
        }
        catch (EntityNotFoundException err)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с заданным id не найден!");

        }
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable("id") Long id)
    {
        Company company = companyService.getCompanyById(id);
        if(company == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(new CompanyResponseDto(company));
    }
}
