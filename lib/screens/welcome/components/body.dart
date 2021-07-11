import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_moc_milktea_order_app/contrains.dart';
import 'package:flutter_moc_milktea_order_app/screens/welcome/components/background.dart';
import 'package:flutter_moc_milktea_order_app/screens/login/login_screen.dart';
import 'package:flutter_moc_milktea_order_app/screens/register/register_screen.dart';
import 'package:flutter_svg/svg.dart';

class Body extends StatelessWidget {
  const Body({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Background(
      child: SingleChildScrollView(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text("Welcome".toUpperCase(),
                style: Theme.of(context)
                    .textTheme
                    .headline5!
                    .copyWith(fontWeight: FontWeight.bold)),
            SizedBox(height: size.height * 0.04),
            SvgPicture.asset(
              "assets/icons/chat.svg",
              height: size.height * .45,
            ),
            SizedBox(height: size.height * 0.04),
            RoundedButton(
              text: "login",
              textColor: Colors.white,
              backgroundColor: kPrimaryColor,
              onPressed: () {
                Navigator.of(context)
                    .push(MaterialPageRoute(builder: (context) {
                  return LoginScreen();
                }));
              },
            ),
            RoundedButton(
              text: "register",
              textColor: Colors.black,
              backgroundColor: kPrimaryColor.withOpacity(.3),
              onPressed: () {
                Navigator.of(context)
                    .push(MaterialPageRoute(builder: (context) {
                  return RegisterScreen();
                }));
              },
            )
          ],
        ),
      ),
    );
  }
}

class RoundedButton extends StatelessWidget {
  final Function onPressed;
  final String text;
  final Color backgroundColor, textColor;
  const RoundedButton({
    Key? key,
    required this.onPressed,
    required this.text,
    required this.backgroundColor,
    required this.textColor,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Container(
      width: size.width * .8,
      margin: EdgeInsets.symmetric(vertical: 10),
      child: ClipRRect(
        borderRadius: BorderRadius.circular(40),
        child: TextButton(
            onPressed: () => this.onPressed(),
            child: Text(
              this.text.toUpperCase(),
              style: TextStyle(color: this.textColor),
            ),
            style: TextButton.styleFrom(
              backgroundColor: backgroundColor,
              padding: EdgeInsets.all(kDefaultPadding),
              elevation: 0,
            )),
      ),
    );
  }
}
