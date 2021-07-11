import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_moc_milktea_order_app/components/rounded_text_field.dart';
import 'package:flutter_moc_milktea_order_app/components/rounded_text_field_container.dart';
import 'package:flutter_moc_milktea_order_app/components/rounded_text_field_password.dart';
import 'package:flutter_moc_milktea_order_app/contrains.dart';
import 'package:flutter_moc_milktea_order_app/screens/login/components/body.dart';
import 'package:flutter_moc_milktea_order_app/screens/login/login_screen.dart';
import 'package:flutter_moc_milktea_order_app/screens/register/components/background.dart';
import 'package:flutter_moc_milktea_order_app/screens/register/components/or_divider.dart';
import 'package:flutter_moc_milktea_order_app/screens/welcome/components/body.dart';
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
          Text("Sign up".toUpperCase(),
              style: Theme.of(context)
                  .textTheme
                  .headline5!
                  .copyWith(fontWeight: FontWeight.bold)),
          SizedBox(height: size.height * .04),
          SvgPicture.asset(
            "assets/icons/signup.svg",
            height: size.height * .35,
          ),
          SizedBox(height: size.height * .04),
          RoundedTextFieldContainer(
              child: RoundedTextField(
                  hintText: "Your email",
                  onChanged: (value) {},
                  icon: Icons.email)),
          RoundedTextFieldContainer(
              child: RoundedTextFiledPassword(onChanged: (value) {
            print(value);
          })),
          RoundedButton(
              onPressed: () {},
              text: "Register",
              backgroundColor: kPrimaryColor,
              textColor: Colors.white),
          AldreadyHaveAccountCheck(
              isLogin: false,
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (context) {
                  return LoginScreen();
                }));
              }),
          OrDivider(),
          Container(
            width: size.width * .8,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                SocialIcon(
                  src: "assets/icons/facebook.svg",
                  onPressed: () {},
                ),
                SocialIcon(
                  src: "assets/icons/google-plus.svg",
                  onPressed: () {},
                ),
                SocialIcon(
                  src: "assets/icons/twitter.svg",
                  onPressed: () {},
                ),
              ],
            ),
          )
        ],
      )),
    );
  }
}

class SocialIcon extends StatelessWidget {
  final String src;
  final Function onPressed;
  const SocialIcon({
    Key? key,
    required this.src,
    required this.onPressed,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        return this.onPressed();
      },
      child: Container(
        margin: EdgeInsets.symmetric(horizontal: 10),
        padding: EdgeInsets.all(kDefaultPadding / 2),
        decoration: BoxDecoration(
            shape: BoxShape.circle,
            border: Border.all(width: 2, color: kPrimaryColor)),
        child: SvgPicture.asset(
          this.src,
          color: kPrimaryColor,
          width: 20,
          height: 20,
        ),
      ),
    );
  }
}
