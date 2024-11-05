package DAOs;

import java.io.PrintWriter;
import java.sql.Time;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;

import Enums.Table;
import Extras.deleteCookie;
import Hikari.*;
import POJO.contact;
import POJO.session;
import POJO.user;
import QueryBuilder.SQLQB;
import QueryLayer.Layer;

public class userDAO {
    private final String URL = "jdbc:mysql://localhost:3306/users";
    private final String root = "root";
    private final String pass = "Prakashrmkec@873215$";
    private static final long TIMEOUT_PERIOD = 30 * 60 * 1000;
    private static DataSource dataSource = ConnectionPool.getDataSource();
    SQLQB qb = new SQLQB();

    //QueryLayer ql = new QueryLayer();
    
    private Connection getConnection() throws ClassNotFoundException, SQLException{
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	Connection conn = DriverManager.getConnection(URL, root, pass);
    	return conn;       
    }

//    public Map<Object, Object> addUser(user uobj) throws ClassNotFoundException, SQLException {
//    	
//    	Object[] resultList = {uobj.getName(), uobj.getGender(), uobj.getPassword(), uobj.getHomeAddress(), uobj.getWorkAddress()};
//    	Table.ColumnEnum[] arr = {
//    							Table.ColumnEnum.NAME,
//    							Table.ColumnEnum.GENDER,
//    							Table.ColumnEnum.PASSWORD,
//    							Table.ColumnEnum.HOME_ADDRESS,
//    							Table.ColumnEnum.WORK_ADDRESS
//    							};
//    	
//    	try {
//    		List<Object> res = ql.Insertion(Table.TableEnum.USER_DATA, arr ,resultList);
//    		return null;
//    		
//    	}
//    	catch(Exception e) {
//    		
//    	}
//    	return null;
//    	
//    	
//    }
    		
//    		String queryUsers = qb.insert("user_data", "Name", "Gender", "Password", "Home_Address", "Work_Address").build();
//    		String queryEmail = qb.insert("ut_email_id", "UID", "EmailID", "Label", "Starred").build();
//    		String queryPhone = qb.insert("ut_phone", "UID", "Phone_Number", "Label").build();
            //String queryUsers = "INSERT INTO user_data(Name, Gender, Password, Home_Address, Work_Address) values (?, ?, ?, ?, ?)";
            //String queryEmail = "INSERT INTO ut_email_id(UID, Email_ID, Label, Starred) values (?, ?, ?, 1)";
            //String queryPhone = "INSERT INTO ut_phone(UID, Phone_Number, Label) values (?, ?, ?)";
//            String salt_query = "INSERT INTO salt(UID, SALT) values (?, ?)";
    		
    		
    
//    public user loginUsers(user obj) throws ClassNotFoundException, SQLException {
//    	ResultSet rs = null;
//    	user resObj = new user();
//    	
//    	try{
//    		rs = lObj.loginLayer(obj);
//    		if (rs != null && rs.next()) {
//    			resObj.setUID(rs.getInt("UID"));
//    			resObj.setName(rs.getString("Name"));
//    			resObj.setEmailID(obj.getEmailID().get(0));
//    			resObj.setPassword(obj.getPassword());
//    			return resObj;
//    		}
//    	}
//    	catch(Exception e) {
//    		
//    	}
//    	finally {
//    		if (rs!=null) {
//    			try {
//    				rs.close();
//    			}
//    			catch(Exception e) {
//    				System.out.println("Error closing ResultSet: " + e.getMessage());
//                }
//    		}
//    	}
//    	
//    	return null;
//    	
//    }
    
