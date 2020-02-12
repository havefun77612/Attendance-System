package com.havefun.attendancesystem;

public class DoctorInfo {
    public String getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(String doctorId) {
        DoctorId = doctorId;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public DoctorInfo(String doctorId, String doctorName, String courseName) {
        DoctorId = doctorId;
        DoctorName = doctorName;
        CourseName = courseName;
    }

    public DoctorInfo() {
        DoctorId = null;
        DoctorName = null;
        CourseName = null;
    }
    private String DoctorId, DoctorName,CourseName;
}
