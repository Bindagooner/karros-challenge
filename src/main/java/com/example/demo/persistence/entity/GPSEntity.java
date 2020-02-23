package com.example.demo.persistence.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "gps")
@EntityListeners(AuditingEntityListener.class)
public class GPSEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "gpx_data")
    @Lob
    private byte[] gpxData;

    @Column(name = "user")
    @Basic
    private String user;

    @Basic
    @Column(name = "created_date")
    @CreationTimestamp
    private Date createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getGpxData() {
        return gpxData;
    }

    public void setGpxData(byte[] gpxData) {
        this.gpxData = gpxData;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUser() { return user; }

    public void setUser(String user) { this.user = user; }
}