    public user loginUsers(user Obj) {
    	// uid
    	
    	//check whether limit 1 is required or not....
    	String query = "select user_data.UID as ResultSet from user_data JOIN ut_email_id on user_data.UID = ut_email_id.UID where user_data.Password = ? AND ut_email_id.Email_ID = ?";
    	try {
    		Connection conn = getConnection();
    		System.out.println("UID from Login" + Obj.getUID());
    		try(PreparedStatement pst = conn.prepareStatement(query)){
    			
    			pst.setString(1, Obj.getPassword());
    			pst.setString(2, Obj.getEmailID().get(0));
    			
    			ResultSet rs = pst.executeQuery();
    			
    			if(rs.next()) {
    				Obj.setUID(rs.getInt("ResultSet"));
    				return Obj;
    			}
    		}
    		catch(Exception e) {
    			System.out.println("Error in the execution of the Login" + e.getMessage());
    			e.printStackTrace();
    		}
    	}
    	catch(Exception e) {
			System.out.println("Error in the connection of the DB from Login" + e.getMessage());
			e.printStackTrace();
    	}
    	return null;
    }
    
    
    
    public boolean addCategory(user us) {
    	String query ="Insert into category(UID, Category_Name) values (?, ?);";
    	try (Connection conn = dataSource.getConnection()){
    		//Connection conn = getConnection();
        	try(PreparedStatement pst = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
        		System.out.println("UID from addCategory: " + us.getUID());
        		System.out.println("Cat from addCategory: " + us.getCategory().get(0));
        		pst.setInt(1, us.getUID());
        		pst.setString(2, us.getCategory().get(0));
        		
        		int res = pst.executeUpdate();
        		if (res != 0) {
        			return true;
            	}
        	}
        	catch(Exception e) {
        		System.out.println("Cannot add category" + e.getMessage());
        	}
    	}
    	catch(Exception e) {
    		System.out.println("DB Error" + e.getMessage());
    	}
    	
    	return false;	
    }
    
//    public List<user> getCategory(user us) throws ClassNotFoundException, SQLException {
//    	String query ="Select Category_Name from category where UID =?;";
//    	Connection conn = getConnection();
//    	try(PreparedStatement pst = conn.prepareStatement(query)){
//    		pst.setInt(1, us.getUID());
//    		List<user> ls = new ArrayList<user>();
//    		ResultSet rs = pst.executeQuery();
//    		while(rs.next()) {
//    			user obj = new user();
//    			obj.setCategory(rs.getString("Category_Name"));
//    			ls.add(obj);
//    		}
//    		if (ls.get(0).getUID() !=0) {
//    			System.out.println("UID from getCategory is: " + ls.get(0).getUID());
//    			return ls;
//    		}
//    	}
//    	catch(Exception e) {
//    		System.out.println("Cannot add category" + e.getMessage());
//    	}
//    	return null;	
//    }
    
    //try to use single obj and pass it all over
    public boolean deleteEmail(String email) throws ClassNotFoundException, SQLException {
    	//Connection conn = getConnection();
    	try (Connection conn = dataSource.getConnection()){
    		String query = "DELETE FROM ut_email_id where Email_ID = ?";
    		try(PreparedStatement pst = conn.prepareStatement(query)){
    			pst.setString(1, email)	;
    			
    			int res = pst.executeUpdate();
    			if (res != 0) {
    				return true;
    			}
    		}
    	}
    	catch(Exception e) {
    		System.out.println("Cannot Delete from userDAO: "+e.getMessage());
    		
    	}
    	return false;
    }
    
    public boolean deleteCategory(int CID) throws ClassNotFoundException, SQLException {
    	//Connection conn = getConnection();
    	try (Connection conn = dataSource.getConnection()){
    		String query = "DELETE FROM category where Category_ID = ?";
    		try(PreparedStatement pst = conn.prepareStatement(query)){
    			pst.setInt(1, CID);
    			int res = pst.executeUpdate();
    			if (res != 0) {
    				return true;
    			}
    		}
    	}
    	catch(Exception e) {
    		System.out.println("Cannot Delete from userDAO: "+e.getMessage());
    		
    	}
    	return false;
    }
    
