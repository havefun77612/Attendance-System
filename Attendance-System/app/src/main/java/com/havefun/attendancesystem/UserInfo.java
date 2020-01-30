package com.havefun.attendancesystem;

import android.graphics.Bitmap;

public class UserInfo {
        private String UserAddress, UserCompleteInfo, UserEmail, UserId, UserName, UserPhoneNumber, UserProfileUri,DateOfBirth;
        private Bitmap bitmap;
        public UserInfo() {

        }
    public UserInfo( Bitmap bitmap) {
        this.UserId = "";
        this.UserName = "";
        this.UserEmail = "";
        this.UserAddress = "";
        this.UserPhoneNumber = "";
        this.DateOfBirth = "";
        this.bitmap = bitmap;
    }
    public String getUserAddress() {
        return UserAddress;
    }

    public void setUserAddress(String userAddress) {
        UserAddress = userAddress;
    }

    public String getUserCompleteInfo() {
        return UserCompleteInfo;
    }

    public void setUserCompleteInfo(Boolean userCompleteInfo) {
        UserCompleteInfo = String.valueOf(userCompleteInfo);
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPhoneNumber() {
        return UserPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        UserPhoneNumber = userPhoneNumber;
    }

    public String getUserProfileUri() {
        return UserProfileUri;
    }

    public void setUserProfileUri(String userProfileUri) {
        UserProfileUri = userProfileUri;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateofBirth) {
        DateOfBirth = dateofBirth;
    }

    public Bitmap getBitmap() { return bitmap; }

    public void setBitmap(Bitmap bitmap) { this.bitmap = bitmap; }
    @Override
        public String toString() {
            return "UserInfo{" +
                    "UserAddress='" + UserAddress + '\'' +
                    ", UserCompleteInfo='" + UserCompleteInfo + '\'' +
                    ", UserEmail='" + UserEmail + '\'' +
                    ", UserId='" + UserId + '\'' +
                    ", UserName='" + UserName + '\'' +
                    ", UserPhoneNumber='" + UserPhoneNumber + '\'' +
                    ", UserProfileUri='" + UserProfileUri + '\'' +
                    ", DateofBirth='" + DateOfBirth + '\'' +
                    '}';
        }
}
