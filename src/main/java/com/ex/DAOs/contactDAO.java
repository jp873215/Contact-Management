package com.ex.DAOs;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ex.pojo.contact;


public class contactDAO {
    private final String URL = "jdbc:mysql://localhost:3306/users";
    private final String root = "root";
    private final String pass = "Prakashrmkec@873215$";
    
    
    public contact addContact(contact ct, int UID) throws ClassNotFoundException, SQLException{
        int PID = -1;
        Connection conn = getConnection();
        contact obj = new contact();
        String contact_query = "INSERT INTO contact_detail(UID, Name, Note, Related_Person, Home_Address, Work_Address) values (?, ?, ?, ?, ?, ?);";
        String email_query = "INSERT INTO ct_email_id(PID, Email_ID, Label) values (?, ?, ?);";
        String phone_query = "INSERT INTO ct_phone(PID, Phone_No, Label) values (?, ?, ?);";
        String date_query = "INSERT INTO ct_date(PID, Date, Label) values (?, ?, ?);";
        try (PreparedStatement pst1 = conn.prepareStatement(contact_query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst1.setInt(1, UID);
            pst1.setString(2, ct.getName());
            pst1.setString(3, ct.getNotes());
            pst1.setString(4, ct.getRelatedPerson());
            pst1.setString(5, ct.getHomeAddress());
            pst1.setString(6, ct.getWorkAddress());
            int res1 = pst1.executeUpdate();

            if (res1 != 0){
            	System.out.println("res1");
                try (ResultSet val = pst1.getGeneratedKeys()) {
                    if (val.next()){
                        PID = val.getInt(1);
                        obj.setPID(PID);
                    }
                }
                try (PreparedStatement pst2 = conn.prepareStatement(email_query)) {
                    pst2.setInt(1, PID);
                    pst2.setString(2, ct.getEmailID());
                    pst2.setString(3, ct.getEmailIDLabel());
                    
                    int res2 = pst2.executeUpdate();

                    if (res2 != 0){
                    	System.out.println("res2");
                        try (PreparedStatement pst3 = conn.prepareStatement(phone_query)) {
                            pst3.setInt(1, PID);
                            pst3.setLong(2, ct.getPhoneNumber());
                            pst3.setString(3, ct.getPhoneLabel());
                            
                            int res3 = pst3.executeUpdate();
                            if (res3 != 0){
                            	System.out.println("res3");
                                try (PreparedStatement pst4 = conn.prepareStatement(date_query)) {
                                    pst4.setInt(1, PID);
                                    pst4.setString(2, ct.getDate());
                                    pst4.setString(3, ct.getDateLabel());
                                    
                                    int res4 = pst4.executeUpdate();
                                    if (res4 != 0){
                                    	System.out.println("res4");
                                        return obj;
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                	System.out.println("Error1:" + e.getMessage());
                }

            }
            
        } catch (Exception e) {
        	System.out.println("Error2:" + e.getMessage());
        }
        return null;
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException{
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	Connection conn = DriverManager.getConnection(URL, root, pass);
    	return conn;
            
    }
    public int addEmail(contact ct, int UID){
        try (Connection conn = getConnection();) {
            String query = "INSERT INTO contact_detail(UID, Name, Note, Related_Person, Home_Address, Work_Address) values (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pst1 = conn.prepareStatement(query)) {
                pst1.setInt(1, UID);
                pst1.setString(2, ct.getName());
                pst1.setString(3, ct.getNotes());
                pst1.setString(4, ct.getRelatedPerson());
                pst1.setString(5, ct.getHomeAddress());
                pst1.setString(6, ct.getWorkAddress());

                int res1 = pst1.executeUpdate();
                if (res1 != 0){
                    return res1;
                }
                
            } catch (Exception e) {
            	System.out.println("Error3:" + e.getMessage());
            }
            
        } catch (Exception e) {
        	System.out.println("Error4:" + e.getMessage());
        }
        return 0;
    }
    public List<contact> getAllContacts(int UID){
    	List<contact> li = new ArrayList<>();
    	try(Connection conn = getConnection();){
    		String query = "SELECT cd.PID, cd.Name, cd.Note, cd.Related_Person, " +
                    "cd.Home_Address, cd.Work_Address, " +
                    "cp.Phone_No, cp.Label AS PhoneLabel, " +
                    "ce.Email_ID, ce.Label AS EmailLabel, " +
                    "cd2.Date, cd2.Label AS DateLabel " + 
                    "FROM contact_detail cd " +
                    "LEFT JOIN ct_phone cp ON cd.PID = cp.PID " +
                    "LEFT JOIN ct_email_id ce ON cd.PID = ce.PID " +
                    "LEFT JOIN ct_date cd2 ON cd.PID = cd2.PID " +
                    "WHERE cd.UID = ?";
    		try(PreparedStatement pst1 = conn.prepareStatement(query)){
    			pst1.setInt(1, UID);
    			ResultSet rs = pst1.executeQuery();
    			while (rs.next()) {
    				contact ct = new contact();
    				ct.setPID(rs.getInt("PID"));
    				ct.setName(rs.getString("Name"));
    				ct.setNotes(rs.getString("Note"));
    				ct.setRelatedPerson(rs.getString("Related_Person")); 
    				ct.setHomeAddress(rs.getString("Home_Address")); 
    				ct.setWorkAddress(rs.getString("Work_Address")); 

    				String phoneNo = rs.getString("Phone_No"); 
    				if (phoneNo != null) {
    				    ct.setPhoneNumber(Long.parseLong(phoneNo)); 
    				} else {
    				    ct.setPhoneNumber(null); 
    				}

    				ct.setPhoneLabel(rs.getString("PhoneLabel")); 
    				ct.setEmailID(rs.getString("Email_ID")); 
    				ct.setEmailIDLabel(rs.getString("EmailLabel")); 
    				ct.setDate(rs.getString("Date")); 
    				ct.setDateLabel(rs.getString("DateLabel")); 

    				li.add(ct);
    			}
    			return li;
    		}
    		catch(Exception e) {
    			System.out.println("Error1:" + e.getMessage());
    		}
    		
    	}
    	catch(Exception e) {
    		System.out.println("Error2:" + e.getMessage());
    	}
    	return null;	
    }
    
    public boolean deletecontact(int PID) throws ClassNotFoundException, SQLException {
    	try(Connection conn = getConnection()){
    		String deletequery = "delete from contact_detail where PID = ?;";
    		String deletePhoneQuery = "DELETE FROM ct_phone WHERE PID = ?;";
    		String deleteEmailQuery = "DELETE FROM ct_email_id WHERE PID = ?;";
    		String deleteDateQuery = "DELETE FROM ct_date WHERE PID = ?;";
    		String fr1 = "SET foreign_key_checks=?";
    		String fr2 = "SET foreign_key_checks=?";
    		int rs1,rs2,rs3,rs4;
    		try(PreparedStatement pst1 = conn.prepareStatement(fr1)){
    			pst1.setInt(1, 0);
    			pst1.execute();
    		}
    		try(PreparedStatement pst2 = conn.prepareStatement(deletequery)){
    			pst2.setInt(1, PID);
        		rs1 = pst2.executeUpdate();
    		}
    		try(PreparedStatement pst3 = conn.prepareStatement(deletePhoneQuery)){
    			pst3.setInt(1, PID);
        		rs2 = pst3.executeUpdate();
    		}
    		try(PreparedStatement pst4 = conn.prepareStatement(deleteEmailQuery)){
    			pst4.setInt(1, PID);
        		rs3 = pst4.executeUpdate();
    		}
    		try(PreparedStatement pst5 = conn.prepareStatement(deleteDateQuery)){
    			pst5.setInt(1, PID);
        		rs4 = pst5.executeUpdate();
    		}
        	try(PreparedStatement pst6 = conn.prepareStatement(fr2)){
        		pst6.setInt(1, 1);
        				pst6.execute();
        				if (rs4 != 0) {
            				return true;
            			}
    		}
    		catch(Exception e) {
    			System.out.println("Error1:" + e.getMessage());
    		}
    	}
    	catch(Exception e) {
    		System.out.println("Error2:" + e.getMessage());
    	}
    	return false;
    }

    
}

