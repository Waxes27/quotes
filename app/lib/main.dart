import 'dart:io';

import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'dart:convert';

import 'package:qute_quotes/api/data.dart';
import 'package:qute_quotes/models/quote.dart';

import 'models/quotes.dart';

void main() {
  runApp(ChangeNotifierProvider(
    create: (context) => Quotes(),
    child: App(),
  ));
}

class App extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: HomePage(),
    );
  }
}

/*

 Home page.dart will come from here

*/

class HomePage extends StatefulWidget {
  @override
  HomePageState createState() => HomePageState();
}

class HomePageState extends State<HomePage> {

  void _goHome() {
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (BuildContext context) {
      return home(context);
    }));
  }

  void _goToAddQuote() {
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (BuildContext context) {
      return addQuote(context);
    }));
  }

  void _goToFullscreenQuote(String author, String quote) {
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (BuildContext context) {
      return _fullscreenQuote(context, author, quote);
    }));
  }

  @mustCallSuper
  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Center(child: Text("Qute Quotes")),
      ),
      body: _buildList(context),
      floatingActionButton: FloatingActionButton(
        child: const Icon(Icons.plus_one_rounded),
        onPressed: () {
          _goToAddQuote();
        },
      ),
    );
  }

  Widget home(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Center(child: Text("Qute Quotes")),
      ),
      body: _buildList(context),
      floatingActionButton: FloatingActionButton(
        child: const Icon(Icons.plus_one_rounded),
        onPressed: () {
          _goToAddQuote();
          dispose();
        },
      ),
    );
  }

  Widget _buildList(BuildContext context) {
    return Consumer<Quotes>(builder: (BuildContext context,Quotes quotes,child){

    
    return FutureBuilder(
        future: getListOfQuotes(),
        builder: (BuildContext context, AsyncSnapshot<Quotes> snapshot) {
          if (snapshot.hasData && snapshot.data!.quotes.isNotEmpty) {
            print("DATA: ${snapshot.data!.quotes}");
            return ListView.builder(
                itemCount: snapshot.data!.quotes.length,
                itemBuilder: (BuildContext context, number) {
                  return Center(
                      child: _buildRow(context, quotesModel.quotes[number]));
                });
          } else {
            return Center(
                child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: const <Widget>[
                CircularProgressIndicator.adaptive(),
                Padding(padding: EdgeInsets.all(50)),
                Text(
                    "No data found yet, Try refreshing or adding a quote with the button in the bottom right ;).")
              ],
            ));
          }
        });
        });
  }

  Widget _buildRow(BuildContext context, Quote quote) {
    return Card(
      child: Column(
        children: <Widget>[
          const Padding(padding: EdgeInsets.symmetric(vertical: 10.0)),
          ListTile(
            onTap: () {
              _goToFullscreenQuote(quote.author, quote.quote);
            },
            leading: const Icon(Icons.book),
            title: Text(quote.quote),
            subtitle: Text("written by: ${quote.author}"),
            trailing: const Icon(Icons.catching_pokemon),
          ),
        ],
      ),
    );
  }

  Widget _fullscreenQuote(BuildContext context, String author, String quote) {
    return Scaffold(
      appBar: AppBar(
        title: const Center(child: Text("Qute Quotes")),
      ),
      body: Center(
        child: Column(
          children: <Widget>[
            const Padding(padding: EdgeInsets.all(50)),
            Center(
                child: Text(
              quote,
              style: TextStyle(fontSize: 30, color: Colors.blue[700]),
            )),
            const Padding(padding: EdgeInsets.all(100)),
            Center(
                child: Text(
              "-$author",
              style: TextStyle(fontSize: 20, color: Colors.blue[300]),
            )),
          ],
        ),
      ),
    );
  }

  Widget addQuote(BuildContext context) {
    String author = '';
    String quote = "";
    return Scaffold(
      appBar: AppBar(
        title: const Center(child: Text("Qute Quotes")),
      ),
      body: Center(
        child: Column(
          children: <Widget>[
            Padding(
                padding: const EdgeInsets.all(24.0),
                child: TextField(
                  decoration: const InputDecoration(hintText: "Author"),
                  onChanged: (text) {
                    author = text;
                    print(author);
                  },
                )),
            Padding(
              padding: const EdgeInsets.all(24.0),
              child: TextField(
                decoration: const InputDecoration(
                    hintText: "Enter Author's quote here"),
                onChanged: (text) {
                  quote = text;
                  print(quote);
                },
              ),
            ),
            Padding(
              padding: const EdgeInsets.symmetric(vertical: 50.0),
              child: ElevatedButton(
                  onPressed: () {
                    sendQuotes(author, quote);
                    print("Call to the API to add quote to database");
                    Navigator.of(context).popUntil((route) => route.isFirst);
                    _goHome();
                  },
                  child: const Text("Submit Here")),
            )
          ],
        ),
      ),
    );
  }
}
