import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_moc_milktea_order_app/contrains.dart';

class OrDivider extends StatelessWidget {
  const OrDivider({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Container(
      margin: EdgeInsets.all(10),
      alignment: Alignment.center,
      width: size.width * .8,
      child: Row(
        children: [
          Expanded(
              child: Divider(
            height: 10,
            color: kPrimaryLightColor,
          )),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: Text("OR"),
          ),
          Expanded(
              child: Divider(
            height: 10,
            color: kPrimaryLightColor,
          )),
        ],
      ),
    );
  }
}
