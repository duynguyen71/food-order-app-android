import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_moc_milktea_order_app/components/my_buttom_nav.dart';
import 'package:flutter_moc_milktea_order_app/screens/home/components/body.dart';
import 'package:flutter_moc_milktea_order_app/screens/login/login_screen.dart';
import 'package:flutter_moc_milktea_order_app/screens/profile/profile_screen.dart';
import 'package:flutter_svg/svg.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        appBar: AppBar(
          elevation: 0,
          leading: IconButton(
              onPressed: () {},
              icon: SvgPicture.asset("assets/icons/menu.svg")),
          actions: [
            IconButton(
                onPressed: () {
                  Navigator.push(context, MaterialPageRoute(builder: (context) {
                    return LoginScreen();
                  }));
                },
                icon: IconButton(
                    onPressed: () {
                      Navigator.of(context).push(MaterialPageRoute(
                          builder: (context) => ProfileScreen()));
                    },
                    icon: SvgPicture.asset(
                      "assets/icons/User Icon.svg",
                      color: Colors.white,
                    )))
          ],
        ),
        body: Body(),
        bottomNavigationBar: MyBottomNav(),
      ),
    );
  }
}
