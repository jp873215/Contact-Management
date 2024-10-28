package com.ex.enums;

import java.util.ArrayList;
import java.util.List;

public class Table {

    public enum TableEnum {
        USER_DATA("user_data"),
        UT_EMAIL_ID("ut_email_id"),
        UT_PHONE("ut_phone"),
        CATEGORY("category"),
        CATEGORY_GROUP("category_group");

        private final String tableName;

        TableEnum(String tableName) {
            this.tableName = tableName;
        }

        public String getTableName() {
            return tableName;
        }
    }

    public enum ColumnEnum {
        NAME("Name"),
        UID("UID"),
        GENDER("Gender"),
        PASSWORD("Password"),
        HOME_ADDRESS("Home_Address"),
        WORK_ADDRESS("Work_Address"),
        EMAIL_ID("Email_ID"),
        LABEL("Label"),
        PHONE_NUMBER("Phone_Number"),
        CATEGORY_ID("Category_ID"),
        CATEGORY_NAME("Category_Name"),
        PID("PID"),
        STARRED("Starred");

        private final String columnName;

        ColumnEnum(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }
    }

    public static List<String> getColumnNames(ColumnEnum... columns) {
        List<String> columnNames = new ArrayList<>();
        for (ColumnEnum column : columns) {
            columnNames.add(column.getColumnName());
        }
        return columnNames;
    }

    public static List<String> getAllTableNames() {
        List<String> tableNames = new ArrayList<>();
        for (TableEnum table : TableEnum.values()) {
            tableNames.add(table.getTableName());
        }
        return tableNames;
    }
}
