package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.jenetics.jpx.GPX;

import java.util.Date;

/**
 * Created on 2/22/20
 *
 * @author manhvan.nguyen
 */
public class DetailViewDTO {

    private Long id;
    private String user;

    @JsonProperty("uploaded_date")
    private Date uploadedDate;

    private GPX gpx;

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

    public Date getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(Date uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public GPX getGpx() {
        return gpx;
    }

    public void setGpx(GPX gpx) {
        this.gpx = gpx;
    }
}
