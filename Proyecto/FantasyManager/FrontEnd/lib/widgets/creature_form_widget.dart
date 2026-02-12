import 'package:flutter/material.dart';
import '../models/creature_model.dart';

class CreatureFormWidget extends StatefulWidget {
  final CreatureModel? initialCreature;
  final Function(CreatureModel) onSave;
  final VoidCallback? onCancel;

  const CreatureFormWidget({
    super.key,
    this.initialCreature,
    required this.onSave,
    this.onCancel,
  });

  @override
  State<CreatureFormWidget> createState() => _CreatureFormWidgetState();
}

class _CreatureFormWidgetState extends State<CreatureFormWidget> {
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  final _type1Controller = TextEditingController();
  final _type2Controller = TextEditingController();
  final _hpController = TextEditingController();
  final _attackController = TextEditingController();
  final _defenseController = TextEditingController();
  final _spAttackController = TextEditingController();
  final _spDefenseController = TextEditingController();
  final _speedController = TextEditingController();
  final _basePriceController = TextEditingController();
  final _frontSpriteController = TextEditingController();
  final _backSpriteController = TextEditingController();
  final _frontShinyController = TextEditingController();
  final _backShinyController = TextEditingController();
  final _imageUrlController = TextEditingController();

  List<Move> _moves = [];
  List<String> _abilities = [];
  final _moveNameController = TextEditingController();
  final _moveTypeController = TextEditingController();
  final _movePowerController = TextEditingController();
  final _moveAccuracyController = TextEditingController();
  final _movePpController = TextEditingController();
  final _abilityController = TextEditingController();

  @override
  void initState() {
    super.initState();
    if (widget.initialCreature != null) {
      _loadCreatureData(widget.initialCreature!);
    } else {
      _setDefaults();
    }
  }

  void _setDefaults() {
    _hpController.text = '100';
    _attackController.text = '50';
    _defenseController.text = '50';
    _spAttackController.text = '50';
    _spDefenseController.text = '50';
    _speedController.text = '50';
    _basePriceController.text = '1000';
    _moveAccuracyController.text = '100';
    _movePpController.text = '5';
  }

  void _loadCreatureData(CreatureModel creature) {
    _nameController.text = creature.name;
    _type1Controller.text = creature.types.isNotEmpty ? creature.types.first : '';
    _type2Controller.text = creature.types.length > 1 ? creature.types[1] : '';
    _hpController.text = creature.baseStats.hp.toString();
    _attackController.text = creature.baseStats.attack.toString();
    _defenseController.text = creature.baseStats.defense.toString();
    _spAttackController.text = creature.baseStats.spAttack.toString();
    _spDefenseController.text = creature.baseStats.spDefense.toString();
    _speedController.text = creature.baseStats.speed.toString();
    _basePriceController.text = creature.basePrice.toStringAsFixed(0);
    _moves = List.from(creature.moves);
    _abilities = List.from(creature.abilities);
    _frontSpriteController.text = creature.sprites.front;
    _backSpriteController.text = creature.sprites.back;
    _frontShinyController.text = creature.sprites.frontShiny;
    _backShinyController.text = creature.sprites.backShiny;
    _imageUrlController.text = creature.imageUrl ?? '';
  }

  @override
  void dispose() {
    _nameController.dispose();
    _type1Controller.dispose();
    _type2Controller.dispose();
    _hpController.dispose();
    _attackController.dispose();
    _defenseController.dispose();
    _spAttackController.dispose();
    _spDefenseController.dispose();
    _speedController.dispose();
    _basePriceController.dispose();
    _frontSpriteController.dispose();
    _backSpriteController.dispose();
    _frontShinyController.dispose();
    _backShinyController.dispose();
    _imageUrlController.dispose();
    _moveNameController.dispose();
    _moveTypeController.dispose();
    _movePowerController.dispose();
    _moveAccuracyController.dispose();
    _movePpController.dispose();
    _abilityController.dispose();
    super.dispose();
  }

  void _addMove() {
    if (_moveNameController.text.isNotEmpty && _moveTypeController.text.isNotEmpty) {
      setState(() {
        _moves.add(Move(
          name: _moveNameController.text.trim(),
          type: _moveTypeController.text.trim(),
          power: int.tryParse(_movePowerController.text) ?? 0,
          accuracy: int.tryParse(_moveAccuracyController.text) ?? 100,
          pp: int.tryParse(_movePpController.text) ?? 5,
        ));
        _moveNameController.clear();
        _moveTypeController.clear();
        _movePowerController.clear();
        _moveAccuracyController.text = '100';
        _movePpController.text = '5';
      });
    }
  }

