import ApiHandler.DBconnect;
import ApiHandler.Quote;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;

import io.javalin.http.Context;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;


public class ApiServer {

    public static void main(String[] args) {
        int port = 5000;
        Javalin server = Javalin.create(JavalinConfig::enableCorsForAllOrigins).start(port);

        server.post("/quote", ApiServer::addQuote);
        server.get("/quotes", ApiServer::getQuotes);
        server.post("/create", ApiServer::createTables);
        server.post("/drop", ApiServer::dropTables);
    }

    private static void addQuote(Context context) throws SQLException, IOException {
        Quote quote = new Quote();

        quote.setQuote(context);

        context.json(quote.getQuote()+" : Saved into the database");
    }


    private static void getQuotes(Context context) throws Exception {
        DBconnect dBconnect = new DBconnect();
        ResultSet DbData = dBconnect.readDataBase();
        List<HashMap<String,String>> data = new ArrayList<>();

        while (DbData.next()){
            String author = DbData.getString(1);
            String quote = DbData.getString(2);
            data.add(serialize(author,quote));
        }

        context.json(data);

    }

    private static void createTables(Context context) throws SQLException, IOException {
        DBconnect dBconnect = new DBconnect();
        dBconnect.createTables(context);
    }

    private static void dropTables(Context context) throws SQLException, IOException {
        DBconnect dBconnect = new DBconnect();
        dBconnect.dropTables(context);
    }


    private static HashMap<String,String> serialize(String author,String quote){
        HashMap<String,String> data = new HashMap<>();
        data.put("author",author);
        data.put("quote",quote);

        return data;

    }
}