import 'dart:convert';
import '../config/api_config.dart';
import '../models/league_model.dart';
import '../models/creature_model.dart';
import 'api_service.dart';

class LeagueService {
  final ApiService _apiService = ApiService();

  String? _token;

  void setToken(String? token) {
    _token = token;
  }

  Map<String, String>? get _headers {
    if (_token == null) return null;
    return {'Authorization': 'Bearer $_token'};
  }

  Future<List<LeagueModel>> getLeagues({bool? public, String? search}) async {
    try {
      final queryParams = <String>[];
      if (public != null) queryParams.add('public=$public');
      if (search != null && search.isNotEmpty) queryParams.add('search=$search');
      
      final endpoint = '/leagues${queryParams.isNotEmpty ? '?${queryParams.join('&')}' : ''}';
      final response = await _apiService.get(endpoint, headers: _headers);

      if (response.statusCode == 200) {
        final List<dynamic> data = jsonDecode(response.body);
        return data.map((json) => LeagueModel.fromJson(json)).toList();
      }
      return [];
    } catch (e) {
      throw Exception('Error al obtener ligas: $e');
    }
  }

  Future<LeagueModel> getLeagueById(String id) async {
    try {
      final response = await _apiService.get('/leagues/$id', headers: _headers);

      if (response.statusCode == 200) {
        return LeagueModel.fromJson(jsonDecode(response.body));
      }
      throw Exception('Liga no encontrada');
    } catch (e) {
      throw Exception('Error al obtener liga: $e');
    }
  }

  Future<LeagueModel> createLeague(LeagueModel league) async {
    try {
      final response = await _apiService.post(
        '/leagues',
        league.toJson(),
        headers: _headers,
      );

      if (response.statusCode == 201) {
        return LeagueModel.fromJson(jsonDecode(response.body));
      }
      final error = jsonDecode(response.body);
      throw Exception(error['error'] ?? 'Error al crear liga');
    } catch (e) {
      throw Exception('Error al crear liga: $e');
    }
  }

  Future<LeagueModel> createLeagueWithCreatures(
    LeagueModel league,
    List<CreatureModel> creatures,
  ) async {
    try {
      final body = league.toJson();
      body['newCreatures'] = creatures.map((c) => c.toJson()).toList();
      
      final response = await _apiService.post(
        '/leagues',
        body,
        headers: _headers,
      );

      if (response.statusCode == 201) {
        return LeagueModel.fromJson(jsonDecode(response.body));
      }
      final error = jsonDecode(response.body);
      throw Exception(error['error'] ?? 'Error al crear liga');
    } catch (e) {
      throw Exception('Error al crear liga: $e');
    }
  }

  Future<LeagueModel> joinLeague(String code) async {
    try {
      final response = await _apiService.post(
        '/leagues/join',
        {'code': code},
        headers: _headers,
      );

      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        return LeagueModel.fromJson(data['league']);
      }
      final error = jsonDecode(response.body);
      throw Exception(error['error'] ?? 'Error al unirse a la liga');
    } catch (e) {
      throw Exception('Error al unirse a la liga: $e');
    }
  }
}
