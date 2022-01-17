import 'package:flutter/widgets.dart';

class Quote extends ChangeNotifier {
  final String quote;
  final String author;
  final List<Quote> quotes = [];

  Quote(this.quote, this.author) {
    add();
  }

  factory Quote.fromJson(Map json) {
    return Quote(json['quote'], json['author']);
  }

  void add() {
    quotes.add(this);
    notifyListeners();
  }
}
