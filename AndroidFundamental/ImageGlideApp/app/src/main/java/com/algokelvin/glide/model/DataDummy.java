package com.algokelvin.glide.model;

import java.util.ArrayList;

public class DataDummy {
    public static ArrayList<ImageUrl> prepareData() {
        String[] imageUrls = {
                "https://3.bp.blogspot.com/-pB-6XU57abw/WQeI1rU-hiI/AAAAAAACmEA/EHHhfVuh1XQO5Tr_tZPAAFf4bAlFoUcrgCLcB/s640/Naruto%2BShippuden.png",
                "https://image.api.playstation.com/cdn/UP0700/CUSA02412_00/gUAft3KlWyWIX5GXX4beddHy9F1CYtro.png",
                "https://assets1.ignimgs.com/2020/04/23/naruto-shippuden-ultimate-ninja-storm-4-road-to-boruto---button-fin-1587603169460.jpg",
                "https://i.pinimg.com/originals/ff/8c/80/ff8c80977b93315f25d52cde12bba036.png"
        };

        ArrayList<ImageUrl> imageUrlList = new ArrayList<>();

        for (String url : imageUrls) {
            ImageUrl imageUrl = new ImageUrl();
            imageUrl.setImageUrl(url);
            imageUrlList.add(imageUrl);
        }

        return imageUrlList;
    }
}
