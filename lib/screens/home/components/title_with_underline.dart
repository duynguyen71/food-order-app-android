import 'package:flutter/cupertino.dart';

import '../../../contrains.dart';

class TitleWithUnderLine extends StatelessWidget {
  final String text;
  const TitleWithUnderLine({
    Key? key,
    required this.text,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 24,
      child: Stack(
        children: [
          Padding(
            padding: const EdgeInsets.only(left: kDefaultPadding / 4),
            child: Text(
              text,
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.w300),
            ),
          ),
          Positioned(
              bottom: 0,
              left: 0,
              right: 0,
              child: Container(
                height: 7,
                padding: EdgeInsets.only(right: kDefaultPadding / 4),
                decoration: BoxDecoration(color: kPrimaryColor.withOpacity(.3)),
              ))
        ],
      ),
    );
  }
}
