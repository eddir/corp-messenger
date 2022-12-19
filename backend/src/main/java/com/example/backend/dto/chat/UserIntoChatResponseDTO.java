package com.example.backend.dto.chat;

import com.example.backend.entities.Member;
import com.example.backend.entities.Profile;
import com.example.backend.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserIntoChatResponseDTO
{
    Long id;
    protected String login;
    protected String first_name;
    protected String middle_name;
    protected String last_name;

    @JsonProperty("img_url")
    protected String imgUrl;

    protected boolean isOnline;
    protected String role;

    public UserIntoChatResponseDTO(Member member) {
        User user = member.getUser();
        this.id = user.getId();
        this.login = user.getLogin();
        Profile profile = user.getProfile();
        if(profile == null)
            profile = Profile.emptyProfile;
        this.first_name = profile.getFirstName();
        this.middle_name = profile.getMiddleName();
        this.last_name = profile.getLastName();
        this.imgUrl = user.getImgUrl();
        this.isOnline = false;

        if(member.getOwner())
            role = "owner";
        else if(member.getAdmin())
            role = "admin";
        else
            role = "user";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

}
