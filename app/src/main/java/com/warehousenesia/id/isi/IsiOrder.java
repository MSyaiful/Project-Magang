package com.warehousenesia.id.isi;

import com.warehousenesia.id.data.DataOrder;

import java.util.ArrayList;

public class IsiOrder {
    private static String[][] data = new String[][]{
            {"3922390", "1000000", "Pesanan Diproses"},
            {"3922391", "2000000", "Pembayaran Sedang Diproses"},
            {"3922392", "3000000", "Pesanan Selesai"},
    };

    public static ArrayList<DataOrder> getData() {
        ArrayList<DataOrder> list = new ArrayList<>();
        for (String[] aData : data){
            DataOrder dataOrder = new DataOrder();
            dataOrder.setKodeTransaksi(aData[0]);
            dataOrder.setTotalBayar(aData[1]);
            dataOrder.setStatus(aData[2]);
            list.add(dataOrder);
        }
        return list;
    }

}
