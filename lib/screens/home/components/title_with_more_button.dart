import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_moc_milktea_order_app/screens/home/components/title_with_underline.dart';

import '../../../contrains.dart';

class TitleWithMoreButton extends StatelessWidget {
  final String titleText;
  final Function onPress;
  const TitleWithMoreButton({
    Key? key,
    required this.titleText,
    required this.onPress,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(
          top: kDefaultPadding / 2, bottom: kDefaultPadding / 4),
      child: Padding(
        padding: EdgeInsets.symmetric(horizontal: kDefaultPadding),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            TitleWithUnderLine(text: this.titleText),
            Spacer(),
            TextButton(
                style: TextButton.styleFrom(
                    backgroundColor: kPrimaryColor,
                    elevation: 0,
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(20))),
                onPressed: () {
                  return this.onPress();
                },
                child: Text("More", style: TextStyle(color: Colors.white))),
          ],
        ),
      ),
    );
  }
}