    public boolean addEmail(user obj) throws ClassNotFoundException, SQLException {
    		//Connection conn = getConnection();
            String query = "INSERT INTO ut_email_id(UID, Email_ID, Label) values(?, ?, ?);";
            try(Connection conn = dataSource.getConnection()){
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    int UID = obj.getUID();
                    System.out.println("Email from addEmail is:" + obj.getEmailID().get(0));
                    System.out.println("UID is:" + UID);
                    System.out.println("UID is:" + UID);
                    if (UID != 0)
                    {
                        pst.setInt(1, UID);
                        pst.setString(2, obj.getEmailID().get(0)); 
                        pst.setString(3, obj.getEmailIDLabel().get(0));
                        int res = pst.executeUpdate();
                        if (res != 0){
                        	System.out.println("true");
                            return true;
                        }
                    }

                } 
                catch (Exception e) {
                    System.out.println("Error" + e.getMessage());}
            }
            catch(Exception e) {
            	System.out.println("Error" + e.getMessage());}
        System.out.println("false");
        return false;
    }
    
    public boolean sessiondata(session sessionObj) throws ClassNotFoundException, SQLException {
    	//Connection conn = getConnection();
    	String query = "INSERT INTO session(UID, Session_ID, Last_Interaction, Timeout_Period) values(?, ?, ?, ?);";
    	try(Connection conn = dataSource.getConnection()){
    		try(PreparedStatement pst = conn.prepareStatement(query)){
        		pst.setInt(1, sessionObj.getUid());
        		pst.setString(2, sessionObj.getSessionId());
        		pst.setTimestamp(3, Timestamp.valueOf(sessionObj.getLastActiveTime()));
        		pst.setInt(4, sessionObj.getTimeoutPeriod());
        		
        		int res = pst.executeUpdate();
        		if (res != 0) {
        			System.out.println("Successfully insert session data into DB");
        			return true;
        		}
        	}
        	catch(Exception e) {
        		System.out.println("Error with input:" + e.getMessage());
        	}
    	}
    	catch(Exception e) {
    		
    	}
    	return false;
    }
    
