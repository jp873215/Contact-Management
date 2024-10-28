package com.ex.Hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionPool {
    public static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/users");
        config.setUsername("root"); 
        config.setPassword("Prakashrmkec@873215$"); 

        config.setMaximumPoolSize(10); 
        config.setConnectionTimeout(30000);  
        config.setIdleTimeout(600000); 
        config.setMaxLifetime(1800000);  

        dataSource = new HikariDataSource(config);
    }
    
    // Method to get the data source instance
    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}
