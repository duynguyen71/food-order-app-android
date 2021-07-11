import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_moc_milktea_order_app/contrains.dart';
import 'package:flutter_svg/svg.dart';

class ProfileMenuItem extends StatelessWidget {
  final String text, iconSrc;
  final Function onPress;

  const ProfileMenuItem({
    Key? key,
    required this.text,
    required this.iconSrc,
    required this.onPress,
  }) : super(key: key);

  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 20),
      child: TextButton(
          onPressed: () => this.onPress(),
          style: TextButton.styleFrom(
              shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.all(Radius.circular(15))),
              elevation: 1,
              backgroundColor: Color(0xFFF5F6F9),
              padding: const EdgeInsets.all(20)),
          child: Row(
            children: [
              SvgPicture.asset(
                this.iconSrc,
                color: kPrimaryColor,
              ),
              SizedBox(
                width: 20,
              ),
              Expanded(
                  child: Text(
                this.text,
                style: Theme.of(context).textTheme.bodyText1,
              )),
              Icon(Icons.arrow_forward_ios, color: kPrimaryLightColor)
            ],
          )),
    );
  }
}
