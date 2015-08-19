This project is a demo of using an H2 database engine with Groovy in Android. 
Among the benefits shown here are: 
* better data types (DATE is mapped automatically to java.sql.Date, DECIMAL is mapped to java.math.BigDecimal)
* ability to create multiple tables in one statement (unlike android.database.sqlite.SQLiteDatabase)
* convenience of groovy.sql.Sql (rows are returned in map form, allowing access to the result of a SQL query by the name of the column, or by the column number)
