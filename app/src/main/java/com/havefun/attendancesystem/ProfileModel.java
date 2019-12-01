package com.havefun.attendancesystem;

import android.graphics.Bitmap;

public class ProfileModel {

    private Bitmap bitmap;
    private String id,name,email,address,mobile,age;

    public ProfileModel(String id, String name, String email, String address, String mobile, String age, Bitmap bitmap) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.mobile = mobile;
        this.age = age;
        this.bitmap = bitmap;
    }
    public ProfileModel() {
        this.id = "";
        this.name = "";
        this.email = "";
        this.address = "";
        this.mobile = "";
        this.age = "";
        this.bitmap = null;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


}
