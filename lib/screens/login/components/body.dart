import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_moc_milktea_order_app/components/rounded_text_field.dart';
import 'package:flutter_moc_milktea_order_app/components/rounded_text_field_container.dart';
import 'package:flutter_moc_milktea_order_app/components/rounded_text_field_password.dart';
import 'package:flutter_moc_milktea_order_app/contrains.dart';
import 'package:flutter_moc_milktea_order_app/screens/home/home_screen.dart';
import 'package:flutter_moc_milktea_order_app/screens/login/components/background.dart';
import 'package:flutter_moc_milktea_order_app/screens/register/register_screen.dart';
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
            Text("Login".toUpperCase(),
                style: Theme.of(context)
                    .textTheme
                    .headline5!
                    .copyWith(fontWeight: FontWeight.bold)),
            SizedBox(height: size.height * 0.04),
            SvgPicture.asset(
              "assets/icons/login.svg",
              height: size.height * .4,
            ),
            RoundedTextFieldContainer(
              child: RoundedTextField(
                icon: Icons.person,
                hintText: "Your email",
                onChanged: (value) {},
              ),
            ),
            RoundedTextFieldContainer(
              child: RoundedTextFiledPassword(
                onChanged: (value) {},
              ),
            ),
            RoundedButton(
                onPressed: () {
                  Navigator.push(context, MaterialPageRoute(
                    builder: (context) {
                      return HomeScreen();
                    },
                  ));
                },
                text: "Login",
                backgroundColor: kPrimaryColor,
                textColor: Colors.white),
            SizedBox(height: size.height * 0.04),
            AldreadyHaveAccountCheck(
              isLogin: true,
              onPressed: () {
                Navigator.of(context).push(MaterialPageRoute(
                  builder: (context) {
                    return RegisterScreen();
                  },
                ));
              },
            )
          ],
        ),
      ),
    );
  }
}

class AldreadyHaveAccountCheck extends StatelessWidget {
  final bool isLogin;
  final Function onPressed;
  const AldreadyHaveAccountCheck({
    Key? key,
    required this.isLogin,
    required this.onPressed,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Container(
      width: size.width * .8,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          Text(this.isLogin ? "Don't have account?" : "Aldready have account?",
              style: TextStyle(color: kPrimaryColor.withOpacity(.5))),
          GestureDetector(
            onTap: () {
              return this.onPressed();
            },
            child: Text(
              this.isLogin ? "Sign up" : "Sign in",
              style:
                  TextStyle(fontWeight: FontWeight.bold, color: kPrimaryColor),
            ),
          )
        ],
      ),
    );
  }
}
