package com.ex.DAOs;


import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.ex.Hikari.*;
import com.ex.pojo.contact;


public class contactDAO {
//    private final String URL = "jdbc:mysql://localhost:3306/users";
//    private final String root = "root";
//    private final String pass = "Prakashrmkec@873215$";
    private static DataSource dataSource = ConnectionPool.getDataSource();
    
    
    public contact addContact(contact ct, int UID) throws ClassNotFoundException, SQLException{
        int PID = -1;
        contact obj = new contact();
        String contact_query = "INSERT INTO contact_detail(UID, Name, Note, Related_Person, Home_Address, Work_Address) values (?, ?, ?, ?, ?, ?);";
        String email_query = "INSERT INTO ct_email_id(PID, Email_ID, Label) values (?, ?, ?);";
        String phone_query = "INSERT INTO ct_phone(PID, Phone_No, Label) values (?, ?, ?);";
        String date_query = "INSERT INTO ct_date(PID, Date, Label) values (?, ?, ?);";
        try(Connection conn = dataSource.getConnection()){
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
                        pst2.setString(2, ct.getEmailID().get(0));
                        pst2.setString(3, ct.getEmailIDLabel().get(0));
                        
                        int res2 = pst2.executeUpdate();

                        if (res2 != 0){
                        	System.out.println("res2");
                            try (PreparedStatement pst3 = conn.prepareStatement(phone_query)) {
                                pst3.setInt(1, PID);
                                pst3.setLong(2, ct.getPhoneNumber().get(0));
                                pst3.setString(3, ct.getPhoneLabel().get(0));
                                
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
        }
        catch(Exception e) {
        	System.out.println("Error3:" + e.getMessage());
        }
        return null;
    }

//    private Connection getConnection() throws ClassNotFoundException, SQLException{
//    	Class.forName("com.mysql.cj.jdbc.Driver");
//    	Connection conn = DriverManager.getConnection(URL, root, pass);
//    	return conn;
//            
//    }
    public int addEmail(contact ct, int UID){
        try (Connection conn = dataSource.getConnection()) {
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
    	try(Connection conn = dataSource.getConnection()){
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
        try (Connection conn = dataSource.getConnection()) {
            String deleteQuery = "DELETE FROM contact_detail WHERE PID = ?;";
            String deletePhoneQuery = "DELETE FROM ct_phone WHERE PID = ?;";
            String deleteEmailQuery = "DELETE FROM ct_email_id WHERE PID = ?;";
            String deleteDateQuery = "DELETE FROM ct_date WHERE PID = ?;";
            String deleteImageQuery = "DELETE FROM ct_image WHERE PID = ?;";
            String deleteCategoryQuery = "DELETE FROM category_group WHERE PID = ?;";
            String setForeignKeyCheckOff = "SET foreign_key_checks = 0;";
            String setForeignKeyCheckOn = "SET foreign_key_checks = 1;";
            try (PreparedStatement disableFKCheck = conn.prepareStatement(setForeignKeyCheckOff)) {
                disableFKCheck.executeUpdate();
            }

            try {
                conn.setAutoCommit(false); 
                // Delete from the main contact table
                try (PreparedStatement pst1 = conn.prepareStatement(deleteQuery)) {
                    pst1.setInt(1, PID);
                    pst1.executeUpdate();
                }

                // Delete from phone table
                try (PreparedStatement pst2 = conn.prepareStatement(deletePhoneQuery)) {
                    pst2.setInt(1, PID);
                    pst2.executeUpdate();
                }

                // Delete from email table
                try (PreparedStatement pst3 = conn.prepareStatement(deleteEmailQuery)) {
                    pst3.setInt(1, PID);
                    pst3.executeUpdate();
                }

                // Delete from date table
                try (PreparedStatement pst4 = conn.prepareStatement(deleteDateQuery)) {
                    pst4.setInt(1, PID);
                    pst4.executeUpdate();
                }

                // Delete from image table
                try (PreparedStatement pst5 = conn.prepareStatement(deleteImageQuery)) {
                    pst5.setInt(1, PID);
                    pst5.executeUpdate();
                }

                // Delete from category group table
                try (PreparedStatement pst6 = conn.prepareStatement(deleteCategoryQuery)) {
                    pst6.setInt(1, PID);
                    pst6.executeUpdate();
                }

                // If all deletions succeeded, commit the transaction
                conn.commit();

                // Re-enable foreign key checks
                try (PreparedStatement enableFKCheck = conn.prepareStatement(setForeignKeyCheckOn)) {
                    enableFKCheck.executeUpdate();
                }

                return true; // Success

            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction in case of an error
                System.out.println("Error occurred during deletion: " + e.getMessage());
                return false;
            } finally {
                // Always enable foreign key checks even if rollback occurs
                try (PreparedStatement enableFKCheck = conn.prepareStatement(setForeignKeyCheckOn)) {
                    enableFKCheck.executeUpdate();
                }
            }
        }
    }
    public contact getContact(contact cObj) {
    	try (Connection conn = dataSource.getConnection()){
    		//Connection conn = getConnection();
    		String query = "SELECT cd.PID, cd.Name, cd.Note, cd.Related_Person, " +
                    "cd.Home_Address, cd.Work_Address, " +
                    "cp.Phone_No, cp.Label AS PhoneLabel, " +
                    "ce.Email_ID, ce.Label AS EmailLabel, " +
                    "cd2.Date, cd2.Label AS DateLabel " + 
                    "FROM contact_detail cd " +
                    "LEFT JOIN ct_phone cp ON cd.PID = cp.PID " +
                    "LEFT JOIN ct_email_id ce ON cd.PID = ce.PID " +
                    "LEFT JOIN ct_date cd2 ON cd.PID = cd2.PID " +
                    "WHERE cd.PID = ?";
    		
    		try(PreparedStatement pst = conn.prepareStatement(query)){
    			pst.setInt(1, cObj.getPID());
    			ResultSet rs = pst.executeQuery();
    			if(rs.next()) {
    				contact ct = new contact();
    				ct.setName(rs.getString("Name"));
    				ct.setPID(cObj.getPID());
    				ct.setNotes(rs.getString("Note"));
    				ct.setRelatedPerson(rs.getString("Related_Person"));
    				ct.setHomeAddress(rs.getString("Home_Address"));
    				ct.setWorkAddress(rs.getString("Work_Address"));
    				ct.setPhoneNumber(rs.getLong("Phone_No"));
    				ct.setPhoneLabel(rs.getString("PhoneLabel"));
    				ct.setEmailID(rs.getString("Email_ID"));
    				ct.setEmailIDLabel(rs.getString("EmailLabel"));
    				ct.setDate(rs.getString("Date"));
    				ct.setDateLabel(rs.getString("DateLabel"));
    				return ct;
    				
    				
    			}
    		}
    		catch(Exception e) {
    			System.out.println("Check the input value and name");
    		}
    	}
    	catch(Exception e) {
    		System.out.println("DB Error");
    	}
    	return null;
    }
    public boolean updateContact(contact ct) throws ClassNotFoundException, SQLException {
        String updateQuery = "UPDATE contact_detail SET Name = ?, Note = ?, Related_Person = ?, " +
                             "Home_Address = ?, Work_Address = ? WHERE PID = ?";
        
        String updatePhoneQuery = "UPDATE ct_phone SET Phone_No = ?, Label = ? WHERE PID = ?";
        String updateEmailQuery = "UPDATE ct_email_id SET Email_ID = ?, Label = ? WHERE PID = ?";
        String updateDateQuery = "UPDATE ct_date SET Date = ?, Label = ? WHERE PID = ?";
        
        try(Connection conn = dataSource.getConnection()){
        	try (PreparedStatement pst = conn.prepareStatement(updateQuery)) {
                pst.setString(1, ct.getName());
                pst.setString(2, ct.getNotes());
                pst.setString(3, ct.getRelatedPerson());
                pst.setString(4, ct.getHomeAddress());
                pst.setString(5, ct.getWorkAddress());
                pst.setInt(6, ct.getPID());
                int res = pst.executeUpdate();
                if (res !=0) {
                	System.out.println("ulla vanten1");
                	try (PreparedStatement pst1 = conn.prepareStatement(updatePhoneQuery)) {
                        pst1.setLong(1, ct.getPhoneNumber().get(0));
                        pst1.setString(2, ct.getPhoneLabel().get(0));
                        pst1.setInt(3, ct.getPID());
                        int res1 = pst1.executeUpdate();
                        if (res1 != 0) {
                        	System.out.println("ulla vanten2");
                        	try (PreparedStatement pst2 = conn.prepareStatement(updateEmailQuery)) {
                                pst2.setString(1, ct.getEmailID().get(0));
                                pst2.setString(2, ct.getEmailIDLabel().get(0));
                                pst2.setInt(3, ct.getPID());
                                int res2 = pst2.executeUpdate();
                                if (res2 != 0) {
                                	System.out.println("ulla vanten3");
                                	try (PreparedStatement pst3 = conn.prepareStatement(updateDateQuery)) {
                                        pst3.setString(1, ct.getDate());
                                        pst3.setString(2, ct.getDateLabel());
                                        pst3.setInt(3, ct.getPID());
                                        int res3 = pst3.executeUpdate();
                                        if (res3 != 0) {
                                        	System.out.println("ulla vanten4");
                                        	return true;
                                        }
                                        
                                    }
                                	catch (SQLException e) {
                                        System.out.println("Database update error1: " + e.getMessage());}
                                }
                            }
                        	catch (SQLException e) {
                                System.out.println("Database update error2: " + e.getMessage());
                            }
                        }
                        
                	}
                	catch (SQLException e) {
                        System.out.println("Database update error: " + e.getMessage());
                    }
                }

            } catch (SQLException e) {
                System.out.println("Database update error3: " + e.getMessage());
            }
        }
        catch (SQLException e) {
            System.out.println("Database update error3: " + e.getMessage());
        }
        return false;
    }
    
//    public contact getContacts(String name) throws ClassNotFoundException, SQLException {
//    	Connection conn = getConnection();
//    	//first get the category id using the category name and then using the category id get the list of contacts
//    	try {
//    		String query_cid = "SELECT Category_ID from category where Category_Name = ?";
//    		String query_
//    		try(PreparedStatement pst = conn.prepareStatement(query)){
//    			pst.setString(1, name);
//    			ResultSet rs = pst.executeQuery();
//    			if(rs.next()) {
//    				int cid = rs.getInt("Category_ID");
//    				
//    				try(PreparedStatement pst = conn.prepareStatement(query)){
//    					
//    				}
//    			}
//    			
//    		}
//    	}
//    	catch(Exception e) {
//    		System.out.println("Error from contactDAO: " + e.getMessage());
//    	}
//    	return null;
//    }
    
    
    public List<Integer> getPIDCat(String cat) throws ClassNotFoundException, SQLException{

    	try (Connection conn = dataSource.getConnection()){
    		String query_pid = "select IFNULL(PID,0) as val from category_group where Category_ID=?";
    		String query_cid = "select Category_ID from category where Category_Name =?";
    		List<Integer> li = new ArrayList<>();
    		
    		try(PreparedStatement pst = conn.prepareStatement(query_cid)){
    			pst.setString(1, cat);

    			ResultSet rs = pst.executeQuery();
    			if(rs.next()) {
    				int cid = (int) rs.getInt("Category_ID");
    				try(PreparedStatement pst1 = conn.prepareStatement(query_pid)){
    					pst1.setInt(1, cid);
    					ResultSet rs1 = pst1.executeQuery();
    					while(rs1.next()) {
    						li.add(rs1.getInt("val"));
    					}
    					return li;
    				}
    				catch(Exception e) {
    					System.out.println("Error1:" +e.getMessage());
    				}
    			}
    			
    		}
    				
    	}
    	catch(Exception e) {
    		System.out.println("Error2:" +e.getMessage());
    	}
    	return null;
    	
    }
    
    public boolean addContactintoCategory(List<Integer> li, int cid) throws ClassNotFoundException, SQLException {
    	
    	
    	try (Connection conn = dataSource.getConnection()){
    		String query = "Insert into category_group(PID, Category_ID) values(?,?);";
    		try(PreparedStatement pst1 = conn.prepareStatement(query)){
    			for(int val : li) {
    				pst1.setInt(1, val);
    				pst1.setInt(2, cid);
    				pst1.addBatch();
    			}
    			int[] countres = pst1.executeBatch();
    			if (countres.length != 0) {
    				return true;
    			}
    		}
    	}
    	catch(Exception e) {
    		System.out.println("Error:" + e.getMessage());
    	}
    	return false;
    }
    
    public List<Integer> getPIDsfromCat(int cid) throws SQLException, ClassNotFoundException{
    	List<Integer> li = new ArrayList<>();
    	String query = "select PID from category_group where Category_ID=?";
    	try(Connection conn = dataSource.getConnection()){
        	try(PreparedStatement pst = conn.prepareStatement(query)){
        		pst.setInt(1, cid);
        		ResultSet rs = pst.executeQuery();
        		while(rs.next()) {
        			li.add(rs.getInt("PID"));
        		}
        		return li;
        		
        	}
        	catch(Exception e) {
        		System.out.println("Error:" + e.getMessage());
        	}
    	}
    	catch(Exception e) {
    		System.out.println("Error:" + e.getMessage());
    	}
    	return null;
    }
    
    public int getCID(String cat_name) throws ClassNotFoundException, SQLException {
    	String query_cid = "Select Category_ID from category where Category_Name=?;";
		try(Connection conn = dataSource.getConnection()){
			try(PreparedStatement pst = conn.prepareStatement(query_cid)){
				pst.setString(1, cat_name);
				ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					return rs.getInt("Category_ID");
				}
			}
			catch(Exception e) {
				System.out.println("Error:" + e.getMessage());
			}
		}
		catch(Exception e) {
			
		}
		return 0;
    }
    
    public List<String> getContactName(List<Integer> li) throws ClassNotFoundException, SQLException {
        List<String> strli = new ArrayList<>();  // To store the result
        String query = "SELECT Name FROM contact_detail WHERE PID = ?";

        try(Connection conn = dataSource.getConnection()){
        	try (PreparedStatement pst = conn.prepareStatement(query)) {
                // Iterate over the list of PIDs
                for (int pid : li) {
                    pst.setInt(1, pid);  // Set PID value in the query
                    try (ResultSet rs = pst.executeQuery()) {
                        // Fetch the results
                        if (rs.next()) {
                            strli.add(rs.getString("Name"));  // Add the name to the list
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        catch(Exception e) {
        	System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        

        return strli;  
    }
    
    public List<contact> getCategoryMembers(int Category_ID){
    	List<contact> li = new ArrayList<>();
    	String query = "SELECT cd.PID, cd.Name, cd.Related_Person, cd.Home_Address, cd.Work_Address, ce.Email_ID, ce.Label AS Email_Label, cp.Phone_No, cp.Label AS Phone_Label FROM category_group cg JOIN contact_detail cd ON cd.PID = cg.PID LEFT JOIN ct_email_id ce ON ce.PID = cd.PID LEFT JOIN ct_phone cp ON cp.PID = cd.PID WHERE cg.Category_ID = ?;";
    	
    	try(Connection conn = dataSource.getConnection()){
    		try(PreparedStatement pst = conn.prepareStatement(query)){
    			pst.setInt(1, Category_ID);
    			ResultSet rs = pst.executeQuery();
    			while(rs.next()) {
    				contact obj = new contact();
    				obj.setPID(rs.getInt("PID"));
    				obj.setName(rs.getString("Name"));
    				obj.setRelatedPerson(rs.getString("Related_Person"));
    				obj.setHomeAddress(rs.getString("Home_Address"));
    				obj.setWorkAddress(rs.getString("Work_Address"));
    				obj.setEmailID(rs.getString("Email_ID"));
    				obj.setEmailIDLabel(rs.getString("Email_Label"));
    				obj.setPhoneNumber(rs.getLong("Phone_No"));
    				obj.setPhoneLabel(rs.getString("Phone_Label"));
    				li.add(obj);
    			}
    		}
    		catch(Exception e) {
    			System.out.println("Error with getting Contact IDs getCategoryMembers - UserDAO :" + e.getMessage());
    		}
    	}
    	catch(Exception e) {
    		System.out.println("Error with connection DB getCategory - UserDAO :" + e.getMessage());
    	}
    	
    	return li;
    }
    
    public contact insertMemberIntoCategory(contact cObj) {
    	try (Connection conn = dataSource.getConnection()){
        	String getCID = "SELECT Category_ID from category where Category_Name = ?";
        	String query_CID = "SELECT c.PID\n"
        			+ "FROM contact_detail c\n"
        			+ "JOIN ct_phone p ON c.PID = p.PID\n"
        			+ "JOIN ct_email_id e ON c.PID = e.PID\n"
        			+ "WHERE c.Name = ? AND p.Phone_No = ? AND e.Email_ID = ?;";
        	String query_insert = "INSERT INTO category_group(PID, Category_ID) values(?, ?)";
        	
        	try(PreparedStatement pst = conn.prepareStatement(query_CID)){
        		pst.setString(1, cObj.getName());
        		pst.setLong(2, cObj.getPhoneNumber().get(0));
        		pst.setString(3, cObj.getEmailID().get(0));
        		ResultSet rs = pst.executeQuery();
        		if (rs.next()) {
        			cObj.setPID(rs.getInt("PID"));
        			System.out.println("PID from contactDAO for adding members is: "+ cObj.getPID());
        			try(PreparedStatement pst1 = conn.prepareStatement(getCID)){
        				pst1.setString(1, cObj.getCategory().get(0));
        				ResultSet rs1 = pst1.executeQuery();
        				if (rs1.next()) {
        					cObj.setCategoryID(rs1.getInt("Category_ID"));
        					
                			try(PreparedStatement pst2 = conn.prepareStatement(query_insert)){
                				pst2.setInt(1, cObj.getPID());
                				pst2.setInt(2, cObj.getCategoryID().get(0));
                				int res = pst2.executeUpdate();
                				
                				if (res !=0) {
                					return cObj;
                				}
                			}
                			catch(Exception e) {
                				System.out.println("Error with connection DB getCategory - UserDAO :" + e.getMessage());
                			}
        				}
        				
        			}
        			catch(Exception e) {
        				
        			}
        		}
        		
        	}
        	catch(Exception e) {
        		System.out.println("Error with connection DB getCategory - UserDAO :" + e.getMessage());
        	}
        	
    	}
    	catch(Exception e) {
    		System.out.println("Error with connection DB - UserDAO :" + e.getMessage());
    	}
    	return cObj;
    	
    }
    


    
}

