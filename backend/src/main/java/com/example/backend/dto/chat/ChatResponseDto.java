package com.example.backend.dto.chat;

import com.example.backend.dto.company.UserSimpleResponseDto;
import com.example.backend.dto.member.MemberResponseDto;
import com.example.backend.entities.Chat;
import com.example.backend.entities.Member;
import com.example.backend.entities.UserCompany;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatResponseDto
{
    protected Long id;
    protected String name;
    protected Long companyId;
    protected Boolean isPrivate;
    protected String imgUrl;
    protected String type;

    protected List<UserIntoChatResponseDTO> simpleResponseDto = new ArrayList<UserIntoChatResponseDTO>();

    public ChatResponseDto(Chat chat) {
        this.id = chat.getId();
        this.name = chat.getTitle();
        this.companyId = chat.getCompanyId().getId();
        this.isPrivate = chat.getPrivate();
        this.imgUrl = chat.getImgUrl();
        this.type = chat.getType().name();

        for(Member member : chat.getMembers())
        {
            simpleResponseDto.add(new UserIntoChatResponseDTO(member));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("company_id")
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @JsonProperty("is_private")
    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    @JsonProperty("img_url")
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("employees")
    public List<UserIntoChatResponseDTO> getSimpleResponseDto() {
        return simpleResponseDto;
    }

    public void setSimpleResponseDto(List<UserIntoChatResponseDTO> simpleResponseDto) {
        this.simpleResponseDto = simpleResponseDto;
    }
}
