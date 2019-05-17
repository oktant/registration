package com.rectasolutions.moving.registration.entities;

import org.hibernate.annotations.Check;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Dell on 26-Jul-18.
 */
@Entity
@Table(name = "rectauser")
public class UserDB {
    @Id
    @Column(name="id")
    private String id;


    @Column(name = "photo_path")
    private String photoPath;
    @NotNull
    private double longitude;
    @NotNull
    private double latitude;

    @NotEmpty
    @Check(constraints = "status in ('online','busy','offline')")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserDB{" +
                "id='" + id + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", status='" + status + '\'' +
                '}';
    }
}
