import 'package:flutter/cupertino.dart';
import 'package:flutter_moc_milktea_order_app/contrains.dart';

class FeatureCardItem extends StatelessWidget {
  final String imgCoverSrc;
  final Function onPressed;
  const FeatureCardItem({
    Key? key,
    required this.imgCoverSrc,
    required this.onPressed,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return GestureDetector(
      onTap: () {
        return this.onPressed();
      },
      child: Container(
        width: size.width * .8,
        height: 185,
        margin: EdgeInsets.only(left: kDefaultPadding, bottom: kDefaultPadding),
        decoration: BoxDecoration(
            image: DecorationImage(
                fit: BoxFit.cover, image: AssetImage(this.imgCoverSrc))),
      ),
    );
  }
}
