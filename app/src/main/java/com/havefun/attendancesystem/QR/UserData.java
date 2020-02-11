package com.havefun.attendancesystem.QR;

import java.io.Serializable;

public class UserData implements Serializable {
    private String Name,ID,Email,Phone,Address,Date;
    public void setName(String name){
        Name=name;
    }
    public void setID(String id){
        ID=id;
    }
    public void setEmail(String email){
        Email=email;
    }
    public void setPhone(String phone){
        Phone=phone;
    }
    public void setAddress(String address){
        Address=address;
    }
    public void setDate(String date){
        Date=date;
    }
    public String getName(){
        return Name;
    }
    public String getID(){
        return ID;
    }
    public String getEmail(){
        return Email;
    }
    public String getPhone(){
        return Phone;
    }
    public String getAddress(){
        return Address;
    }
    public String getDate(){
        return Date;
    }
}

