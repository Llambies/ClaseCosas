class BaseStats {
  final int hp;
  final int attack;
  final int defense;
  final int spAttack;
  final int spDefense;
  final int speed;

  BaseStats({
    required this.hp,
    required this.attack,
    required this.defense,
    required this.spAttack,
    required this.spDefense,
    required this.speed,
  });

  factory BaseStats.fromJson(Map<String, dynamic> json) {
    return BaseStats(
      hp: json['hp'] ?? 0,
      attack: json['attack'] ?? 0,
      defense: json['defense'] ?? 0,
      spAttack: json['spAttack'] ?? 0,
      spDefense: json['spDefense'] ?? 0,
      speed: json['speed'] ?? 0,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'hp': hp,
      'attack': attack,
      'defense': defense,
      'spAttack': spAttack,
      'spDefense': spDefense,
      'speed': speed,
    };
  }
}

class Move {
  final String name;
  final String type;
  final int power;
  final int accuracy;
  final int pp;

  Move({
    required this.name,
    required this.type,
    this.power = 0,
    this.accuracy = 100,
    this.pp = 5,
  });

  factory Move.fromJson(Map<String, dynamic> json) {
    return Move(
      name: json['name'] ?? '',
      type: json['type'] ?? '',
      power: json['power'] ?? 0,
      accuracy: json['accuracy'] ?? 100,
      pp: json['pp'] ?? 5,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'name': name,
      'type': type,
      'power': power,
      'accuracy': accuracy,
      'pp': pp,
    };
  }
}

class Sprites {
  final String front;
  final String back;
  final String frontShiny;
  final String backShiny;

  Sprites({
    this.front = '',
    this.back = '',
    this.frontShiny = '',
    this.backShiny = '',
  });

  factory Sprites.fromJson(Map<String, dynamic> json) {
    return Sprites(
      front: json['front'] ?? '',
      back: json['back'] ?? '',
      frontShiny: json['frontShiny'] ?? '',
      backShiny: json['backShiny'] ?? '',
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'front': front,
      'back': back,
      'frontShiny': frontShiny,
      'backShiny': backShiny,
    };
  }
}

class CreatureModel {
  final String id;
  final String name;
  final List<String> types;
  final BaseStats baseStats;
  final List<Move> moves;
  final List<String> abilities;
  final double basePrice;
  final Sprites sprites;
  final String? imageUrl;
  final bool isActive;

  CreatureModel({
    required this.id,
    required this.name,
    required this.types,
    required this.baseStats,
    required this.moves,
    required this.abilities,
    required this.basePrice,
    required this.sprites,
    this.imageUrl,
    required this.isActive,
  });

  factory CreatureModel.fromJson(Map<String, dynamic> json) {
    return CreatureModel(
      id: json['_id'] ?? json['id'] ?? '',
      name: json['name'] ?? '',
      types: (json['types'] as List<dynamic>?)
              ?.map((t) => t.toString())
              .toList() ??
          (json['type'] != null ? [json['type'].toString()] : []),
      baseStats: BaseStats.fromJson(json['baseStats'] ?? {}),
      moves: (json['moves'] as List<dynamic>?)
              ?.map((m) => Move.fromJson(m))
              .toList() ??
          [],
      abilities: (json['abilities'] as List<dynamic>?)
              ?.map((a) => a.toString())
              .toList() ??
          [],
      basePrice: (json['basePrice'] ?? 0).toDouble(),
      sprites: json['sprites'] != null
          ? Sprites.fromJson(json['sprites'])
          : Sprites(),
      imageUrl: json['imageUrl'],
      isActive: json['isActive'] ?? true,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'name': name,
      'types': types,
      'baseStats': baseStats.toJson(),
      'moves': moves.map((m) => m.toJson()).toList(),
      'abilities': abilities,
      'basePrice': basePrice,
      'sprites': sprites.toJson(),
      if (imageUrl != null) 'imageUrl': imageUrl,
      'isActive': isActive,
    };
  }

  // Getter para compatibilidad con código antiguo
  String get type => types.isNotEmpty ? types.first : '';
}
