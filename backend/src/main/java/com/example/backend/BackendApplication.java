package com.example.backend;

import com.example.backend.entities.ApplicationRole;
import com.example.backend.entities.Company;
import com.example.backend.entities.User;
import com.example.backend.services.CompanyService;
import com.example.backend.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService, CompanyService companyService)
    {
        return (args) -> {
            if(userService.findUserByLogin("anton") == null)
                userService.save(new User("anton", "1", ApplicationRole.ADMIN));
            if(userService.findUserByLogin("mihail") == null)
                userService.save(new User("mihail", "1", ApplicationRole.SUPER_ADMIN));
            User Ed = new User("eduard", "1", ApplicationRole.SUPER_ADMIN);
            if(userService.findUserByLogin("eduard") == null)
                userService.save(Ed);
            companyService.save(new Company("Microsoft", Ed));
        };
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
