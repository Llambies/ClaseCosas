import 'package:ejemplo_clase/repost.dart' as repost;

void main(List<String> arguments) async {
  print('Conectando a la API...');

  try {
    final repPost = repost.RepPost();
    final posts = await repPost.getData();
    for (var post in posts) {
      print(post['name']);
    }
  } catch (e) {
    print('Error: $e');
  }
}
