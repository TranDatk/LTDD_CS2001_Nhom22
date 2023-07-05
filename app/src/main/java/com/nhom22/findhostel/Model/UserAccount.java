package com.nhom22.findhostel.Model;


public class UserAccount {
    private int id;
    private String username; // name of user, not have to login
    private String email;
    private String password;
    private String phone;
    private double digital_money;
    private int roleUser;
    private byte[] image;
    private int isActive;
    private Address address;

    public void setId(int id) {
        this.id = id;
    }

    public UserAccount(String username, byte[] image) {
        this.username = username;
        this.image = image;
    }

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDigital_money(double digital_money) {
        this.digital_money = digital_money;
    }

    public void setRoleUser(int roleUser) {
        this.roleUser = roleUser;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public double getDigital_money() {
        return digital_money;
    }

    public int getRoleUser() {
        return roleUser;
    }

    public byte[] getImage() {
        return image;
    }

    public int getIsActive() {
        return isActive;
    }

    public Address getAddress() {
        return address;
    }

    public UserAccount(int id, String username, String email, String password, String phone, double digital_money, int roleUser, byte[] image, int isActive, Address address) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.digital_money = digital_money;
        this.roleUser = roleUser;
        this.image = image;
        this.isActive = isActive;
        this.address = address;
    }
}
