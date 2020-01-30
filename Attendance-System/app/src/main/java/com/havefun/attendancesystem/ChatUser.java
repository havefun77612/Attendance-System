package com.havefun.attendancesystem;

public class ChatUser {
    private String  UserEmail, UserId, UserName , UserProfileUri;

    public ChatUser() {
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