//    public boolean checksessiondata(int UID) throws ClassNotFoundException, SQLException {
//    	//Connection conn = getConnection();
//    	String query = "select 1 as Found from session where Session_ID = ?";
//    	try(Connection conn = dataSource.getConnection()){
//    		try(PreparedStatement pst = conn.prepareStatement(query)){
//        		pst.setInt(1, UID);
//        		ResultSet rs = pst.executeQuery();
//        		if(rs.next()) {
//        			return rs.getInt("Found") != 0; 
//        		}
//        	}
//        	catch(Exception e) {
//        		System.out.println("Error" + e.getMessage());
//        	}
//    	}
//    	catch(Exception e) {
//    		System.out.println("Error" + e.getMessage());
//    	}
//    	return false;
//    }
    
    public boolean clearCookie(session sObj, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
    	System.out.println("Inside Session DAO");
    	//Connection conn = getConnection();
    	String query = "DELETE FROM session where Session_ID = ?";
    	try(Connection conn = dataSource.getConnection()){
    		try(PreparedStatement pst = conn.prepareStatement(query)){
    			String SID =  (String) sObj.getSessionId();
    			System.out.println("SID is:" + SID);
    			pst.setString(1, SID);
    			int res = pst.executeUpdate();	
    			if (res !=0) {
    				if (deleteCookie.delete(request, response)) {
        				System.out.println("Cleared Session");
        				return true;
    				}
    			}
    			else {
    				System.out.println("SID is not found in the DB - May be the SessionID have deleted Already");
    			}
    	}
    	catch(Exception e) {
    		System.out.println("Error logging out!!" + e.getMessage());
    	}
    	}
    	catch(Exception e) {
    		
    	}
    	return false;
    }
    
    
    public boolean clearAllTheSession(session sObj, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
    	System.out.println("Inside Session DAO");
    	//Connection conn = getConnection();
    	String query = "DELETE FROM session where UID = ?";
    	try(Connection conn = dataSource.getConnection()){
    		try(PreparedStatement pst = conn.prepareStatement(query)){
    			int UID =  (int) sObj.getUid();
    			System.out.println("SID is:" + UID);
    			pst.setInt(1, UID);
    			int res = pst.executeUpdate();	
    			if (res !=0) {
    				if (deleteCookie.delete(request, response)) {
        				System.out.println("Cleared Session");
        				return true;
    				}
    			}
    			else {
    				System.out.println("SID is not found in the DB - May be the SessionID have deleted Already");
    			}
    	}catch(Exception e) {
    		System.out.println("Error logging out!!" + e.getMessage());
    		}
    	}
    	catch(Exception e) {
    		System.out.println("Error with connection");
    	}
    	return false;
    }
    
    
    public boolean updateLastInteractionTime(session sObj) {
    	try(Connection conn = dataSource.getConnection()) {
    		System.out.println("Inside the updateCookieData");
    		String query = "UPDATE session SET Last_Interaction = ? WHERE Session_ID = ?";
    		try(PreparedStatement pst = conn.prepareStatement(query)){
    			pst.setTimestamp(1, java.sql.Timestamp.valueOf(sObj.getLastActiveTime()));
    			pst.setString(2, sObj.getSessionId());
    			
    			int res = pst.executeUpdate();
    			if (res != 0) {
    				return true;
    			}
    		}
    		catch(Exception e) {
    			System.out.println("Error with updating the data: " + e.getMessage());
    		}
    	}
    	catch(Exception e) {
    		System.out.println("Error with DB Connection: " + e.getMessage());
    	}
    	
    	return false;
    }
    
    public boolean validateSession(String SID, int UID, HttpServletRequest request, HttpServletResponse response) {
    	try(Connection conn = dataSource.getConnection()) {
    		String query = "SELECT 1 as Result from session where Session_ID = ?";
    		try(PreparedStatement pst = conn.prepareStatement(query)){
    			pst.setString(1, SID);
    			ResultSet rs = pst.executeQuery();
    			if (rs.next()) {
    				int val = rs.getInt("Result");
    				if (val !=0){
    					System.out.println("Auth is completed from the DAO and Success!!");
    					return true;
    				}
    			}
    			else {
    				System.out.println("The session ID is not present in the DB - but deleted");
    				// if the user tries to add the SID manually he should not be able to get in
    			}
    		}
    		catch(Exception e) {
        		System.out.println("Error with getting the data from the DB " + e.getMessage());
    		}
    		
    	}
    	catch(Exception e) {
    		System.out.println("Error with connecting the DB " + e.getMessage());
    	}
    	return false;
    }
    
    
    public boolean updateTimeoutPeriod(session sObj) {
    	try(Connection conn = dataSource.getConnection()){
    		String query = "UPDATE session SET Timeout_Period = ? WHERE Session_ID = ?";
    		try(PreparedStatement pst = conn.prepareStatement(query)){
    			pst.setInt(1, sObj.getTimeoutPeriod());
    			pst.setString(2, sObj.getSessionId());
    			
    			int res = pst.executeUpdate();
    			if (res != 0) {
    				return true;
    			}
    		}
    		catch(Exception e) {
    			System.out.println("Error with updating the Timeout Period:" + e.getMessage());
    		}
    	}
    	catch(Exception e) {
    		System.out.println("Error with DB Connection: " + e.getMessage());
    	}
    	return false;
    }
    
    public user getUserFromSession(String SID) throws ClassNotFoundException, SQLException {
    	String Name_query = "SELECT Name from user_data where UID=?";
    	String Email_query = "SELECT Email_ID from ut_email_id where UID=?";
    	String UID_query = "SELECT UID from session where Session_ID=?";
    	user userObj = new user();
    	int UID = 0;
    	try(Connection conn = dataSource.getConnection()){
    		try(PreparedStatement pst = conn.prepareStatement(UID_query)){
        		pst.setString(1, SID);
        		
        		ResultSet rs = pst.executeQuery();
        		if(rs.next()) {
        			UID = Integer.parseInt(rs.getString("UID"));
        			userObj.setUID(UID);
        			try(PreparedStatement pst1 = conn.prepareStatement(Name_query)){
        				pst1.setInt(1, UID);
        				ResultSet rs1 = pst1.executeQuery();
        				if (rs1.next()) {
        					userObj.setName(rs1.getString("Name"));
        					try(PreparedStatement pst2 = conn.prepareStatement(Email_query)){
        						pst2.setInt(1, UID);
        						ResultSet rs2 = pst2.executeQuery();
        						while(rs2.next()) {
        							userObj.setEmailID(rs2.getString("Email_ID"));
        						}
        						return userObj;
        					}
        					catch(Exception e) {
        						System.out.println("Error with getting Email ID" + e.getCause());
        					}
        				}
        				
        			}
        			catch(Exception e) {
        				System.out.println("Error with getting Name" + e.getCause());
        			}
        		}
        		
        	}
        	catch(Exception e) {
        		System.out.println("Error with SessionID getting userDAO" + e.getCause());
        	}
    	}
    	catch(Exception e) {
    		
    	}
    	return null;
    }
    
    public user getUserFromSession(int UID) throws ClassNotFoundException, SQLException {
        String nameQuery = "SELECT Name, Gender, Home_Address, Work_Address, Password FROM user_data WHERE UID=?";
        String emailQuery = "SELECT Email_ID, Label FROM ut_email_id WHERE UID=?";
        String phoneQuery = "SELECT Phone_Number, Label FROM ut_phone WHERE UID=?";
        user userObj = new user();
        userObj.setUID(UID);
        
        try(Connection conn = dataSource.getConnection()){
        	try (PreparedStatement pst1 = conn.prepareStatement(nameQuery)) {
                pst1.setInt(1, UID);
                ResultSet rs1 = pst1.executeQuery();
                
                if (rs1.next()) {
                    userObj.setName(rs1.getString("Name"));
                    userObj.setGender(rs1.getString("Gender"));
                    userObj.setHomeAddress(rs1.getString("Home_Address"));
                    userObj.setWorkAddress(rs1.getString("Work_Address"));
                    userObj.setPassword(rs1.getString("Password"));
                    
                    // Fetch email IDs
                    try (PreparedStatement pst2 = conn.prepareStatement(emailQuery)) {
                        pst2.setInt(1, UID);
                        ResultSet rs2 = pst2.executeQuery();
                        
                        while (rs2.next()) {
                            userObj.setEmailID(rs2.getString("Email_ID")); // Add to the list
                            userObj.setEmailIDLabel(rs2.getString("Label")); // Add label if applicable
                        }
                    } catch (Exception e) {
                        System.out.println("Error with getting Email ID");
                        e.printStackTrace();
                    }
                    
                    // Fetch phone numbers
                    try (PreparedStatement pst3 = conn.prepareStatement(phoneQuery)) {
                        pst3.setInt(1, UID);
                        ResultSet rs3 = pst3.executeQuery();
                        
                        while (rs3.next()) {
                            userObj.setPhoneNumber(rs3.getLong("Phone_Number")); // Add to the list
                            userObj.setPhoneLabel(rs3.getString("Label")); // Add label if applicable
                        }
                    } catch (Exception e) {
                        System.out.println("Error with getting Phone Number");
                        e.printStackTrace();
                    }
                    
                    return userObj; // Return user object with populated fields
                }
            } catch (Exception e) {
                System.out.println("Error with getting user data");
                e.printStackTrace();
            }
        }
        catch(Exception e) {
        	
        }
        
        
        return null; // Return null if UID not found
    }

    
    public user getEmails(int UID){
    	System.out.println("ula vanten");
    	user userObj = new user();
		String query = "SELECT Email_ID FROM ut_email_id WHERE UID=? AND Starred is NULL";
    	try(Connection conn = dataSource.getConnection()){
    		//Connection conn = getConnection();
    		try(PreparedStatement pst = conn.prepareStatement(query)){
    			pst.setInt(1, UID);
    			ResultSet rs = pst.executeQuery();
    			while(rs.next()) {
    				System.out.println(rs.getString("Email_ID"));
    				userObj.setEmailID(rs.getString("Email_ID"));
    			}
    		}
    		catch(Exception e) {
    			System.out.println("Error with getting list of Email from DB" + e.getMessage());
    		}
    	}
    	catch(Exception e) {
    		System.out.println("Error with connection DB" + e.getMessage());
    	}
    	return userObj;
    }
    
    public user getCategory(int UID){
        user userObj = new user(); // Initialize the user object
        String query = "SELECT Category_ID, Category_Name from category where UID = ?";
        try(Connection conn = dataSource.getConnection()){
            //Connection conn = getConnection();
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setInt(1, UID);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getString("Category_Name") + " " + rs.getInt("Category_ID"));
                    userObj.setCategory(rs.getString("Category_Name")); // Use a method to add to a list
                    userObj.setCategoryID(rs.getInt("Category_ID")); // Use a method to add to a list
                }
                if (userObj.getCategory().isEmpty()) {
                    return null; // Return null if there are no categories
                }
                return userObj;
            } catch (Exception e) {
                System.out.println("Error with getting data from DB from getCategory - UserDAO :" + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error with connection DB getCategory - UserDAO :" + e.getMessage());
        }
        return null;
    }    
    
