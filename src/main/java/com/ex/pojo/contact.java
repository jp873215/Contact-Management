package com.ex.pojo;


public class contact {
    private String name;
    private String notes;
    private String relatedPerson;
    private String homeAddress;
    private String workAddress;
    private Long phoneNumber;
    private String date;
    private String emailID;
    private String emailIDLabel;
    private String dateLabel;
    private String phoneLabel;
    private int PID;
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPID(int PID) {
    	this.PID = PID;
    }
    
    public int getPID() {
    	return PID;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRelatedPerson() {
        return relatedPerson;
    }

    public void setRelatedPerson(String relatedPerson) {
        this.relatedPerson = relatedPerson;
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

    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
        return date;
    }

    public void setPhoneLabel(String phoneLabel){
        this.phoneLabel = phoneLabel;
    }

    public String getPhoneLabel(){
        return phoneLabel;
    }

    public void setDateLabel(String dateLabel){
        this.dateLabel = dateLabel;
    }

    public String getDateLabel(){
        return dateLabel;
    }
}
