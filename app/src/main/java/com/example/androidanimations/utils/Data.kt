package com.example.androidanimations.utils

import com.example.androidanimations.R
import java.io.Serializable

data class Item(val name: String, val image: Any): Serializable

val sampleGridData = listOf(
    Item("Gingerbread", R.drawable.img_lights),
    Item("Ice Cream Sandwich ", R.drawable.serf),
    Item("Jelly Bean", R.drawable.butterfly),
    Item("KitKat", R.drawable.image2),
    Item("Lollipop", R.drawable.serf),
    Item("Marshmallow", R.drawable.butterfly),
    Item("Nougat", R.drawable.image2),
    Item("Oreo", R.drawable.img_lights),
    Item("Pie", R.drawable.serf),
    Item("Qwerty", R.drawable.butterfly)
)

val sampleRemoteGridData = listOf(
    Item("Gingerbread", "https://www.atlasandboots.com/wp-content/uploads/2019/05/feat-image-1-most-beautiful-mountains-in-the-world.jpg"),
    Item("Ice Cream Sandwich", "https://arc-anglerfish-arc2-prod-bonnier.s3.amazonaws.com/public/5CG3HJ3FYJJ4A4IGTFDV4IJLTE.jpg"),
    Item("Jelly Bean", "https://s3.amazonaws.com/images.gearjunkie.com/uploads/2018/05/matterhorn-3x2.jpg"),
    Item("KitKat", "https://geographical.co.uk/media/k2/items/cache/e533c4b8d2d2d3798f3271c35ca6e050_XL.jpg"),
    Item("Lollipop", "https://www.theuiaa.org/wp-content/uploads/2017/12/2018_banner.jpg"),
    Item("Marshmallow", "http://cdn.cnn.com/cnnnext/dam/assets/170407220916-04-iconic-mountains-matterhorn-restricted.jpg"),
    Item("Nougat", "https://www.jetsetter.com/uploads/sites/7/2018/04/rp47F1oo-1380x690.jpeg"),
    Item("Oreo", "http://papers.co/wallpaper/papers.co-me89-himalaya-sunset-white-mountain-art-24-wallpaper.jpg"),
    Item("Pie", "http://canitbesaturdaynow.com/images/fpics/2692/0956a399e99f941f6355291bb7191cda.jpg"),
    Item("Qwerty", "https://www.outsideonline.com/sites/default/files/styles/full-page/public/2018/05/15/deadly-mountains_h.jpg?itok=SMwA1ypO")
)