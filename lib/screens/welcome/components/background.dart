import 'dart:ui';

import 'package:flutter/cupertino.dart';

class Background extends StatelessWidget {
  final Widget child;
  const Background({Key? key, required this.child}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;

    return Container(
        width: double.infinity,
        height: size.height,
        child: Stack(
          alignment: Alignment.center,
          children: [
            Positioned(
              child: Image.asset("assets/images/main_top.png"),
              top: 0,
              left: 0,
              width: size.width * .3,
            ),
            Positioned(
              child: Image.asset("assets/images/main_bottom.png"),
              bottom: 0,
              left: 0,
              width: size.width * .25,
            ),
            this.child
          ],
        ));
  }
}
