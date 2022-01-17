class User {
  final int id;
  String name, email, token;

  User(this.id, this.name, this.email, this.token);

  // User.fromJson(dynamic json) : this.id = json['id'] {
  //   this.name = json['name'];
  //   this.email = json['email'];
  //   this.token = json['token'];
  // }

  dynamic toJson() => {'id': id, 'name': name, 'email': email, 'token': token};

  @override
  String toString() {
    return toJson().toString();
  }
}
