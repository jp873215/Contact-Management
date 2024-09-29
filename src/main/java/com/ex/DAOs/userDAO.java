package com.ex.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ex.pojo.user;

public class userDAO {
    private final String URL = "jdbc:mysql://localhost:3306/users";
    private final String root = "root";
    private final String pass = "Prakashrmkec@873215$";
    
    private Connection getConnection() throws ClassNotFoundException, SQLException{
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	Connection conn = DriverManager.getConnection(URL, root, pass);
    	return conn;       
    }
    
    public user addUser(user uobj) {
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = getConnection();
            String queryUsers = "INSERT INTO user_data(Name, Gender, Password, Home_Address, Work_Address) values (?, ?, ?, ?, ?)";
            String queryEmail = "INSERT INTO ut_email_id(UID, Email_ID, Label, Starred) values (?, ?, ?, 1)";
            String queryPhone = "INSERT INTO ut_phone(UID, Phone_Number, Label) values (?, ?, ?)";
            String salt_query = "INSERT INTO salt(UID, SALT) values (?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(queryUsers, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, uobj.getName());
                pst.setString(2, uobj.getGender());
                pst.setString(3, uobj.getPassword());
                pst.setString(4, uobj.getHomeAddress());
                pst.setString(5, uobj.getWorkAddress());

                int rs = pst.executeUpdate();
                int uid = 0;
                if (rs != 0) {
                    try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            uid = generatedKeys.getInt(1);
                            uobj.setUID(uid);
                            uobj.setName(uobj.getName());
                            
                        }
                    }

                    try (PreparedStatement pst1 = conn.prepareStatement(queryEmail)) {
                        pst1.setInt(1, uid);
                        pst1.setString(2, uobj.getEmailID());
                        pst1.setString(3, uobj.getEmailIDLabel());
                        int rs1 = pst1.executeUpdate();

                        if (rs1 != 0) {
                        	uobj.setEmailID(uobj.getEmailID());
                            try (PreparedStatement pst2 = conn.prepareStatement(queryPhone)) {
                                pst2.setInt(1, uid);
                                pst2.setLong(2, uobj.getPhoneNumber());
                                pst2.setString(3, uobj.getPhoneLabel());
                                int rs2 = pst2.executeUpdate();
                                if (rs2 != 0) {
                                	try(PreparedStatement pst3 = conn.prepareStatement(salt_query)){
                                		pst3.setInt(1, uid);
                                		pst3.setString(2, uobj.getSalt());
                                		int rs3 = pst3.executeUpdate();
                                		if (rs3 != 0) {
                                			return uobj;
                                		}
                                		
                                	}
                                }
                            }
                            catch(Exception e) {
                            	System.out.println("Error2:" + e.getMessage());
                            }
                        }
                    }
                    catch(Exception e) {
                    	System.out.println("Error1:" + e.getMessage());
                    }
                } 
                else {
                    System.out.println("Wrong Information");
                }
            }
        } 
    	catch (Exception e) {
            System.out.println("DB Driver Error: " + e.getMessage());
        }
        return null;
    }
    
    public user loginUsers(user obj) {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		String query = "select user_data.UID from user_data JOIN ut_email_id on user_data.UID = ut_email_id.UID where ut_email_id.Email_ID = ? AND user_data.Password = ?";
        	String namequery = "select Name from user_data where UID = ?;";
    		Connection conn = getConnection();
        	try(PreparedStatement pst = conn.prepareStatement(query)){
        		pst.setString(1, obj.getEmailID());
        		pst.setString(2, obj.getPassword());
        		
        		ResultSet rs = pst.executeQuery();
        		if(rs.next()) {
        			int uid = rs.getInt("UID");
        			System.out.println(uid);
        			if (uid !=0) {
            			try (PreparedStatement pst1 = conn.prepareStatement(namequery)){
            				pst1.setInt(1, uid);
            				ResultSet rs1 = pst1.executeQuery();
            				if (rs1.next())
            				{
            					String name = rs1.getString("Name");
                				user uobj = new user();	
                				uobj.setName(name);
                				uobj.setPassword(obj.getPassword());
                				uobj.setEmailID(obj.getEmailID());
                				uobj.setUID(uid);
                				return uobj;
            				}
            			}
            			catch(Exception e) {
            				System.out.println("Name query Error");
            			}
        			}
        		}
        	}
        	catch(Exception e) {
        		System.out.println("Unable query from userDAO");
        	}
    	}
    	catch(Exception e) {
    		System.out.println("Unable connect from userDAO");
    	}
    	return null;
    }
    
    public user getSaltandHash(user obj) throws ClassNotFoundException, SQLException {
    	Connection conn = getConnection();
    	user uobj = new user();
    	try {
    		String getsaltquery = "SELECT SALT FROM salt WHERE UID = ?";
    		String getuidquery = "SELECT UID FROM ut_email_id WHERE Email_ID = ?";
    		String gethashquery = "SELECT Password, Name FROM user_data WHERE UID = ?";
    		
    		try(PreparedStatement pst1 = conn.prepareStatement(getuidquery)){
    			pst1.setString(1,obj.getEmailID());
    			
    			ResultSet rs = pst1.executeQuery();
    			if(rs.next()) {
    				System.out.println("UID checkpoint");
    				int uid = rs.getInt("UID");
    				uobj.setUID(uid);
    				
    				try(PreparedStatement pst2 = conn.prepareStatement(getsaltquery)){
    					pst2.setInt(1, uid);
    					
    					ResultSet rs1 = pst2.executeQuery();
    					if (rs1.next()) {
    						System.out.println("SALT checkpoint");
    						uobj.setSalt(rs1.getString("SALT"));
    						
    						try(PreparedStatement pst3 = conn.prepareStatement(gethashquery)){
    	    					pst3.setInt(1, uid);
    	    					
    	    					ResultSet rs2 = pst3.executeQuery();
    	    					if (rs2.next()) {
    	    						System.out.println("Hash checkpoint");
    	    						uobj.setPassword(rs2.getString("Password"));
    	    						uobj.setName(rs2.getString("Name"));
    	    						return uobj;
    	    					}
    						}
    						catch(Exception e) {
    							System.out.println("Didn't get into password query:" + e.getMessage());
    						}
    					}
    				}
    				catch(Exception e) {
    					System.out.println("Didn't get into salt query:" + e.getMessage());
    				}
    			}
    		}
    		catch(Exception e) {
				System.out.println("Didn't get into mail query:" + e.getMessage());
			}
    	}
    	catch(Exception e) {
    		System.out.println("Error:" + e.getMessage());
    	}
    	return null;
    }
    
}
