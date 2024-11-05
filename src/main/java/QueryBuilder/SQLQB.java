package QueryBuilder;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import Enums.Table;
//
//public class SQLQB {
//    private StringBuilder query;
//
//    public SQLQB() {
//        this.query = new StringBuilder();
//    }
//
//    public SQLQB select(Table.TableEnum table, Table.ColumnEnum... columns) {
//        query.append("SELECT ");
//        if (columns.length == 0) {
//            query.append(" * ");
//        } else {
//        	for(Table.ColumnEnum col : columns) {
//        		query.append(col).append(", ");
//        	}
//        }
//        query.setLength(query.length()-2);
//        query.append(" FROM ").append(table.getTableName());
//        return this;
//    }
//
//    public SQLQB insert(Table.TableEnum table, Table.ColumnEnum[] columnList) {
//        query.append("INSERT INTO ").append(table.getTableName())
//             .append(" (").append(String.join(", ", getColumnNames(columnList))).append(") VALUES (");
//
//        for (int i = 0; i < columnList.length; i++) {
//            query.append("?").append(", ");
//        }
//        query.setLength(query.length()-2);
//        query.append(")");
//        return this;
//    }
//
//    public SQLQB delete(Table.TableEnum table) {
//        query.append("DELETE FROM ").append(table.getTableName());
//        return this;
//    }
//
//    public SQLQB update(Table.TableEnum table) {
//        query.append("UPDATE ").append(table.getTableName()).append(" SET ");
//        return this;
//    }
//
//    public SQLQB set(Table.ColumnEnum column) {
//        if (query.toString().contains("SET ")) {
//            query.append(", ");
//        }
//        query.append(column.getColumnName()).append(" = ?");
//        return this;
//    }
//
//    public SQLQB join(Table.TableEnum joinTable, Table.ColumnEnum... columns) {
//        query.append(" JOIN ").append(joinTable.getTableName())
//             .append(" ON ");
//        
//        int  i = 0;
//        if ((columns.length % 2) == 0) {
//        	while(i <= columns.length-1) {
//        		query.append(columns[i]).append(" = ").append(columns[i+1]);
//        	}
//        }
//        return this;
//    }
//
//    public SQLQB where(String[] conditions, String[] logics) {
//        query.append(" WHERE ");
//        
//        for (int i = 0; i < conditions.length; i++) {
//            query.append(conditions[i]);
//            if (i < logics.length) {
//                query.append(" ").append(logics[i]).append(" ");
//            }
//        }
//        return this;
//    }
//
//    public SQLQB or() {
//        query.append(" OR ");
//        return this;
//    }
//
//    public SQLQB and() {
//        query.append(" AND ");
//        return this;
//    }
//
//    public String build() {
//        return this.query.toString().trim();
//    }
//
//    @Override
//    public String toString() {
//        return build();
//    }
//
//    public void reset() {
//        query.setLength(0);
//    }
//
//    // Helper method to get column names for given column enums
//    private List<String> getColumnNames(Table.ColumnEnum[] columnList) {
//        List<String> colNames = new ArrayList<>();
//        for (Table.ColumnEnum column : columnList) {
//            colNames.add(column.getColumnName());
//        }
//        return colNames;
//    }
//}

import Enums.Table;
import Enums.TableColumn;

public class SQLQB {

    private StringBuilder query = new StringBuilder();

    public SQLQB select(TableColumn... columns) {
        query.append("SELECT ");
        for (int i = 0; i < columns.length; i++) {
            TableColumn col = columns[i];
            query.append(col.getTableEnum().getTableName())
                 .append(".")
                 .append(col.getColumnName());
            if (i < columns.length - 1) {
                query.append(", ");
            }
        }
        return this;
    }

    public SQLQB from(Table.TableEnum table) {
        query.append(" FROM ").append(table.getTableName());
        return this;
    }

    public SQLQB delete(Table.TableEnum table) {
        query.append("DELETE FROM ").append(table.getTableName());
        return this;
    }

    public SQLQB update(Table.TableEnum table) {
        query.append("UPDATE ").append(table.getTableName()).append(" SET ");
        return this;
    }

    public SQLQB set(TableColumn column) {
        query
        .append(column.getColumnName())
        .append(" = ?");
        return this;
    }

    public SQLQB join(Table.TableEnum joinTable, TableColumn... columns) {
        query.append(" JOIN ").append(joinTable.getTableName()).append(" ON ");
        if ((columns.length % 2) != 0) {
            throw new IllegalArgumentException("Column pairs must be even for join conditions.");
        }
        for (int i = 0; i < columns.length; i += 2) {
            query.append(columns[i].getTableEnum().getTableName())
                 .append(".")
                 .append(columns[i].getColumnName())
                 .append(" = ")
                 .append(columns[i + 1].getTableEnum().getTableName())
                 .append(".")
                 .append(columns[i + 1].getColumnName());
            if (i < columns.length - 2) {
                query.append(" AND ");
            }
        }
        return this;
    }

    public SQLQB where(TableColumn column) {
        if (query.toString().contains("WHERE")){
                query.append(column).append(" = ? ");
        }
        else{
            query.append(" WHERE ").append(column).append(" = ?");
        }
        return this;
    }

    public SQLQB and() {
        query.append(" AND ");
        return this;
    }

    public SQLQB is(){
        query.append(" IS ");
        return this;
    }

    public SQLQB NULL(){
        query.append(" NULL ");
        return this;
    }

    public SQLQB or() {
        query.append(" OR ");
        return this;
    }

    public SQLQB condition(TableColumn columnName){
        query.append(columnName);
        return this;
    }

    public SQLQB equalTo(Object value) {
        query.append(" = ").append(value instanceof String ? "'" + value + "'" : value);
        return this;
    }

    public SQLQB lessThan(Object value) {
        query.append(" < ").append(value instanceof String ? "'" + value + "'" : value);
        return this;
    }

    public SQLQB greaterThan(Object value) {
        query.append(" > ").append(value instanceof String ? "'" + value + "'" : value);
        return this;
    }

    public SQLQB GETGENID(){
        query.append(" @uid = LAST_INSERT_ID();");
        return this;
    }

    public SQLQB insert(Table.TableEnum table, TableColumn... columns) {
        query.append(" INSERT INTO ").append(table.getTableName()).append(" (");
        for (int i = 0; i < columns.length; i++) {
            query.append(columns[i].getColumnName());
            if (i < columns.length - 1) {
                query.append(", ");
            }
        }
        query.append(") VALUES (");
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].getColumnName().equals("UID") && columns[i].getTableEnum().toString().equalsIgnoreCase("user_data")){
                query.append("@uid");
            }
            else{
                query.append("?");
            }
            if (i < columns.length - 1) {
                query.append(", ");
            }
        }
        query.append(")");
        return this;
    }

    public String build() {
        query.append(";");
        return this.query.toString().trim();
    }

    @Override
    public String toString() {
        return build();
    }

    public void reset() {
        query.setLength(0);
    }
}





