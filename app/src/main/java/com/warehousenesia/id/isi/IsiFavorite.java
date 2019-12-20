package com.warehousenesia.id.isi;

import com.warehousenesia.id.data.DataFavorite;

import java.util.ArrayList;

public class IsiFavorite {
    private static String[][] data = new String[][]{
            {"https://s3-ap-southeast-1.amazonaws.com/storage.asumsi.co/uploads/public/5bd/993/615/thumb_2396_800_800_0_0_auto.jpg", "Pesawat", "Japan", "Ini pesawat", "1000000"},
            {"https://www.cordstrap.com/globalassets/images---new-for-web/product-section/container-lashing/cargo-containers-at-port.jpg", "Cargo", "Japan", "Ini cargo", "1000000"},
            {"https://s3-ap-southeast-1.amazonaws.com/storage.asumsi.co/uploads/public/5bd/993/615/thumb_2396_800_800_0_0_auto.jpg", "Pesawat", "Japan", "Ini pesawat", "1000000"},
            {"https://www.cordstrap.com/globalassets/images---new-for-web/product-section/container-lashing/cargo-containers-at-port.jpg", "Cargo", "Japan", "Ini cargo", "1000000"},
            {"https://s3-ap-southeast-1.amazonaws.com/storage.asumsi.co/uploads/public/5bd/993/615/thumb_2396_800_800_0_0_auto.jpg", "Pesawat", "Japan", "Ini pesawat", "1000000"},
            {"https://www.cordstrap.com/globalassets/images---new-for-web/product-section/container-lashing/cargo-containers-at-port.jpg", "Cargo", "Japan", "Ini cargo", "1000000"},
    };

    public static ArrayList<DataFavorite> getData() {
        ArrayList<DataFavorite> list = new ArrayList<>();
        for (String[] aData : data){
            DataFavorite dataFavorite = new DataFavorite();
            dataFavorite.setImg(aData[0]);
            dataFavorite.setName(aData[1]);
            dataFavorite.setCountry(aData[2]);
            dataFavorite.setDesc(aData[3]);
            dataFavorite.setPrice(Integer.parseInt(aData[4]));
            list.add(dataFavorite);
        }
        return list;
    }
}
