package com.havefun.attendancesystem.QueryFirebase;

public class StudentModel {
  String        Name    ,Gender   ,AcadYear   ,  ID         , Email , NationalID ,Supervisor   ,    Nationality  ,   BDate ,Phone
    ,NIDplace ,BPlace ,Religion     ,AcademicQualification,  Address,EnrollmentStatus, Department, Status,MinorDep,Activity,Enlistment
            ,MService;

    public StudentModel() {

    }

    public StudentModel(String name, String gender, String acadYear, String ID, String email, String nationalID, String supervisor, String nationality, String BDate, String phone, String NIDplace, String BPlace, String religion, String academicQualification, String address, String enrollmentStatus, String department, String status, String minorDep, String activity, String enlistment, String MService) {
        Name = name;
        Gender = gender;
        AcadYear = acadYear;
        this.ID = ID;
        Email = email;
        NationalID = nationalID;
        Supervisor = supervisor;
        Nationality = nationality;
        this.BDate = BDate;
        Phone = phone;
        this.NIDplace = NIDplace;
        this.BPlace = BPlace;
        Religion = religion;
        AcademicQualification = academicQualification;
        Address = address;
        EnrollmentStatus = enrollmentStatus;
        Department = department;
        Status = status;
        MinorDep = minorDep;
        Activity = activity;
        Enlistment = enlistment;
        this.MService = MService;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAcadYear() {
        return AcadYear;
    }

    public void setAcadYear(String acadYear) {
        AcadYear = acadYear;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNationalID() {
        return NationalID;
    }

    public void setNationalID(String nationalID) {
        NationalID = nationalID;
    }

    public String getSupervisor() {
        return Supervisor;
    }

    public void setSupervisor(String supervisor) {
        Supervisor = supervisor;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getBDate() {
        return BDate;
    }

    public void setBDate(String BDate) {
        this.BDate = BDate;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getNIDplace() {
        return NIDplace;
    }

    public void setNIDplace(String NIDplace) {
        this.NIDplace = NIDplace;
    }

    public String getBPlace() {
        return BPlace;
    }

    public void setBPlace(String BPlace) {
        this.BPlace = BPlace;
    }

    public String getReligion() {
        return Religion;
    }

    public void setReligion(String religion) {
        Religion = religion;
    }

    public String getAcademicQualification() {
        return AcademicQualification;
    }

    public void setAcademicQualification(String academicQualification) {
        AcademicQualification = academicQualification;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEnrollmentStatus() {
        return EnrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        EnrollmentStatus = enrollmentStatus;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMinorDep() {
        return MinorDep;
    }

    public void setMinorDep(String minorDep) {
        MinorDep = minorDep;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public String getEnlistment() {
        return Enlistment;
    }

    public void setEnlistment(String enlistment) {
        Enlistment = enlistment;
    }

    public String getMService() {
        return MService;
    }

    public void setMService(String MService) {
        this.MService = MService;
    }
}