  void _removeMove(int index) {
    setState(() {
      _moves.removeAt(index);
    });
  }

  void _addAbility() {
    if (_abilityController.text.isNotEmpty) {
      setState(() {
        _abilities.add(_abilityController.text.trim());
        _abilityController.clear();
      });
    }
  }

  void _removeAbility(int index) {
    setState(() {
      _abilities.removeAt(index);
    });
  }

  void _saveCreature() {
    if (_formKey.currentState!.validate()) {
      final types = <String>[];
      if (_type1Controller.text.isNotEmpty) {
        types.add(_type1Controller.text.trim());
      }
      if (_type2Controller.text.isNotEmpty && _type2Controller.text.trim() != _type1Controller.text.trim()) {
        types.add(_type2Controller.text.trim());
      }

      if (types.isEmpty) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Debes ingresar al menos un tipo')),
        );
        return;
      }

      final creature = CreatureModel(
        id: widget.initialCreature?.id ?? '',
        name: _nameController.text.trim(),
        types: types,
        baseStats: BaseStats(
          hp: int.parse(_hpController.text),
          attack: int.parse(_attackController.text),
          defense: int.parse(_defenseController.text),
          spAttack: int.parse(_spAttackController.text),
          spDefense: int.parse(_spDefenseController.text),
          speed: int.parse(_speedController.text),
        ),
        moves: _moves,
        abilities: _abilities,
        basePrice: double.parse(_basePriceController.text),
        sprites: Sprites(
          front: _frontSpriteController.text.trim(),
          back: _backSpriteController.text.trim(),
          frontShiny: _frontShinyController.text.trim(),
          backShiny: _backShinyController.text.trim(),
        ),
        imageUrl: _imageUrlController.text.trim().isEmpty ? null : _imageUrlController.text.trim(),
        isActive: true,
      );

