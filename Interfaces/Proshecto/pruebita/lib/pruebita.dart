import 'dart:convert';
import 'package:http/http.dart' as http;

int calculate() {
  return 6 * 7;
}

// https://jsonplaceholder.typicode.com/posts

Future<void> main() async {
  final response = await http.get(
    Uri.parse('https://jsonplaceholder.typicode.com/posts'),
  );
  print(jsonDecode(response.body));
}

Future<List<dynamic>> fetchPosts() async {
  final response = await http.get(
    Uri.parse('https://jsonplaceholder.typicode.com/posts'),
  );
  return jsonDecode(response.body) as List<dynamic>;
}
