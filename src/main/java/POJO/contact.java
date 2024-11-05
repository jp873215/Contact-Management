package POJO;

import java.util.ArrayList;
import java.util.List;

public class contact {
    private String name;
    private String notes;
    private String relatedPerson;
    private String homeAddress;
    private String workAddress;
    private List<Long> phoneNumber;
    private String date;
    private List<String> emailID;
    private List<String> emailIDLabel;
    private String dateLabel;
    private List<String> phoneLabel;
    private int PID;
    private List<String> categories;
    private List<Integer> cat_id;
    
    
    public contact(){
    	categories = new ArrayList<>();
    	cat_id= new ArrayList<>();
    	emailID = new ArrayList<>();
    	emailIDLabel = new ArrayList<>();
    	phoneLabel = new ArrayList<>();
    	phoneNumber = new ArrayList<>();
    	
    }
  
    public void setCategory(String cat_name) {
    	this.categories.add(cat_name);
    }
    
    public List<String> getCategory(){
    	return categories;
    }
    
    public void setCategoryID(int CID) {
    	this.cat_id.add(CID);
    }
    
    public List<Integer> getCategoryID(){
    	return cat_id;
    }

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

    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
        return date;
    }

    public void setPhoneLabel(String phoneLabel){
        this.phoneLabel.add(phoneLabel);
    }

    public List<String> getPhoneLabel(){
        return phoneLabel;
    }

    public void setDateLabel(String dateLabel){
        this.dateLabel = dateLabel;
    }

    public String getDateLabel(){
        return dateLabel;
    }
}
