import 'creature_model.dart';

class EVs {
  final int hp;
  final int attack;
  final int defense;
  final int spAttack;
  final int spDefense;
  final int speed;

  EVs({
    this.hp = 0,
    this.attack = 0,
    this.defense = 0,
    this.spAttack = 0,
    this.spDefense = 0,
    this.speed = 0,
  });

  int get total => hp + attack + defense + spAttack + spDefense + speed;

  factory EVs.fromJson(Map<String, dynamic> json) {
    return EVs(
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

  EVs copyWith({
    int? hp,
    int? attack,
    int? defense,
    int? spAttack,
    int? spDefense,
    int? speed,
  }) {
    return EVs(
      hp: hp ?? this.hp,
      attack: attack ?? this.attack,
      defense: defense ?? this.defense,
      spAttack: spAttack ?? this.spAttack,
      spDefense: spDefense ?? this.spDefense,
      speed: speed ?? this.speed,
    );
  }
}

class TeamCreature {
  final String creatureId;
  final CreatureModel? creature;
  final String? nickname;
  final EVs evs;
  final List<String> moves;
  final String nature;
  final String item;

  TeamCreature({
    required this.creatureId,
    this.creature,
    this.nickname,
    EVs? evs,
    required this.moves,
    this.nature = 'neutral',
    this.item = '',
  }) : evs = evs ?? EVs();

  factory TeamCreature.fromJson(Map<String, dynamic> json) {
    return TeamCreature(
      creatureId: json['creature'] is String
          ? json['creature']
          : json['creature']?['_id'] ?? '',
      creature: json['creature'] is Map
          ? CreatureModel.fromJson(json['creature'])
          : null,
      nickname: json['nickname'],
      evs: json['evs'] != null ? EVs.fromJson(json['evs']) : EVs(),
      moves: (json['moves'] as List<dynamic>?)
              ?.map((m) => m.toString())
              .toList() ??
          [],
      nature: json['nature'] ?? 'neutral',
      item: json['item'] ?? '',
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'creature': creatureId,
      if (nickname != null) 'nickname': nickname,
      'evs': evs.toJson(),
      'moves': moves,
      'nature': nature,
      'item': item,
    };
  }
}

class TeamModel {
  final String id;
  final String userId;
  final String leagueId;
  final String name;
  final List<TeamCreature> creatures;
  final bool isActive;

  TeamModel({
    required this.id,
    required this.userId,
    required this.leagueId,
    required this.name,
    required this.creatures,
    required this.isActive,
  });

  factory TeamModel.fromJson(Map<String, dynamic> json) {
    return TeamModel(
      id: json['_id'] ?? json['id'] ?? '',
      userId: json['user'] is String
          ? json['user']
          : json['user']?['_id'] ?? '',
      leagueId: json['league'] is String
          ? json['league']
          : json['league']?['_id'] ?? '',
      name: json['name'] ?? '',
      creatures: (json['creatures'] as List<dynamic>?)
              ?.map((c) => TeamCreature.fromJson(c))
              .toList() ??
          [],
      isActive: json['isActive'] ?? true,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'name': name,
      'creatures': creatures.map((c) => c.toJson()).toList(),
    };
  }
}
