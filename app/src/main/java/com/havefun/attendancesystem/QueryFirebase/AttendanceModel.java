package com.havefun.attendancesystem.QueryFirebase;

public class AttendanceModel {
    String StudentID,StudentName;

    public AttendanceModel() {
    }

    public AttendanceModel(String studentID, String studentName) {
        StudentID = studentID;
        StudentName = studentName;
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
