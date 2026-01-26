import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../models/league_model.dart';
import '../../models/creature_model.dart';
import '../../providers/league_provider.dart';
import '../../widgets/creature_form_widget.dart';
import 'leagues_screen.dart';

class CreateLeagueScreen extends StatefulWidget {
  const CreateLeagueScreen({super.key});

  @override
  State<CreateLeagueScreen> createState() => _CreateLeagueScreenState();
}

class _CreateLeagueScreenState extends State<CreateLeagueScreen> {
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  final _descriptionController = TextEditingController();
  bool _isPublic = true;
  int _maxParticipants = 20;
  List<CreatureModel> _createdCreatures = [];

  @override
  void dispose() {
    _nameController.dispose();
    _descriptionController.dispose();
    super.dispose();
  }

  void _addCreature(CreatureModel creature) {
    setState(() {
      _createdCreatures.add(creature);
    });
    Navigator.pop(context);
  }

  void _removeCreature(int index) {
    setState(() {
      _createdCreatures.removeAt(index);
    });
  }

  void _editCreature(int index) {
    final creature = _createdCreatures[index];
    _showCreatureForm(creature: creature, index: index);
  }

  void _showCreatureForm({CreatureModel? creature, int? index}) {
    showDialog(
      context: context,
      builder: (context) => Dialog(
        child: SizedBox(
          width: MediaQuery.of(context).size.width * 0.9,
          height: MediaQuery.of(context).size.height * 0.9,
          child: Column(
            children: [
              AppBar(
                title: Text(creature == null ? 'Nueva Criatura' : 'Editar Criatura'),
                automaticallyImplyLeading: false,
                actions: [
                  IconButton(
                    icon: const Icon(Icons.close),
                    onPressed: () => Navigator.pop(context),
                  ),
                ],
              ),
              Expanded(
                child: CreatureFormWidget(
                  initialCreature: creature,
                  onSave: (savedCreature) {
                    if (index != null) {
                      setState(() {
                        _createdCreatures[index] = savedCreature;
                      });
                    } else {
                      _addCreature(savedCreature);
                    }
                  },
                  onCancel: () => Navigator.pop(context),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Future<void> _createLeague() async {
    if (_formKey.currentState!.validate()) {
      if (_createdCreatures.isEmpty) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Debes crear al menos una criatura'),
            backgroundColor: Colors.orange,
          ),
        );
        return;
      }

      final league = LeagueModel(
        id: '',
        name: _nameController.text.trim(),
        description: _descriptionController.text.trim().isEmpty
            ? null
            : _descriptionController.text.trim(),
        creatorId: '',
        isPublic: _isPublic,
        maxParticipants: _maxParticipants,
        participantIds: [],
        status: 'draft',
        availableCreatureIds: [],
      );

      final provider = context.read<LeagueProvider>();
      final success = await provider.createLeagueWithCreatures(
        league,
        _createdCreatures,
      );

      if (mounted) {
        if (success) {
          Navigator.of(context).pushReplacement(
            MaterialPageRoute(builder: (_) => const LeaguesScreen()),
          );
        } else {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text(provider.error ?? 'Error al crear liga'),
              backgroundColor: Colors.red,
            ),
          );
        }
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Crear Liga')),
      body: Form(
        key: _formKey,
        child: ListView(
          padding: const EdgeInsets.all(16),
          children: [
            TextFormField(
              controller: _nameController,
              decoration: const InputDecoration(
                labelText: 'Nombre de la liga',
                border: OutlineInputBorder(),
              ),
              validator: (value) {
                if (value == null || value.isEmpty) {
                  return 'Por favor ingresa un nombre';
                }
                return null;
              },
            ),
            const SizedBox(height: 16),
            TextFormField(
              controller: _descriptionController,
              decoration: const InputDecoration(
                labelText: 'Descripción (opcional)',
                border: OutlineInputBorder(),
              ),
              maxLines: 3,
            ),
            const SizedBox(height: 16),
            SwitchListTile(
              title: const Text('Liga pública'),
              subtitle: const Text('Cualquiera puede unirse'),
              value: _isPublic,
              onChanged: (value) {
                setState(() {
                  _isPublic = value;
                });
              },
            ),
            const SizedBox(height: 16),
            ListTile(
              title: const Text('Máximo de participantes'),
              subtitle: Slider(
                value: _maxParticipants.toDouble(),
                min: 2,
                max: 50,
                divisions: 48,
                label: _maxParticipants.toString(),
                onChanged: (value) {
                  setState(() {
                    _maxParticipants = value.toInt();
                  });
                },
              ),
              trailing: Text('$_maxParticipants'),
            ),
            const SizedBox(height: 24),
            const Divider(),
            const SizedBox(height: 8),
            Row(
              children: [
                const Text(
                  'Criaturas de la liga',
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
                const Spacer(),
                Text(
                  '${_createdCreatures.length} criaturas',
                  style: TextStyle(
                    color: _createdCreatures.isEmpty ? Colors.red : Colors.green,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 8),
            ElevatedButton.icon(
              onPressed: () => _showCreatureForm(),
              icon: const Icon(Icons.add),
              label: const Text('Agregar Criatura'),
            ),
            const SizedBox(height: 16),
            if (_createdCreatures.isEmpty)
              Card(
                color: Colors.orange.shade50,
                child: const Padding(
                  padding: EdgeInsets.all(16),
                  child: Text(
                    'No hay criaturas creadas. Debes crear al menos una criatura para la liga.',
                    style: TextStyle(color: Colors.orange),
                  ),
                ),
              )
            else
              ...List.generate(_createdCreatures.length, (index) {
                final creature = _createdCreatures[index];
                return Card(
                  margin: const EdgeInsets.symmetric(vertical: 4),
                  child: ListTile(
                    leading: CircleAvatar(
                      child: Text(creature.name[0].toUpperCase()),
                    ),
                    title: Text(creature.name),
                    subtitle: Text(
                      '${creature.types.join("/")} - ${creature.basePrice.toStringAsFixed(0)} monedas',
                    ),
                    trailing: Row(
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        IconButton(
                          icon: const Icon(Icons.edit),
                          onPressed: () => _editCreature(index),
                        ),
                        IconButton(
                          icon: const Icon(Icons.delete, color: Colors.red),
                          onPressed: () => _removeCreature(index),
                        ),
                      ],
                    ),
                  ),
                );
              }),
            const SizedBox(height: 24),
            Consumer<LeagueProvider>(
              builder: (context, provider, child) {
                return ElevatedButton(
                  onPressed: provider.isLoading ? null : _createLeague,
                  style: ElevatedButton.styleFrom(
                    padding: const EdgeInsets.symmetric(vertical: 16),
                  ),
                  child: provider.isLoading
                      ? const SizedBox(
                          height: 20,
                          width: 20,
                          child: CircularProgressIndicator(strokeWidth: 2),
                        )
                      : const Text('Crear Liga'),
                );
              },
            ),
          ],
        ),
      ),
    );
  }
}
