package com.example.backend.dto;

public class UserRequestDto
{
   protected String login;
   protected String password;
   protected String first_name;
   protected String middle_name;
   protected String last_name;


   public UserRequestDto(String login, String password, String first_name, String middle_name, String last_name)
   {
      this.login = login;
      this.password = password;
      this.first_name = first_name;
      this.middle_name = middle_name;
      this.last_name = last_name;
   }

   public String getLogin() {
      return login;
   }

   public void setLogin(String login) {
      this.login = login;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getFirst_name() {
      return first_name;
   }

   public void setFirst_name(String first_name) {
      this.first_name = first_name;
   }

   public String getMiddle_name() {
      return middle_name;
   }

   public void setMiddle_name(String middle_name) {
      this.middle_name = middle_name;
   }

   public String getLast_name() {
      return last_name;
   }

   public void setLast_name(String last_name) {
      this.last_name = last_name;
   }
}
