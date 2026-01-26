import 'package:flutter/foundation.dart';
import '../models/league_model.dart';
import '../models/creature_model.dart';
import '../services/league_service.dart';

class LeagueProvider with ChangeNotifier {
  final LeagueService _leagueService = LeagueService();
  
  List<LeagueModel> _leagues = [];
  bool _isLoading = false;
  String? _error;

  List<LeagueModel> get leagues => _leagues;
  bool get isLoading => _isLoading;
  String? get error => _error;

  void setToken(String? token) {
    _leagueService.setToken(token);
  }

  Future<void> loadLeagues({bool? public}) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      _leagues = await _leagueService.getLeagues(public: public);
      _error = null;
    } catch (e) {
      _error = e.toString();
      _leagues = [];
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<bool> createLeague(LeagueModel league) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      final newLeague = await _leagueService.createLeague(league);
      _leagues.add(newLeague);
      _error = null;
      _isLoading = false;
      notifyListeners();
      return true;
    } catch (e) {
      _error = e.toString();
      _isLoading = false;
      notifyListeners();
      return false;
    }
  }

  Future<bool> createLeagueWithCreatures(
    LeagueModel league,
    List<CreatureModel> creatures,
  ) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      final newLeague = await _leagueService.createLeagueWithCreatures(league, creatures);
      _leagues.add(newLeague);
      _error = null;
      _isLoading = false;
      notifyListeners();
      return true;
    } catch (e) {
      _error = e.toString();
      _isLoading = false;
      notifyListeners();
      return false;
    }
  }

  Future<bool> joinLeague(String code) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      final league = await _leagueService.joinLeague(code);
      _leagues.add(league);
      _error = null;
      _isLoading = false;
      notifyListeners();
      return true;
    } catch (e) {
      _error = e.toString();
      _isLoading = false;
      notifyListeners();
      return false;
    }
  }
}
