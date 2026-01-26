import 'dart:convert';
import 'package:http/http.dart' as http;
import '../config/api_config.dart';

class ApiService {
  final String baseUrl;

  ApiService({String? baseUrl})
      : baseUrl = baseUrl ?? ApiConfig.apiBaseUrl;

  Future<Map<String, String>> _getHeaders({Map<String, String>? customHeaders}) async {
    final headers = {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    };
    if (customHeaders != null) {
      headers.addAll(customHeaders);
    }
    return headers;
  }

  Future<http.Response> _handleRequest(
    Future<http.Response> Function() request,
  ) async {
    try {
      final response = await request();
      return response;
    } catch (e) {
      throw Exception('Error de conexión: $e');
    }
  }

  // GET request
  Future<http.Response> get(String endpoint, {Map<String, String>? headers}) async {
    return _handleRequest(() async {
      final uri = Uri.parse('$baseUrl$endpoint');
      return await http.get(
        uri,
        headers: await _getHeaders(customHeaders: headers),
      ).timeout(ApiConfig.connectTimeout);
    });
  }

  // POST request
  Future<http.Response> post(String endpoint, Map<String, dynamic> body, {Map<String, String>? headers}) async {
    return _handleRequest(() async {
      final uri = Uri.parse('$baseUrl$endpoint');
      return await http.post(
        uri,
        headers: await _getHeaders(customHeaders: headers),
        body: jsonEncode(body),
      ).timeout(ApiConfig.connectTimeout);
    });
  }

  // PUT request
  Future<http.Response> put(String endpoint, Map<String, dynamic> body, {Map<String, String>? headers}) async {
    return _handleRequest(() async {
      final uri = Uri.parse('$baseUrl$endpoint');
      return await http.put(
        uri,
        headers: await _getHeaders(customHeaders: headers),
        body: jsonEncode(body),
      ).timeout(ApiConfig.connectTimeout);
    });
  }

  // DELETE request
  Future<http.Response> delete(String endpoint, {Map<String, String>? headers}) async {
    return _handleRequest(() async {
      final uri = Uri.parse('$baseUrl$endpoint');
      return await http.delete(
        uri,
        headers: await _getHeaders(customHeaders: headers),
      ).timeout(ApiConfig.connectTimeout);
    });
  }
}
