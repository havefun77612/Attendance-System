package com.havefun.attendancesystem.QueryFirebase;

public class AttendanceModel {
    String StudentID;
    String StudentName;
    String StudentEmail;
    public AttendanceModel() {
    }

    public AttendanceModel(String studentID, String studentName, String studentEmail) {
        StudentID = studentID;
        StudentName = studentName;
        StudentEmail=studentEmail;

    }

    public String getStudentEmail() {
        return StudentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        StudentEmail = studentEmail;
    }


    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }
}
