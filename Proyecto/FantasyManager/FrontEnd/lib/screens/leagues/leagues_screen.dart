import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../providers/auth_provider.dart';
import '../../providers/league_provider.dart';
import '../../services/league_service.dart';
import 'create_league_screen.dart';
import 'league_detail_screen.dart';

class LeaguesScreen extends StatefulWidget {
  const LeaguesScreen({super.key});

  @override
  State<LeaguesScreen> createState() => _LeaguesScreenState();
}

class _LeaguesScreenState extends State<LeaguesScreen> {
  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      final authProvider = context.read<AuthProvider>();
      if (authProvider.isAuthenticated) {
        final leagueProvider = context.read<LeagueProvider>();
        leagueProvider.setToken(authProvider.token);
        leagueProvider.loadLeagues();
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Ligas'),
        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: () async {
              await context.read<AuthProvider>().logout();
              if (mounted) {
                Navigator.of(context).pushReplacementNamed('/login');
              }
            },
          ),
        ],
      ),
      body: Consumer<LeagueProvider>(
        builder: (context, provider, child) {
          if (provider.isLoading) {
            return const Center(child: CircularProgressIndicator());
          }

          if (provider.error != null) {
            return Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text('Error: ${provider.error}'),
                  ElevatedButton(
                    onPressed: () => provider.loadLeagues(),
                    child: const Text('Reintentar'),
                  ),
                ],
              ),
            );
          }

          if (provider.leagues.isEmpty) {
            return Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Icon(Icons.group, size: 64, color: Colors.grey),
                  const SizedBox(height: 16),
                  const Text('No hay ligas disponibles'),
                  const SizedBox(height: 8),
                  ElevatedButton(
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (_) => const CreateLeagueScreen(),
                        ),
                      );
                    },
                    child: const Text('Crear Liga'),
                  ),
                ],
              ),
            );
          }

          return RefreshIndicator(
            onRefresh: () => provider.loadLeagues(),
            child: ListView.builder(
              itemCount: provider.leagues.length,
              padding: const EdgeInsets.all(8),
              itemBuilder: (context, index) {
                final league = provider.leagues[index];
                return Card(
                  margin: const EdgeInsets.symmetric(vertical: 4),
                  child: ListTile(
                    leading: Icon(
                      league.isPublic ? Icons.public : Icons.lock,
                      color: league.isPublic ? Colors.green : Colors.orange,
                    ),
                    title: Text(league.name),
                    subtitle: Text(
                      '${league.participantIds.length}/${league.maxParticipants} participantes',
                    ),
                    trailing: const Icon(Icons.arrow_forward_ios),
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (_) => LeagueDetailScreen(leagueId: league.id),
                        ),
                      );
                    },
                  ),
                );
              },
            ),
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.push(
            context,
            MaterialPageRoute(builder: (_) => const CreateLeagueScreen()),
          );
        },
        child: const Icon(Icons.add),
      ),
    );
  }
}
