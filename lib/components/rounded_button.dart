import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_moc_milktea_order_app/contrains.dart';

class RoundedButton extends StatelessWidget {
  final Function onPressed;
  final String text;
  final Color backgroundColor, textColor;
  const RoundedButton({
    Key? key,
    required this.onPressed,
    required this.text,
    required this.backgroundColor,
    required this.textColor,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Container(
      width: size.width * .8,
      margin: EdgeInsets.symmetric(vertical: 10),
      child: ClipRRect(
        borderRadius: BorderRadius.circular(40),
        child: TextButton(
            onPressed: () => this.onPressed(),
            child: Text(
              this.text.toUpperCase(),
              style: TextStyle(color: this.textColor),
            ),
            style: TextButton.styleFrom(
              backgroundColor: backgroundColor,
              padding: EdgeInsets.all(kDefaultPadding),
              elevation: 0,
            )),
      ),
    );
  }
}
