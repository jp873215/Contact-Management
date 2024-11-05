package QueryLayer;

import java.sql.Connection;
import java.sql.ResultSet;

import javax.sql.DataSource;

import Enums.Table;
import Hikari.ConnectionPool;
import POJO.user;
import QueryBuilder.SQLQB;

public class Layer {
    private static DataSource dataSource = ConnectionPool.getDataSource();
    SQLQB qb = new SQLQB();
    
    // loginUser
//    public void loginLayer(user obj) {
//        
//        try{
//        	
//        	// just calling the mysql qb and athu returns string
//        	
//        	//issue1 - never pass String -- constraint nee
//        	
//        	String query = qb.select(Table.TableEnum.USER_DATA, Table.ColumnEnum.USER_DATA_UID, Table.ColumnEnum.USER_DATA_NAME)
//        			.join(Table.ColumnEnum.USER_DATA_EMAIL_ID, "")
//        			.build();
//        	
//        	
//        	String query1 = qb.select(Table.user_data, Table.user_data.UID , ColumnEnum.NAME)
//                    .join("INNER", TableEnum.UT_EMAIL_ID, "poda")
//                    .where(new String[] {
//                        String.format("%s.%s = ?", TableEnum.UT_EMAIL_ID.getTableName(), ColumnEnum.EMAIL_ID.getColumnName()),
//                        String.format("%s.%s = ?", TableEnum.USER_DATA.getTableName(), ColumnEnum.PASSWORD.getColumnName())
//                    }, new String[] {"AND"}).build(); 
//        	
//        	
//        	
//            
//        } catch (Exception e) {
//            System.out.println("Error with building the query: " + e.getMessage());
//        }
//        
//    }
}
