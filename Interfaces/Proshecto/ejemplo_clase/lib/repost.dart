import 'dart:convert';
import 'package:http/http.dart' as http;

class RepPost {
  static const _base = 'https://pokeapi.co/api/v2/';

  Future<List<dynamic>> getData() async {
    final response = await http.get(Uri.parse('$_base/pokemon?limit=1025'));
    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      return data['results'];
    } else {
      throw Exception('Failed to load data');
    }
  }
}
