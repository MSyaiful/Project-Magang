package com.warehousenesia.id.isi;

import com.warehousenesia.id.data.DataCart;

import java.util.ArrayList;

public class IsiCart {
    private static String[][] data = new String[][]{
            {"https://s3-ap-southeast-1.amazonaws.com/storage.asumsi.co/uploads/public/5bd/993/615/thumb_2396_800_800_0_0_auto.jpg", "Pesawat", "Japan", "1000000"},
            {"https://www.cordstrap.com/globalassets/images---new-for-web/product-section/container-lashing/cargo-containers-at-port.jpg", "Cargo", "Japan", "1000000"},
            {"https://s3-ap-southeast-1.amazonaws.com/storage.asumsi.co/uploads/public/5bd/993/615/thumb_2396_800_800_0_0_auto.jpg", "Pesawat", "Japan", "1000000"},
            {"https://www.cordstrap.com/globalassets/images---new-for-web/product-section/container-lashing/cargo-containers-at-port.jpg", "Cargo", "Japan", "1000000"},
            {"https://s3-ap-southeast-1.amazonaws.com/storage.asumsi.co/uploads/public/5bd/993/615/thumb_2396_800_800_0_0_auto.jpg", "Pesawat", "Japan", "1000000"},
            {"https://www.cordstrap.com/globalassets/images---new-for-web/product-section/container-lashing/cargo-containers-at-port.jpg", "Cargo", "Japan", "1000000"},
            {"https://s3-ap-southeast-1.amazonaws.com/storage.asumsi.co/uploads/public/5bd/993/615/thumb_2396_800_800_0_0_auto.jpg", "Pesawat", "Japan", "1000000"},
            {"https://www.cordstrap.com/globalassets/images---new-for-web/product-section/container-lashing/cargo-containers-at-port.jpg", "Cargo", "Japan", "1000000"},
            {"https://s3-ap-southeast-1.amazonaws.com/storage.asumsi.co/uploads/public/5bd/993/615/thumb_2396_800_800_0_0_auto.jpg", "Pesawat", "Japan", "1000000"},
            {"https://www.cordstrap.com/globalassets/images---new-for-web/product-section/container-lashing/cargo-containers-at-port.jpg", "Cargo", "Japan", "1000000"},
            {"https://s3-ap-southeast-1.amazonaws.com/storage.asumsi.co/uploads/public/5bd/993/615/thumb_2396_800_800_0_0_auto.jpg", "Pesawat", "Japan", "1000000"},
            {"https://www.cordstrap.com/globalassets/images---new-for-web/product-section/container-lashing/cargo-containers-at-port.jpg", "Cargo", "Japan", "1000000"},
    };

    public static ArrayList<DataCart> getData() {
        ArrayList<DataCart> list = new ArrayList<>();
        for (String[] aData : data){
            DataCart dataCart = new DataCart();
            dataCart.setImg(aData[0]);
            dataCart.setName(aData[1]);
            dataCart.setCountry(aData[2]);
            dataCart.setPrice(Integer.parseInt(aData[3]));
            list.add(dataCart);
        }
        return list;
    }
}
