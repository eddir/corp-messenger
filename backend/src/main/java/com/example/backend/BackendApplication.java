package com.example.backend;

import com.example.backend.entities.*;
import com.example.backend.services.*;
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
    CommandLineRunner run(UserService userService, CompanyService companyService, UserCompanyService userCompanyService, ChatService chatService, MemberService memberService)
    {
        User anton = new User("anton", "1", ApplicationRole.USER);
        User mikhail = new User("mikhail", "1", ApplicationRole.ADMIN);
        return (args) -> {
            if(userService.findUserByLogin("anton") == null)
                userService.save(anton);
            if(userService.findUserByLogin("mikhail") == null)
                userService.save(mikhail);
            User Ed = new User("eduard", "1", ApplicationRole.SUPER_ADMIN);
            if(userService.findUserByLogin("eduard") == null)
                userService.save(Ed);
            if(companyService.getCompanyByName("Microsoft") == null)
            {

                if (userService.findUserByLogin("eduard") != null)
                {
                    Company company = new Company();
                    company.setName("Microsoft");
                    company.setUserOwner(Ed);
                    companyService.save(company);
                    userCompanyService.addUserToCompany(anton,company,false);
                    userCompanyService.addUserToCompany(mikhail, company, true);



                    Chat chat = new Chat();
                    chat.setCompanyId(company);
                    chat.setTitle("Тест1");
                    chatService.createNewChat(chat);
                    Member member = new Member(chat,Ed,true,false,true);
                    memberService.addUserIntoChat(member);

                }


            }


        };
    }

    @Bean
    public BCryptPasswordEncoder customBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


}
