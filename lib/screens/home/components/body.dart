import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_moc_milktea_order_app/components/feature_card_item.dart';
import 'package:flutter_moc_milktea_order_app/screens/home/components/header_with_search_box.dart';
import 'package:flutter_moc_milktea_order_app/components/recomended_item.dart';
import 'package:flutter_moc_milktea_order_app/screens/home/components/title_with_more_button.dart';

class Body extends StatelessWidget {
  const Body({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          HeaderWithSearchBox(),
          TitleWithMoreButton(
            titleText: "Recomended",
            onPress: () {
              print("Mpre button prsse");
            },
          ),
          RecomendedItems(),
          TitleWithMoreButton(
            titleText: "Feature",
            onPress: () {
              print("Mpre button prsse");
            },
          ),
          buildFeartureCardItems()
        ],
      ),
    );
  }

  SingleChildScrollView buildFeartureCardItems() {
    return SingleChildScrollView(
      scrollDirection: Axis.horizontal,
      child: Row(
        children: [
          FeatureCardItem(
            imgCoverSrc: "assets/images/bottom_img_1.png",
            onPressed: () {},
          ),
          FeatureCardItem(
            imgCoverSrc: "assets/images/bottom_img_1.png",
            onPressed: () {},
          ),
          FeatureCardItem(
            imgCoverSrc: "assets/images/bottom_img_1.png",
            onPressed: () {},
          ),
        ],
      ),
    );
  }
}

class RecomendedItems extends StatelessWidget {
  const RecomendedItems({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      scrollDirection: Axis.horizontal,
      child: Row(
        children: [
          RecomendedItem(
              title: "Product",
              category: "category",
              price: "100",
              src: "assets/images/image_1.png",
              onPress: () {}),
          RecomendedItem(
              title: "Product",
              category: "category",
              price: "100",
              src: "assets/images/image_1.png",
              onPress: () {}),
          RecomendedItem(
              title: "Product",
              category: "category",
              price: "100",
              src: "assets/images/image_1.png",
              onPress: () {}),
        ],
      ),
    );
  }
}
