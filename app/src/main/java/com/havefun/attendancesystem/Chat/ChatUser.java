package com.havefun.attendancesystem.Chat;

public class ChatUser {
    private String DateOfBith, UserAddress, UserEmail, UserId, UserName, UserPassword, UserPhoneNumber, UserProfileUri, UserType;

    public ChatUser() {
    }

    public ChatUser(String dateOfBith, String userAddress, String userEmail, String userId, String userName, String userPassword, String userPhoneNumber, String userProfileUri, String userType) {

        DateOfBith = dateOfBith;
        UserAddress = userAddress;
        UserEmail = userEmail;
        UserId = userId;
        UserName = userName;
        UserPassword = userPassword;
        UserPhoneNumber = userPhoneNumber;
        UserProfileUri = userProfileUri;
        UserType = userType;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getDateOfBith() {
        return DateOfBith;
    }

    public void setDateOfBith(String dateOfBith) {
        DateOfBith = dateOfBith;
    }

    public String getUserAddress() {
        return UserAddress;
    }

    public void setUserAddress(String userAddress) {
        UserAddress = userAddress;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getUserPhoneNumber() {
        return UserPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        UserPhoneNumber = userPhoneNumber;
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

    public String getUserProfileUri() {
        return UserProfileUri;
    }

    public void setUserProfileUri(String userProfileUri) {
        UserProfileUri = userProfileUri;
    }
}
