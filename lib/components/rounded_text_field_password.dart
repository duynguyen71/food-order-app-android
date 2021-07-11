import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_moc_milktea_order_app/contrains.dart';

class RoundedTextFiledPassword extends StatelessWidget {
  final Function onChanged;
  const RoundedTextFiledPassword({
    Key? key,
    required this.onChanged,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return TextField(
      onChanged: (value) {
        return this.onChanged(value);
      },
      obscureText: true,
      decoration: InputDecoration(
          alignLabelWithHint: true,
          hintText: "Your password",
          border: InputBorder.none,
          suffixIcon: Icon(Icons.visibility, color: kPrimaryColor),
          icon: Icon(Icons.lock, color: kPrimaryColor)),
    );
  }
}
