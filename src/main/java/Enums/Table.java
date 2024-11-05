package Enums;

public class Table {

    // Enum for Table Names
    public enum TableEnum {
        USER_DATA("user_data"),
        CONTACT_DETAIL("contact_detail"),
        UT_EMAIL_ID("ut_email_id"),
        UT_PHONE("ut_phone"),
        CT_PHONE("ct_phone"),
        CT_EMAIL_ID("ct_email_id"),
        CATEGORY("category"),
        CATEGORY_GROUP("category_group"),
        CT_DATE("ct_date"),
        SESSION("session");

        private final String tableName;

        TableEnum(String tableName) {
            this.tableName = tableName;
        }

        public String getTableName() {
            return tableName;
        }
    }

    public enum found implements TableColumn {
        One("one");
        private final String columnName;

        found(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }

        public TableEnum getTableEnum() {
            return TableEnum.USER_DATA;
        }
    }

    public enum user_data implements TableColumn {
        UID("UID"),
        Name("Name"),
        Gender("Gender"),
        Password("Password"),
        Home_Address("Home_Address"),
        Work_Address("Work_Address");

        private final String columnName;

        user_data(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }

        public TableEnum getTableEnum() {
            return TableEnum.USER_DATA;
        }
    }

    public enum ut_email_id implements TableColumn{
        UID("UID"),
        Email_ID("Email_ID"),
        Label("Label"),
        Starred("Starred");

        private final String columnName;

        ut_email_id(String columnName){
            this.columnName = columnName;
        }

        public String getColumnName(){
            return columnName;
        }

        public TableEnum getTableEnum(){
            return TableEnum.UT_EMAIL_ID;
        }

    }

    public enum ut_phone implements TableColumn{
        UID("UID"),
        Phone_No("Phone_Number"),
        Label("Label");
    
        private final String columnName;
    
        ut_phone(String columnName) {
            this.columnName = columnName;

        }
    
        public String getColumnName() {
            return columnName;
        }

        public TableEnum getTableEnum(){
            return TableEnum.CT_PHONE;
        }

    }

    public enum session implements TableColumn{
        UID("UID"),
        Session_ID("Session_ID"),
        Last_Interaction("Last_Interaction"),
        Timeout_Period("Timeout_Period");

        private final String columnName;

        session(String columnName){
            this.columnName = columnName;
        }

        public String getColumnName(){
            return columnName;
        }

        public TableEnum getTableEnum(){
            return TableEnum.SESSION;
        }

    }

    public enum contact_detail implements TableColumn {
        UID("UID"),
        PID("PID"),
        Name("Name"),
        Note("Note"),
        Related_Person("Related_Person"),
        Home_Address("Home_Address"),
        Work_Address("Work_Address");

        private final String columnName;

        contact_detail(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }

        public TableEnum getTableEnum() {
            return TableEnum.CONTACT_DETAIL;
        }
    }


    

    public enum ct_phone implements TableColumn{
        PID("PID"),
        Phone_No("Phone_No"),
        Label("Label");
    
        private final String columnName;
    
        ct_phone(String columnName) {
            this.columnName = columnName;

        }
    
        public String getColumnName() {
            return columnName;
        }

        public TableEnum getTableEnum(){
            return TableEnum.CT_PHONE;
        }

    }

    public enum ct_email_id implements TableColumn{
        PID("PID"),
        Email_ID("Email_ID"),
        Label("Label");
    
        private final String columnName;
    
        ct_email_id(String columnName) {
            this.columnName = columnName;
        }
    
        public String getColumnName() {
            return columnName;
        }

        public TableEnum getTableEnum(){
            return TableEnum.CT_EMAIL_ID;
        }

    }

    public enum category implements TableColumn{
        UID("UID"),
        Category_ID("Category_ID"),
        Category_Name("Category_Name");
    
        private final String columnName;
    
        category(String columnName) {
            this.columnName = columnName;
        }
    
        public String getColumnName() {
            return columnName;
        }
    
        public TableEnum getTableEnum() {
            return TableEnum.CATEGORY;
        }
    }


    public enum category_group implements TableColumn{
        PID("PID"),
        Category_ID("Category_ID");
    
        private final String columnName;
    
        category_group(String columnName) {
            this.columnName = columnName;
        }
    
        public String getColumnName() {
            return columnName;
        }
    
        public TableEnum getTableEnum() {
            return TableEnum.CATEGORY_GROUP;
        }
    }
    

    public enum ct_date implements TableColumn{
        PID("PID"),
        Date("Date"),
        Label("Label");
    
        private final String columnName;
    
        ct_date(String columnName) {
            this.columnName = columnName;
        }
    
        public String getColumnName() {
            return columnName;
        }
    
        public Table.TableEnum getTableEnum() {
            return Table.TableEnum.CT_DATE;
        }
    }

    

    
}
