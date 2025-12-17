import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:pruebita/counter_screen.dart';

import 'counter_model.dart'; // Import the CounterModel

void main() {
  runApp( MyApp() );
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider( // Here we set up the provider
      create: (context) => CounterModel(),
      child: MaterialApp(
        home: CounterScreen(),
      ),
    );
  }
}
