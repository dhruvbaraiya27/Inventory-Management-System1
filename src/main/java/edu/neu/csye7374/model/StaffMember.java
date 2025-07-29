package edu.neu.csye7374.model;

public class StaffMember {
    private int staffId;

    private String staffFullName;

    private String staffUsername;

    private String staffPassword;

    private double staffSalary;

    private String staffDob;

    private double staffRating;

    private String staffDesignation;

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getStaffFullName() {
        return staffFullName;
    }

    public void setStaffFullName(String staffFullName) {
        this.staffFullName = staffFullName;
    }

    public String getStaffUsername() {
        return staffUsername;
    }

    public void setStaffUsername(String staffUsername) {
        this.staffUsername = staffUsername;
    }

    public String getStaffPassword() {
        return staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
    }

    public double getStaffSalary() {
        return staffSalary;
    }

    public void setStaffSalary(double staffSalary) {
        this.staffSalary = staffSalary;
    }

    public String getStaffDob() {
        return staffDob;
    }

    public void setStaffDob(String staffDob) {
        this.staffDob = staffDob;
    }

    public double getStaffRating() {
        return staffRating;
    }

    public void setStaffRating(double staffRating) {
        this.staffRating = staffRating;
    }

    public String getStaffDesignation() {
        return staffDesignation;
    }

    public void setStaffDesignation(String staffDesignation) {
        this.staffDesignation = staffDesignation;
    }

    @Override
    public String toString() {
        return "StaffMember{" + "staffId=" + staffId + ", staffFullName='" + staffFullName + '\'' + ", staffUsername='" + staffUsername + '\'' + ", staffPassword='" + staffPassword + '\'' + ", staffSalary=" + staffSalary + ", staffDob='" + staffDob + '\'' + ", staffRating=" + staffRating + ", staffDesignation='" + staffDesignation + '\'' + '}';
    }
}
