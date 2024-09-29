package com.ex.pojo;

public class user {
    private String name;
    private String gender;
//    private String relatedPerson;
    private String homeAddress;
    private String workAddress;
    private Long phoneNumber;
    //private String date;
    private String emailID;
    private String emailIDLabel;
    private int starred;
    //private String dateLabel;
    private String phoneLabel;
    private int UID;
    private String password;
    private String salt;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setUID(int UID) {
    	this.UID = UID;
    }
    
    public int getUID() {
    	return UID;
    }
    
    public void setstarred(int starred) {
    	this.starred = starred;
    }
    
    public int getstarred() {
    	return starred;
    }
    
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getRelatedPerson() {
//        return relatedPerson;
//    }
//
//    public void setRelatedPerson(String relatedPerson) {
//        this.relatedPerson = relatedPerson;
//    }
    
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public void setPhoneNumber(Long phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public Long getPhoneNumber(){
        return phoneNumber;
    }

    public void setEmailID(String emailID){
        this.emailID = emailID;
    }

    public String getEmailID(){
        return emailID;
    }

    public void setEmailIDLabel(String emailIDLabel){
        this.emailIDLabel = emailIDLabel;
    }

    public String getEmailIDLabel(){
        return emailIDLabel;
    }

//    public void setDate(String date){
//        this.date = date;
//    }
//    public String getDate(){
//        return date;
//    }

    public void setPhoneLabel(String phoneLabel){
        this.phoneLabel = phoneLabel;
    }

    public String getPhoneLabel(){
        return phoneLabel;
    }

//    public void setDateLabel(String dateLabel){
//        this.dateLabel = dateLabel;
//    }
//
//    public String getDateLabel(){
//        return dateLabel;
//    }
}

