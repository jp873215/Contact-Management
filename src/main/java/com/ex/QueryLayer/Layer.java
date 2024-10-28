package com.ex.QueryLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import com.ex.Hikari.ConnectionPool;
import com.ex.pojo.user;

public class Layer {
    private static DataSource dataSource = ConnectionPool.getDataSource();
    QueryBuilder qb = new QueryBuilder();
    
    
    // loginUser
    public ResultSet loginLayer(user obj) {
        ResultSet rs = null;
        
        try (Connection conn = dataSource.getConnection()) {
            String query = qb.select("user_data.UID, user_data.Name")
                             .from("user_data")
                             .join("ut_email_id", "INNER", "user_data.UID = ut_email_id.UID")
                             .where("ut_email_id.Email_ID = ? AND user_data.Password = ?")
                             .build();
            
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setString(1, obj.getEmailID().get(0));
                pst.setString(2, obj.getPassword());
                
                rs = pst.executeQuery();
                return rs; 
            } catch (Exception e) {
                System.out.println("Unable to query from userDAO: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Connection Error: " + e.getMessage());
        }
        
        return null; 
    }


    
	
	
	
}
