import 'package:flutter/widgets.dart';

import 'quote.dart';

class Quotes extends ChangeNotifier {
  final List<Quote> quotes = [];

  // Quotes() {

  // }

  void add(Quote quote) {
    quotes.add(quote);
    notifyListeners();
  }

  void clear() {
    quotes.clear();
    notifyListeners();
  }
}
