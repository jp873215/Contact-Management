package com.ex.QueryLayer;


//dump this java file
public class QueryBuilder {
	// SELECT
	// INSERT
	// UPDATE
	// DELETE
	// WHERE 
	// SET
	
	
	private StringBuilder query;
	
	private boolean isSelected;
	private boolean isInsert;
	private boolean isUpdate;
	private boolean isDelete;
	
	
	public QueryBuilder() {
		this.query = new StringBuilder();
	}
	
	public QueryBuilder insert(String tablename, String... columns) {
		
		
		this.isInsert = true;
		query.append("INSERT INTO ").append(tablename).append(" (");
		
		query.append(String.join(", ", columns)).append(") values (");
		
		query.append("?, ".repeat(columns.length));
		
		query.setLength(query.length()-2);
		
		query.append(")");
		return this;
	}
	
	public QueryBuilder select(String... columns) {
		
		this.isSelected = true;
		query.append("SELECT ");
		
		if (columns.length != 0) {
			query.append(String.join(", ", columns));
		}
		else {
			query.append("* ");
		}
		return this;
	}
	
	public QueryBuilder deleteFrom(String column, String value) {
		
		query.append("DELETE FROM ").append(column).append(" ");
		return this;
	}
	
	public QueryBuilder where(String condition) {
		
		query.append("WHERE ").append(condition).append(" ");
		return this;
	}
	
	public QueryBuilder from(String tablename) {
		query.append(" FROM ").append(tablename).append(" ");
		return this;
		
	}
	
	public QueryBuilder update(String tablename) {
		
		this.isUpdate = true;
		query.append("UPDATE ").append(tablename).append(" SET ");
		
		return this;
	}
	
	public QueryBuilder set(String column, String value) {
		query.append(column).append(" = '").append(value).append("', ");
		return this;
	}
	
	public QueryBuilder or(String condition) {
		query.append("OR ").append(condition).append(" ");
		return this;
	}
	
    public QueryBuilder and(String condition) {
        query.append("AND ").append(condition).append(" ");
        return this;
    }
    
    public QueryBuilder join(String tablename, String jointype, String condition) {
    	query.append(jointype).append(" JOIN ").append(tablename).append(" ON ").append(isDelete).append(condition).append(" ");
    	return this;
    }
    
	public String build() {
		
		if(isUpdate) {
			query.setLength(query.length()-2);
		}
		query.append(";");
		return query.toString();
	}
	
	public void reset() {
		query.setLength(0);
	}


}
