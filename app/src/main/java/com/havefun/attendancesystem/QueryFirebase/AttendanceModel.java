package com.havefun.attendancesystem.QueryFirebase;

public class AttendanceModel {
    private String StudentEmail,StudentID,StudentName;



    public AttendanceModel(String studentID, String studentName, String studentEmail) {
        StudentID = studentID;
        StudentName = studentName;
        StudentEmail = studentEmail;
    }
    public AttendanceModel() {
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

    public String getStudentEmail() {
        return StudentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        StudentEmail = studentEmail;
    }
}
