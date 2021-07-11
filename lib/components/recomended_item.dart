import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../contrains.dart';

class RecomendedItem extends StatelessWidget {
  final String title, category, price, src;
  final Function onPress;
  const RecomendedItem({
    Key? key,
    required this.title,
    required this.category,
    required this.price,
    required this.src,
    required this.onPress,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return GestureDetector(
      onTap: () {
        return this.onPress();
      },
      child: Container(
        width: size.width * 0.4,
        margin: EdgeInsets.only(
            left: kDefaultPadding,
            right: kDefaultPadding,
            bottom: kDefaultPadding / 2),
        child: Column(
          children: [
            Image.asset(this.src),
            Container(
              padding: EdgeInsets.all(kDefaultPadding / 2),
              decoration: BoxDecoration(
                color: Colors.white,
                boxShadow: [
                  BoxShadow(
                      offset: Offset(0, 5),
                      color: kPrimaryLightColor,
                      blurRadius: 20),
                ],
                borderRadius: BorderRadius.only(
                    bottomLeft: Radius.circular(20),
                    bottomRight: Radius.circular(20)),
              ),
              child: Row(
                children: [
                  RichText(
                      text: TextSpan(children: [
                    TextSpan(
                        text: this.title.toUpperCase() + "\n",
                        style: TextStyle(color: kTextColor)),
                    TextSpan(
                        text: this.category.toUpperCase(),
                        style: TextStyle(color: kPrimaryLightColor))
                  ])),
                  Spacer(),
                  Text(
                    "\$" + this.price,
                    style: Theme.of(context)
                        .textTheme
                        .button!
                        .copyWith(color: kPrimaryColor),
                  )
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}
