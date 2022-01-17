import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:qute_quotes/models/quote.dart';
import 'package:qute_quotes/models/quotes.dart';

String ip = "102.221.36.216";
String port = "5000";
final quotesModel = Quotes();

// Future<List<Map>> getListOfQuotes() {
Future<Quotes> getListOfQuotes() async {
  quotesModel.clear();

  Uri uri = Uri.parse("http://$ip:$port/quotes");
  http.Response response = await http.get(uri);

  List data = jsonDecode(response.body);

  for (var item in data) {
    print(item);
    quotesModel.add(Quote.fromJson(item));
  }

  return quotesModel;
}

void sendQuotes(String author, String quote) {
  Uri url = Uri.parse("http://$ip:$port/quote");
  var res = http.post(url,
      body: jsonEncode(<String, String>{"author": author, "quote": quote}));
}
