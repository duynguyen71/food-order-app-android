import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_moc_milktea_order_app/screens/welcome/components/body.dart';

class WelcomeScreen extends StatelessWidget {
  const WelcomeScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SafeArea(
        child: Scaffold(
      body: Body(),
    ));
  }
}
