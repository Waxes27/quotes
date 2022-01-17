package ApiHandler;

import io.javalin.http.Context;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Quote implements QuoteInterface{

    private String quote;
    private String author;


    @Override
    public List<HashMap<String, String>> getAllQuotes(Context context) throws Exception {
        DBconnect dBconnect = new DBconnect();
        List<HashMap<String,String>> quotes = new ArrayList<>();
        ResultSet data = dBconnect.readDataBase();

        return quotes;
    }

    @Override
    public void setQuote(Context context) throws SQLException, IOException {
        JSONObject quoteJson = new JSONObject(context.body());
        this.quote = quoteJson.get("quote").toString();
        this.author = quoteJson.get("author").toString();

        DBconnect dBconnect = new DBconnect();
        dBconnect.writeDatabase(this);
    }

    @Override
    public String getQuote() {
        return this.quote;
    }

    @Override
    public void setAuthor(Context context) {
        JSONObject request = context.bodyAsClass(JSONObject.class);
        this.author = request.get("author").toString();
    }

    @Override
    public String getAuthor() {
        return this.author;
    }
}
