package com.warehousenesia.id.isi;

import com.warehousenesia.id.data.DataRequest;

import java.util.ArrayList;

public class IsiRequest {
    private static String[][] data = new String[][]{
            {"https://s3-ap-southeast-1.amazonaws.com/storage.asumsi.co/uploads/public/5bd/993/615/thumb_2396_800_800_0_0_auto.jpg", "Pesawat", "Ini pesawat", "Otomotif", "Barang Ditolak"},
            {"https://www.cordstrap.com/globalassets/images---new-for-web/product-section/container-lashing/cargo-containers-at-port.jpg", "Cargo", "Ini cargo", "Barang", "Barang Di Katalog"},
            {"https://s3-ap-southeast-1.amazonaws.com/storage.asumsi.co/uploads/public/5bd/993/615/thumb_2396_800_800_0_0_auto.jpg", "Pesawat", "Ini pesawat", "Otomotif", "Barang Ditolak"},
            {"https://www.cordstrap.com/globalassets/images---new-for-web/product-section/container-lashing/cargo-containers-at-port.jpg", "Cargo", "Ini cargo", "Barang", "Barang Di Katalog"},
    };

    public static ArrayList<DataRequest> getData() {
        ArrayList<DataRequest> list = new ArrayList<>();
        for (String[] aData : data){
            DataRequest dataRequest = new DataRequest();
            dataRequest.setImg(aData[0]);
            dataRequest.setName(aData[1]);
            dataRequest.setDesc(aData[2]);
            dataRequest.setCategory(aData[3]);
            dataRequest.setStatus(aData[4]);
            list.add(dataRequest);
        }
        return list;
    }

}
