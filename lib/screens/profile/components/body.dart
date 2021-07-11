import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_moc_milktea_order_app/screens/login/login_screen.dart';
import 'package:flutter_moc_milktea_order_app/screens/profile/components/profile_menu_item.dart';
import 'package:flutter_moc_milktea_order_app/screens/profile/components/profile_picture.dart';

class Body extends StatelessWidget {
  const Body({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: [
          SizedBox(
            height: 20,
          ),
          ProfilePicture(),
          SizedBox(
            height: 20,
          ),
          ProfileMenuItem(
            iconSrc: "assets/icons/User Icon.svg",
            text: "Account",
            onPress: () {},
          ),
          ProfileMenuItem(
            iconSrc: "assets/icons/Bell.svg",
            text: "Notifications",
            onPress: () {},
          ),
          ProfileMenuItem(
            iconSrc: "assets/icons/Question mark.svg",
            text: "Setting",
            onPress: () {},
          ),
          ProfileMenuItem(
            iconSrc: "assets/icons/Question mark.svg",
            text: "Help Center",
            onPress: () {},
          ),
          ProfileMenuItem(
            iconSrc: "assets/icons/Log out.svg",
            text: "Log out",
            onPress: () {
              Navigator.push(context,
                  MaterialPageRoute(builder: (context) => LoginScreen()));
            },
          ),
        ],
      ),
    );
  }
}
