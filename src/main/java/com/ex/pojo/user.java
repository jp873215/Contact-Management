package com.ex.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class user {
    private String name;
    private String gender;
    private String homeAddress;
    private String workAddress;
    private List<Long> phoneNumber;//for 
    private List<String> emailID;
    private List<String> emailIDLabel;
    private List<String> phoneNumberLabel;
    private int UID;
    private String password;
    private List<String> category_name;
    private List<Integer> category_id;

    
    public user() {
        phoneNumber = new ArrayList<>();
        emailID = new ArrayList<>();
        emailIDLabel = new ArrayList<>();
        phoneNumberLabel = new ArrayList<>();
        category_name = new ArrayList<>();
        category_id = new ArrayList<>();
    }
/*
 * I want to store list of emails, category, phon
 */
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
//    
//    public void setstarred(int starred) {
//    	this.starred = starred;
//    }
//    
//    public int getstarred() {
//    	return starred;
//    }
    
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
        this.phoneNumber.add(phoneNumber);
    }
    public List<Long> getPhoneNumber(){
        return phoneNumber;
    }

    public void setEmailID(String emailID){
        this.emailID.add(emailID);
    }

    public List<String> getEmailID(){
        return emailID;
    }

    public void setEmailIDLabel(String emailIDLabel){
        this.emailIDLabel.add(emailIDLabel);
    }

    public List<String> getEmailIDLabel(){
        return emailIDLabel;
    }

//    public void setDate(String date){
//        this.date = date;
//    }
//    public String getDate(){
//        return date;
//    }

    public void setPhoneLabel(String phoneNumberLabel){
        this.phoneNumberLabel.add(phoneNumberLabel);
    }

    public List<String> getPhoneLabel(){
        return phoneNumberLabel;
    }
//    
    public void setCategory(String category_name) {
    	this.category_name.add(category_name);
    }
    
    public List<String>  getCategory() {
    	return category_name;
    }
    
    public void setCategoryID(Integer category_id) {
    	this.category_id.add(category_id);
    }
    
    public List<Integer>getCategoryID() {
    	return category_id;
    }
    


}

