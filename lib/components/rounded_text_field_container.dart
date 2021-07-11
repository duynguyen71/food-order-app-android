import 'package:flutter/cupertino.dart';

import '../contrains.dart';

class RoundedTextFieldContainer extends StatelessWidget {
  final Widget child;
  const RoundedTextFieldContainer({
    Key? key,
    required this.child,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Container(
        alignment: Alignment.center,
        margin: EdgeInsets.symmetric(vertical: 10),
        padding: EdgeInsets.symmetric(vertical: 5, horizontal: 20),
        width: size.width * .8,
        decoration: BoxDecoration(
            color: kPrimaryColor.withOpacity(.4),
            borderRadius: BorderRadius.circular(40)),
        child: this.child);
  }
}
