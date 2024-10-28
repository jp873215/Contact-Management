//package com.ex.QueryLayer;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.ex.DAOs.userDAO;
//import com.ex.QueryLayer.DAOs.MySQLDAO;
//import com.ex.QueryLayer.QueryBuilders.SQLQB;
//import com.ex.enums.Table;
//
//public class QueryLayer {
//	
//	MySQLDAO mysql = new MySQLDAO();
//	
//	
//	public List<Object> Insertion(Table.TableEnum tableName, Table.ColumnEnum[] columnList , Object[] valueList) {
//		
//		SQLQB qb = new SQLQB();
//		
//		String insert = qb.insert(tableName.getTableName(), columnList, valueList).build();
//		
//		List<Object> = mysql.AddUser(insert);
//		
//		
//	
//		
//		
//		return 0;	
//	}
//	
//	
//	// should only be triggered by the Insertion method
//	private ArrayList<Object> subInsertion(){
//		return null;
//	}
//	
//	
//	
//}