//    public user getUserInfo(int UID) {
//        user obj = null; // Initialize obj to null
//        String query = "SELECT "
//                + "ud.UID, ud.Name, ud.Gender, ud.Password, ud.Home_Address, "
//                + "ud.Work_Address, ue.Email_ID, ue.Label AS Email_Label, "
//                + "ue.Starred, up.Phone_Number, up.Label AS Phone_Label, "
//                + "ui.ut_photo "
//                + "FROM user_data ud "
//                + "LEFT JOIN ut_email_id ue ON ud.UID = ue.UID "
//                + "LEFT JOIN ut_phone up ON ud.UID = up.UID "
//                + "LEFT JOIN ut_image ui ON ud.UID = ui.UID "
//                + "WHERE ud.UID = ?";
//        try {
//        	Connection conn = getConnection();
//            try ( // Ensure you close the connection
//                    PreparedStatement pst = conn.prepareStatement(query)) {
//                   pst.setInt(1, UID);
//                   ResultSet rs = pst.executeQuery();
//                   if (rs.next()) {
//                       obj = new user();
//                       obj.setUID(rs.getInt("UID"));
//                       obj.setName(rs.getString("Name"));
//                       obj.setGender(rs.getString("Gender"));
//                       obj.setPassword(rs.getString("Password"));
//                       obj.setHomeAddress(rs.getString("Home_Address"));
//                       obj.setWorkAddress(rs.getString("Work_Address"));
//
//                       Long phoneNumber = rs.getLong("Phone_Number");
//                       if (rs.wasNull()) { // Check if the value was null
//                           obj.setPhoneNumber(null); // Set to null if not present
//                       } else {
//                           obj.setPhoneNumber(phoneNumber);
//                       }
//
//                       String emailID = rs.getString("Email_ID");
//                       if (rs.wasNull()) {
//                           obj.setEmailID(null); // Handle null value for Email_ID
//                       } else {
//                           obj.setEmailID(emailID);
//                       }
//
//                       String emailLabel = rs.getString("Email_Label");
//                       if (rs.wasNull()) {
//                           obj.setEmailIDLabel(null);
//                       } else {
//                           obj.setEmailIDLabel(emailLabel);
//                       }
//
//                       String phoneLabel = rs.getString("Phone_Label");
//                       if (rs.wasNull()) {
//                           obj.setPhoneLabel(null);
//                       } else {
//                           obj.setPhoneLabel(phoneLabel);
//                       }
//
//                       // Debug output
//                       System.out.println("User Info: " + obj);
//                   } else {
//                       System.out.println("No user found with UID: " + UID); // Log if no user found
//                   }
//               } catch (SQLException e) {
//                   System.out.println("Error with getting data: " + e.getMessage());
//                   e.printStackTrace(); // Print stack trace for debugging
//               }
//        }
//        catch(Exception e) {
//        	
//        }
//        return obj; // Return the user object or null
//    }

    
    
}
