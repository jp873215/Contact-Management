package com.ex.QueryLayer.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.ex.Hikari.ConnectionPool;

public class MySQLDAO {
	
	//private static final long TIMEOUT_PERIOD = 30 * 60 * 1000;
    private static DataSource dataSource = ConnectionPool.getDataSource();
    
    
    public ArrayList<Object> AddUser(String query) {
    	ArrayList<Object> li = new ArrayList<>();
    	try(Connection conn = dataSource.getConnection()){
    		conn.setAutoCommit(false);
    		try(PreparedStatement pst = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
    			int res = pst.executeUpdate();
    			try(ResultSet generatedKeys = pst.getGeneratedKeys()){
    				if (generatedKeys.next()) {
						li.add(String.valueOf(generatedKeys.getInt(1)));
					}
				}
				catch(Exception e) {
					
				}
			}
    		catch(Exception e) {
			System.out.println("Error at the add users " + e.getMessage());
    		}
    		
    	}
    	catch(Exception e) {
    		
    	}
    	
    	return li;
    }
}
