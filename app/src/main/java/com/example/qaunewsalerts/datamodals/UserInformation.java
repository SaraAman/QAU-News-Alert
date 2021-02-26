package com.example.qaunewsalerts.datamodals;

import android.widget.Spinner;

public class UserInformation {

    public String fullname;
    public String id;
    public String email;
    public String password;
    public String confirmpass;
    public String  department;
    public UserInformation() {

    }
    public UserInformation(String id, String fullname, String email, String password, String confirmpass,String department) {
        this.fullname = fullname;
        this.id = id;
        this.email = email;
        this.password = password;
        this.department=department;
        this.confirmpass = confirmpass;
    }
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {this.department = department;
    }
}
