package com.zjy.pojo;

public class User {
    private String number;
    private String password;
    private String dorm;

    public User() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDorm() {
        return dorm;
    }

    public void setDorm(String dorm) {
        this.dorm = dorm;
    }

    @Override
    public String toString() {
        return "User{" +
                ", number='" + number + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
