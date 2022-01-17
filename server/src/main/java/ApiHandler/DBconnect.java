package ApiHandler;
//import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import io.javalin.http.Context;
import org.json.JSONObject;
import org.sqlite.SQLiteException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DBconnect {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    public Connection connection() throws SQLException, IOException {
        try {
        return DriverManager
                .getConnection("jdbc:sqlite:.src/main/resources/sql/quotes.db");
        }catch (SQLException e){
            File data = new File(".src/main/resources/sql/");
            
            data.mkdirs();
            data = new File(".src/main/resources/sql/quotes.db");
            data.createNewFile();


            return DriverManager
                .getConnection("jdbc:sqlite:.src/main/resources/sql/quotes.db");

        }
    }


    public ResultSet readDataBase(
    ) throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("org.sqlite.JDBC");
            // Setup the connection with the DB
            connect = connection();

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            try{
                ResultSet stuff = statement.executeQuery("select * from quotes;");
                return stuff;
            }catch (SQLiteException e){
                statement.executeUpdate("CREATE TABLE quotes(author varchar (30),quote varchar (120) not null);");

                ResultSet stuff = statement.executeQuery("select * from quotes;");
                return stuff;
            }
//            connect.close();

            // return stuff;
//

        } catch (Exception e) {
            throw e;
        } finally {
//            close();
        }

    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
    public void writeDatabase(Quote quote) throws SQLException, IOException {
        Connection connection = connection();
        // INSERT INTO comments values (default, 'lars', 'myemail@gmail.com','https://www.vogella.com/', '2009-09-14 10:33:11', 'Summary','My first comment' );

        statement = connection.createStatement();
        System.out.println(quote.getAuthor());
        System.out.println(quote.getQuote());

        statement.executeUpdate("insert into quotes(author,quote) values ("
                + "\"" + quote.getAuthor()+"\","
                + "\"" + quote.getQuote() + "\""
                + ")"
        );
//        System.out.println(ticket.getCategory());
        connection.close();
    }

    public void updateDatabase(Context context) throws SQLException, IOException {
        Connection connection = connection();
        String status = context.pathParam("status");
        String id = context.pathParam("id");
        // INSERT INTO comments values (default, 'lars', 'myemail@gmail.com','https://www.vogella.com/', '2009-09-14 10:33:11', 'Summary','My first comment' );

        statement = connection.createStatement();
        System.out.println("update tickets set completed="+status+" where id="+id);
        statement.executeUpdate("update tickets set completed='"+status.toUpperCase()+"' where id='"+id+"'");

        context.json("Ticket "+id+":\n has been updated to "+status);
        connection.close();
    }

    public void resetCounter(int id, Context context) throws SQLException, IOException {
        Connection connection = connection();
        // INSERT INTO comments values (default, 'lars', 'myemail@gmail.com','https://www.vogella.com/', '2009-09-14 10:33:11', 'Summary','My first comment' );
        JSONObject query = new JSONObject(context.body());

        statement = connection.createStatement();
        if (query.get("password").equals("beresponsibleWithApis@2015")) {
            statement.executeUpdate("delete from quotes");
            statement.executeUpdate("alter table quotes auto_increment = " + id);
            connection.close();
            context.json("Deleted Data and Reset Table");
        }
        else {
            context.json("Password Incorrect");
        }
    }

    public void createTables(Context context) throws SQLException, IOException {

        Connection connection = connection();
//        String id = context.pathParam("id");
        context.json("Creating Tables...");

        statement = connection.createStatement();
        try {
            statement.executeUpdate("create table quotes (id INT ," +
                    "author VARCHAR(30) NOT NULL," +
                    "quote VARCHAR(120) NOT NULL);"
            );
            System.out.println("create table quotes (id INT NOT NULL AUTOINCREMENT," +
                    "author VARCHAR(30) NOT NULL," +
                    "quote VARCHAR(120) NOT NULL);"
            );
        }
        catch (SQLiteException e){
            context.json("Table needs to be dropped first");
        }System.out.println("create table quotes (id INT NOT NULL AUTOINCREMENT," +
                "author VARCHAR(30) NOT NULL," +
                "quote VARCHAR(120) NOT NULL)"
        );




        context.json("Created Tables...");
        connection.close();
    }

    public void dropTables(Context context) throws SQLException, IOException {
        Connection connection = connection();
        // INSERT INTO comments values (default, 'lars', 'myemail@gmail.com','https://www.vogella.com/', '2009-09-14 10:33:11', 'Summary','My first comment' );

        statement = connection.createStatement();
        try {
            context.json("Dropping Tables...");
            statement.executeUpdate("drop table quotes");
            context.json("Dropping Tables...");
        }catch (Exception e) {
            createTables(context);
        }
        createTables(context);
        connection.close();
    }
}
