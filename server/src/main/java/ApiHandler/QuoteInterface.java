package ApiHandler;

import io.javalin.http.Context;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface QuoteInterface {
//    String quote = null;
//    String author = null;
    
    List<HashMap<String,String>> getAllQuotes(Context context) throws Exception;

    void setQuote(Context context) throws SQLException, IOException;
    String getQuote();

    void setAuthor(Context context);
    String getAuthor();

}
