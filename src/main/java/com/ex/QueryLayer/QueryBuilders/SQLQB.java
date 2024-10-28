package com.ex.QueryLayer.QueryBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ex.enums.Table;

public class SQLQB {
	
	public StringBuilder query;
	public SQLQB() {
		this.query = new StringBuilder();
	}
	
	public SQLQB select(String tablename, String... columnNames) {
		query.append("SELECT ");
		
		if (columnNames == null || columnNames.length == 0) {
			query.append(" * ");
		}
		else {
			query.append(String.join(", ", columnNames));
		}
		query.append(" FROM ").append(tablename);
		
		return this;
	}
	
	public SQLQB insert(String tableName , Table.ColumnEnum[] columnList, Object[] values) {
		
		
//		if (columnName.size() != values.length) {
//			throw new IllegalArgumentException("columns and names should be in same count");
//		}
	    List<String> ColList = new ArrayList<>();
	    for (Table.ColumnEnum column : columnList) {
	        ColList.add(column.getColumnName()); // Get the column name from the enum
	    }
		
		query.append("INSERT INTO ").append(tableName).append(" ( ")
		.append(String.join(", ", ColList))
		.append(" ) VALUES ( ");

		
		for (int i = 0; i < values.length; i++) {
	        query.append("?");
	        if (i < values.length - 1) {  
	            query.append(", ");
	        }
	    }
		query.append(") ");		
		
		return this;
	}
	
	public SQLQB or() {
		query.append(" or ");
		return this;
	}
	
	public SQLQB and() {
		query.append(" and ");
		return this;
	}
	
	public SQLQB where(String[] conditions, String[] logics) {
		query.append(" where ");
		
		if (conditions.length > 0) {
	        StringBuilder whereClause = new StringBuilder();
	        for (int i = 0; i < conditions.length; i++) {
	            whereClause.append(conditions[i]);

	            if (i < logics.length) {
	                whereClause.append(" ").append(logics[i]).append(" ");
	            }
	        }
	        if (conditions.length > 1) {
	            query.append("(").append(whereClause.toString().trim()).append(")");
	        } else {
	            query.append(whereClause.toString().trim());
	        }
	    }
		return this;
	}
	
	public SQLQB delete(String tableName) {
		query.append(" DELETE FROM ").append(tableName).append(" ");
		return this;
	}
	
	public SQLQB update(String tableName) {
		query.append("UPDATE ")
		.append(tableName).append(" SET ");
		
		return this;
	}
	
	public SQLQB set(String column, String value) {
	    if (query.toString().contains("SET ")) {
	        query.append(", "); 
	    }
	    query.append(column).append(" = ").append(value); 
	    return this;
	}
	
	public SQLQB join(String type, String tableName, String condition) {
		
		if (query.length() > 0) {
			query.append(" ");
		}
		
		query.append(type)
		.append(" JOIN ")
		.append(tableName)
		.append(" ON ")
		.append(condition);
		
		
		return this;
	}
	
	public String build() {
		return this.toString();
	}
	
	@Override
	public String toString() {
		return query.toString();
	}
	
	
	
	// build the query and execute directly after the query is built --> done in the SQLQB
	
	
	
}
 