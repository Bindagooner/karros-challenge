package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created on 2/21/20
 *
 * @author manhvan.nguyen
 */
public class ItemViewResponseDTO {

    private Long id;
    private String user;
    private String name;
    private String description;

    @JsonProperty("uploaded_date")
    private Date uploadedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(Date uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

}
