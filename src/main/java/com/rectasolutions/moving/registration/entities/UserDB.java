package com.rectasolutions.moving.registration.entities;

import org.hibernate.annotations.Check;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
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

    @NotBlank
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

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public double getLongitude() {
        return longitude;
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
