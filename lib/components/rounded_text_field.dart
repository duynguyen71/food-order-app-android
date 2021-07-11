import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../contrains.dart';

class RoundedTextField extends StatelessWidget {
  final String hintText;
  final ValueChanged<String> onChanged;
  final IconData icon;
  const RoundedTextField({
    Key? key,
    required this.hintText,
    required this.onChanged,
    required this.icon,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return TextField(
      obscureText: false,
      onChanged: (value) {
        return this.onChanged(value);
      },
      decoration: InputDecoration(
          border: InputBorder.none,
          hintText: this.hintText,
          icon: Icon(
            this.icon,
            color: kPrimaryColor,
          )),
    );
  }
}
