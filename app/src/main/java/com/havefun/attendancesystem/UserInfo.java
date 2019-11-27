package com.havefun.attendancesystem;

public class UserInfo {
        public String UserAddress, UserCompleteInfo, UserEmail, UserId, UserName, UserPassword, UserPhoneNumber, UserProfileUri,DateofBirth;

        public UserInfo() {

        }

        public String getDateofBirth() {
            return DateofBirth;
        }

        public void setDateofBirth(String dateofBirth) {
            DateofBirth = dateofBirth;
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

        public void setUserCompleteInfo(String userCompleteInfo) { UserCompleteInfo = userCompleteInfo; }

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

        public String getUserPassword() {
            return UserPassword;
        }

        public void setUserPassword(String userPassword) {
            UserPassword = userPassword;
        }

        public String getUserPhoneNumber() {
            return UserPhoneNumber;
        }

        public void setUserPhoneNumber(String userPhoneNumber) { UserPhoneNumber = userPhoneNumber; }

        public String getUserProfileUri() {
            return UserProfileUri;
        }

        public void setUserProfileUri(String userProfileUri) {
            UserProfileUri = userProfileUri;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "UserAddress='" + UserAddress + '\'' +
                    ", UserCompleteInfo='" + UserCompleteInfo + '\'' +
                    ", UserEmail='" + UserEmail + '\'' +
                    ", UserId='" + UserId + '\'' +
                    ", UserName='" + UserName + '\'' +
                    ", UserPassword='" + UserPassword + '\'' +
                    ", UserPhoneNumber='" + UserPhoneNumber + '\'' +
                    ", UserProfileUri='" + UserProfileUri + '\'' +
                    ", DateofBirth='" + DateofBirth + '\'' +
                    '}';
        }
}