      widget.onSave(creature);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Form(
      key: _formKey,
      child: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            TextFormField(
              controller: _nameController,
              decoration: const InputDecoration(
                labelText: 'Nombre *',
                border: OutlineInputBorder(),
              ),
              validator: (value) {
                if (value == null || value.isEmpty) {
                  return 'El nombre es requerido';
                }
                return null;
              },
            ),
            const SizedBox(height: 16),
            Row(
              children: [
                Expanded(
                  child: TextFormField(
                    controller: _type1Controller,
                    decoration: const InputDecoration(
                      labelText: 'Tipo 1 *',
                      border: OutlineInputBorder(),
                    ),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Requerido';
                      }
                      return null;
                    },
                  ),
                ),
                const SizedBox(width: 16),
                Expanded(
                  child: TextFormField(
                    controller: _type2Controller,
                    decoration: const InputDecoration(
                      labelText: 'Tipo 2 (opcional)',
                      border: OutlineInputBorder(),
                    ),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 16),
            const Text('Estadísticas Base:', style: TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            Row(
              children: [
                Expanded(
                  child: TextFormField(
                    controller: _hpController,
                    decoration: const InputDecoration(labelText: 'HP', border: OutlineInputBorder()),
                    keyboardType: TextInputType.number,
                    validator: (value) => value?.isEmpty ?? true ? 'Requerido' : null,
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: TextFormField(
                    controller: _attackController,
                    decoration: const InputDecoration(labelText: 'Ataque', border: OutlineInputBorder()),
                    keyboardType: TextInputType.number,
                    validator: (value) => value?.isEmpty ?? true ? 'Requerido' : null,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 8),
            Row(
              children: [
                Expanded(
                  child: TextFormField(
                    controller: _defenseController,
                    decoration: const InputDecoration(labelText: 'Defensa', border: OutlineInputBorder()),
                    keyboardType: TextInputType.number,
                    validator: (value) => value?.isEmpty ?? true ? 'Requerido' : null,
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: TextFormField(
                    controller: _spAttackController,
                    decoration: const InputDecoration(labelText: 'Ataque Esp.', border: OutlineInputBorder()),
                    keyboardType: TextInputType.number,
                    validator: (value) => value?.isEmpty ?? true ? 'Requerido' : null,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 8),
            Row(
              children: [
                Expanded(
                  child: TextFormField(
                    controller: _spDefenseController,
                    decoration: const InputDecoration(labelText: 'Defensa Esp.', border: OutlineInputBorder()),
                    keyboardType: TextInputType.number,
                    validator: (value) => value?.isEmpty ?? true ? 'Requerido' : null,
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: TextFormField(
                    controller: _speedController,
                    decoration: const InputDecoration(labelText: 'Velocidad', border: OutlineInputBorder()),
                    keyboardType: TextInputType.number,
                    validator: (value) => value?.isEmpty ?? true ? 'Requerido' : null,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 16),
            TextFormField(
              controller: _basePriceController,
              decoration: const InputDecoration(
                labelText: 'Precio Base *',
                border: OutlineInputBorder(),
              ),
              keyboardType: TextInputType.number,
              validator: (value) => value?.isEmpty ?? true ? 'Requerido' : null,
            ),
            const SizedBox(height: 16),
            const Text('Movimientos:', style: TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            Row(
              children: [
                Expanded(
                  flex: 2,
                  child: TextFormField(
                    controller: _moveNameController,
                    decoration: const InputDecoration(labelText: 'Nombre', border: OutlineInputBorder()),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: TextFormField(
                    controller: _moveTypeController,
                    decoration: const InputDecoration(labelText: 'Tipo', border: OutlineInputBorder()),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: TextFormField(
                    controller: _movePowerController,
                    decoration: const InputDecoration(labelText: 'Poder', border: OutlineInputBorder()),
                    keyboardType: TextInputType.number,
                  ),
                ),
                IconButton(
                  icon: const Icon(Icons.add),
                  onPressed: _addMove,
                ),
              ],
            ),
            Row(
              children: [
                Expanded(
                  child: TextFormField(
                    controller: _moveAccuracyController,
                    decoration: const InputDecoration(labelText: 'Precisión', border: OutlineInputBorder()),
                    keyboardType: TextInputType.number,
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: TextFormField(
                    controller: _movePpController,
                    decoration: const InputDecoration(labelText: 'PP', border: OutlineInputBorder()),
                    keyboardType: TextInputType.number,
                  ),
                ),
              ],
            ),
            if (_moves.isNotEmpty) ...[
              const SizedBox(height: 8),
              ...List.generate(_moves.length, (index) {
                final move = _moves[index];
                return Card(
                  margin: const EdgeInsets.symmetric(vertical: 4),
                  child: ListTile(
                    title: Text(move.name),
                    subtitle: Text('${move.type} - Poder: ${move.power} - Prec: ${move.accuracy}% - PP: ${move.pp}'),
                    trailing: IconButton(
                      icon: const Icon(Icons.delete, color: Colors.red),
                      onPressed: () => _removeMove(index),
                    ),
                  ),
                );
              }),
            ],
            const SizedBox(height: 16),
            const Text('Habilidades:', style: TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            Row(
              children: [
                Expanded(
                  child: TextFormField(
                    controller: _abilityController,
                    decoration: const InputDecoration(labelText: 'Nombre de habilidad', border: OutlineInputBorder()),
                  ),
                ),
                IconButton(
                  icon: const Icon(Icons.add),
                  onPressed: _addAbility,
                ),
              ],
            ),
            if (_abilities.isNotEmpty) ...[
              const SizedBox(height: 8),
              Wrap(
                spacing: 8,
                runSpacing: 8,
                children: List.generate(_abilities.length, (index) {
                  return Chip(
                    label: Text(_abilities[index]),
                    onDeleted: () => _removeAbility(index),
                  );
                }),
              ),
            ],
            const SizedBox(height: 16),
            const Text('Sprites (URLs):', style: TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            TextFormField(
              controller: _frontSpriteController,
              decoration: const InputDecoration(labelText: 'Front', border: OutlineInputBorder()),
            ),
            const SizedBox(height: 8),
            TextFormField(
              controller: _backSpriteController,
              decoration: const InputDecoration(labelText: 'Back', border: OutlineInputBorder()),
            ),
            const SizedBox(height: 8),
            TextFormField(
              controller: _frontShinyController,
              decoration: const InputDecoration(labelText: 'Front Shiny', border: OutlineInputBorder()),
            ),
            const SizedBox(height: 8),
            TextFormField(
              controller: _backShinyController,
              decoration: const InputDecoration(labelText: 'Back Shiny', border: OutlineInputBorder()),
            ),
            const SizedBox(height: 16),
            TextFormField(
              controller: _imageUrlController,
              decoration: const InputDecoration(labelText: 'URL Imagen (opcional)', border: OutlineInputBorder()),
            ),
            const SizedBox(height: 24),
            Row(
              children: [
                if (widget.onCancel != null)
                  Expanded(
                    child: OutlinedButton(
                      onPressed: widget.onCancel,
                      child: const Text('Cancelar'),
                    ),
                  ),
                if (widget.onCancel != null) const SizedBox(width: 16),
                Expanded(
                  child: ElevatedButton(
                    onPressed: _saveCreature,
                    child: const Text('Guardar Criatura'),
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
