import 'package:flutter/material.dart';
import '../../models/league_model.dart';
import '../../services/league_service.dart';
import '../../providers/auth_provider.dart';
import 'package:provider/provider.dart';

class LeagueDetailScreen extends StatefulWidget {
  final String leagueId;

  const LeagueDetailScreen({super.key, required this.leagueId});

  @override
  State<LeagueDetailScreen> createState() => _LeagueDetailScreenState();
}

class _LeagueDetailScreenState extends State<LeagueDetailScreen> {
  LeagueModel? _league;
  bool _isLoading = true;
  String? _error;
  final LeagueService _leagueService = LeagueService();

  @override
  void initState() {
    super.initState();
    _loadLeague();
  }

  Future<void> _loadLeague() async {
    try {
      final authProvider = context.read<AuthProvider>();
      _leagueService.setToken(authProvider.token);
      final league = await _leagueService.getLeagueById(widget.leagueId);
      setState(() {
        _league = league;
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _error = e.toString();
        _isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    if (_isLoading) {
      return const Scaffold(
        body: Center(child: CircularProgressIndicator()),
      );
    }

    if (_error != null || _league == null) {
      return Scaffold(
        appBar: AppBar(title: const Text('Detalle de Liga')),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text('Error: ${_error ?? "Liga no encontrada"}'),
              ElevatedButton(
                onPressed: _loadLeague,
                child: const Text('Reintentar'),
              ),
            ],
          ),
        ),
      );
    }

    return Scaffold(
      appBar: AppBar(
        title: Text(_league!.name),
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            if (_league!.description != null) ...[
              Text(
                _league!.description!,
                style: Theme.of(context).textTheme.bodyLarge,
              ),
              const SizedBox(height: 16),
            ],
            Card(
              child: Padding(
                padding: const EdgeInsets.all(16),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Información',
                      style: Theme.of(context).textTheme.titleLarge,
                    ),
                    const SizedBox(height: 8),
                    Row(
                      children: [
                        Icon(
                          _league!.isPublic ? Icons.public : Icons.lock,
                          size: 20,
                        ),
                        const SizedBox(width: 8),
                        Text(_league!.isPublic ? 'Pública' : 'Privada'),
                      ],
                    ),
                    const SizedBox(height: 8),
                    Text(
                      'Participantes: ${_league!.participantIds.length}/${_league!.maxParticipants}',
                    ),
                    const SizedBox(height: 8),
                    Text('Estado: ${_league!.status}'),
                  ],
                ),
              ),
            ),
            if (_league!.invitationCode != null) ...[
              const SizedBox(height: 16),
              Card(
                color: Colors.blue.shade50,
                child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text(
                        'Código de invitación',
                        style: TextStyle(fontWeight: FontWeight.bold),
                      ),
                      const SizedBox(height: 8),
                      SelectableText(
                        _league!.invitationCode!,
                        style: const TextStyle(
                          fontSize: 24,
                          fontWeight: FontWeight.bold,
                          letterSpacing: 2,
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ],
            if (_league!.availableCreatures != null && _league!.availableCreatures!.isNotEmpty) ...[
              const SizedBox(height: 16),
              Text(
                'Criaturas disponibles (${_league!.availableCreatures!.length})',
                style: Theme.of(context).textTheme.titleLarge,
              ),
              const SizedBox(height: 8),
              GridView.builder(
                shrinkWrap: true,
                physics: const NeverScrollableScrollPhysics(),
                gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 2,
                  crossAxisSpacing: 8,
                  mainAxisSpacing: 8,
                  childAspectRatio: 1.5,
                ),
                itemCount: _league!.availableCreatures!.length,
                itemBuilder: (context, index) {
                  final creature = _league!.availableCreatures![index];
                  return Card(
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            creature.name,
                            style: const TextStyle(
                              fontWeight: FontWeight.bold,
                            ),
                            maxLines: 1,
                            overflow: TextOverflow.ellipsis,
                          ),
                          const SizedBox(height: 4),
                          Text(
                            creature.types.join('/'),
                            style: TextStyle(
                              fontSize: 12,
                              color: Colors.grey.shade600,
                            ),
                          ),
                          const Spacer(),
                          Text(
                            '${creature.basePrice.toStringAsFixed(0)} monedas',
                            style: TextStyle(
                              fontSize: 11,
                              color: Colors.grey.shade700,
                            ),
                          ),
                        ],
                      ),
                    ),
                  );
                },
              ),
            ],
            if (_league!.participants != null && _league!.participants!.isNotEmpty) ...[
              const SizedBox(height: 16),
              Text(
                'Participantes',
                style: Theme.of(context).textTheme.titleLarge,
              ),
              const SizedBox(height: 8),
              ..._league!.participants!.map((participant) {
                return ListTile(
                  leading: const CircleAvatar(child: Icon(Icons.person)),
                  title: Text(participant.username),
                  subtitle: Text('${participant.totalPoints} puntos'),
                  trailing: Text('${participant.budget.toStringAsFixed(0)} monedas'),
                );
              }),
            ],
          ],
        ),
      ),
    );
  }
}
