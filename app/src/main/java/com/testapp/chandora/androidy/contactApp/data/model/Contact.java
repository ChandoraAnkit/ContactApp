package com.testapp.chandora.androidy.contactApp.data.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by chandora on 24-May-2019
 */
public class Contact implements Serializable {

    private String id;

    private String name;

    private String phone;

    private String imageUri;

    private String email;

    private String address;

    private int hasPhone;

    private String firstLetter;

    private Drawable colorDrawable;

    public String getName() {
        return name;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public Drawable getColorDrawable() {
        return colorDrawable;
    }

    public void setColorDrawable(Drawable colorDrawable) {
        this.colorDrawable = colorDrawable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int isHasPhone() {
        return hasPhone;
    }

    public void setHasPhone(int hasPhone) {
        this.hasPhone = hasPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
