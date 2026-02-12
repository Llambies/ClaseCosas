import 'user_model.dart';
import 'creature_model.dart';

class LeagueModel {
  final String id;
  final String name;
  final String? description;
  final String creatorId;
  final UserModel? creator;
  final bool isPublic;
  final String? invitationCode;
  final int maxParticipants;
  final List<String> participantIds;
  final List<UserModel>? participants;
  final String status;
  final Map<String, dynamic>? rules;
  final List<String> availableCreatureIds;
  final List<CreatureModel>? availableCreatures;

  LeagueModel({
    required this.id,
    required this.name,
    this.description,
    required this.creatorId,
    this.creator,
    required this.isPublic,
    this.invitationCode,
    required this.maxParticipants,
    required this.participantIds,
    this.participants,
    required this.status,
    this.rules,
    required this.availableCreatureIds,
    this.availableCreatures,
  });

  factory LeagueModel.fromJson(Map<String, dynamic> json) {
    return LeagueModel(
      id: json['_id'] ?? json['id'] ?? '',
      name: json['name'] ?? '',
      description: json['description'],
      creatorId: json['creator'] is String
          ? json['creator']
          : json['creator']?['_id'] ?? '',
      creator: json['creator'] is Map
          ? UserModel.fromJson(json['creator'])
          : null,
      isPublic: json['isPublic'] ?? true,
      invitationCode: json['invitationCode'],
      maxParticipants: json['maxParticipants'] ?? 20,
      participantIds: json['participants'] != null
          ? (json['participants'] as List<dynamic>)
              .map<String>((p) {
                // Si es un string (ObjectId), devolverlo directamente
                if (p is String) return p;
                // Si es un objeto con _id, extraer el ID
                if (p is Map) return (p['_id'] ?? p['id'] ?? '').toString();
                // Si es otro tipo, convertir a string
                return p.toString();
              })
              .where((id) => id.isNotEmpty)
              .toList()
          : [],
      participants: json['participants'] != null
          ? (json['participants'] as List<dynamic>)
              .where((p) => p is Map<String, dynamic>)
              .map((p) => UserModel.fromJson(p as Map<String, dynamic>))
              .toList()
          : null,
      status: json['status'] ?? 'draft',
      rules: json['rules'],
      availableCreatureIds: json['availableCreatures'] != null
          ? (json['availableCreatures'] as List<dynamic>)
              .map<String>((c) {
                if (c is String) return c;
                if (c is Map) return (c['_id'] ?? c['id'] ?? '').toString();
                return c.toString();
              })
              .where((id) => id.isNotEmpty)
              .toList()
          : [],
      availableCreatures: json['availableCreatures'] != null
          ? (json['availableCreatures'] as List<dynamic>)
              .where((c) => c is Map<String, dynamic>)
              .map((c) => CreatureModel.fromJson(c as Map<String, dynamic>))
              .toList()
          : null,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'name': name,
      'description': description,
      'isPublic': isPublic,
      'maxParticipants': maxParticipants,
      'rules': rules,
      if (availableCreatureIds.isNotEmpty) 'availableCreatures': availableCreatureIds,
    };
  }
}
