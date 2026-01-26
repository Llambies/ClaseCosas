class UserModel {
  final String id;
  final String email;
  final String username;
  final double budget;
  final int totalPoints;
  final bool isVerified;

  UserModel({
    required this.id,
    required this.email,
    required this.username,
    required this.budget,
    required this.totalPoints,
    required this.isVerified,
  });

  factory UserModel.fromJson(Map<String, dynamic> json) {
    return UserModel(
      id: json['id'] ?? json['_id'] ?? '',
      email: json['email'] ?? '',
      username: json['username'] ?? '',
      budget: (json['budget'] ?? 0).toDouble(),
      totalPoints: json['totalPoints'] ?? 0,
      isVerified: json['isVerified'] ?? false,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'email': email,
      'username': username,
      'budget': budget,
      'totalPoints': totalPoints,
    };
  }
}
