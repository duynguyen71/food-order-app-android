import 'package:flutter/material.dart';
import 'package:flutter_moc_milktea_order_app/contrains.dart';
import 'package:flutter_moc_milktea_order_app/screens/home/home_screen.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        textTheme: Theme.of(context).textTheme.apply(bodyColor: kTextColor),
        scaffoldBackgroundColor: Colors.white,
        primaryColor: kPrimaryColor,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      
      ),
      //use route name
      
      home: HomeScreen(),
    );
  }
}
