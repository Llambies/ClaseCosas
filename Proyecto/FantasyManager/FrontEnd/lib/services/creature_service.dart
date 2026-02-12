import 'dart:convert';
import '../config/api_config.dart';
import '../models/creature_model.dart';
import 'api_service.dart';

class CreatureService {
  final ApiService _apiService = ApiService();

  Future<List<CreatureModel>> getCreatures({String? search, String? type}) async {
    try {
      final queryParams = <String>[];
      if (search != null && search.isNotEmpty) queryParams.add('search=$search');
      if (type != null && type.isNotEmpty) queryParams.add('type=$type');
      
      final endpoint = '/creatures${queryParams.isNotEmpty ? '?${queryParams.join('&')}' : ''}';
      final response = await _apiService.get(endpoint);

      if (response.statusCode == 200) {
        final List<dynamic> data = jsonDecode(response.body);
        return data.map((json) => CreatureModel.fromJson(json)).toList();
      }
      return [];
    } catch (e) {
      throw Exception('Error al obtener criaturas: $e');
    }
  }

  Future<CreatureModel> getCreatureById(String id) async {
    try {
      final response = await _apiService.get('/creatures/$id');

      if (response.statusCode == 200) {
        return CreatureModel.fromJson(jsonDecode(response.body));
      }
      throw Exception('Criatura no encontrada');
    } catch (e) {
      throw Exception('Error al obtener criatura: $e');
    }
  }
}
