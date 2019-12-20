package com.warehousenesia.id;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    public static final String SP_MEMBER_APP = "spMemberApp";

    public static final String SP_NAMA = "spNama";
    public static final String SP_IDAGENT = "spIdagent";
    public static final String SP_IDMEMBER = "spIdmember";
    public static final String SP_STATUS = "spStatus";
    public static final String SP_LEVEL = "spLeevel";
    public static final String SP_ROLE = "spRole";
    public static final String SP_LIMIT = "spLimit";
    public static final String SP_LASTMEMBER = "spLastmember";
    public static final String SP_IDBANK = "spIdbank";

    public static final String SP_PhoneNumber = "spPhoneNumber";
    public static final String SP_Email = "spEmail";
    public static final String SP_FullName = "spFullName";
    public static final String SP_CompanyName = "spCompanyName";
    public static final String SP_GambarUser  = "spGambarUser";


    public static final String SP_BANKID = "spBankId";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    public static final String SP_VERIFY = "spVerify";

    public static final String SP_PANJANG = "spPanjang";

    //Customer
    public static final String SP_IdCustomer = "spIdCustomer";
    public static final String SP_IdAgentCustomer = "spIdAgentCustomer";
    public static final String SP_NamaCustomer = "spNamaCustomer";
    public static final String SP_TelponCustomer = "spTelponCustomer";
    public static final String SP_AlamatCustomer = "spAlamatCustomer";
    public static final String SP_KecamatanCustomer = "spKecamatanCustomer";
    public static final String SP_KotaCustomer = "spKotaCustomer";
    public static final String SP_ProvinsiCustomer = "spProvinsiCustomer";
    public static final String SP_KtpCustomer = "spKtpCustomer";
    public static final String SP_NpwpCustomer = "spNpwpCustomer";
    public static final String SP_KodeposCustomer = "spKodeposCustomer";
    public static final String SP_IdTransaksi = "spIdTransaksi";

    //Produk
    public static final String SP_NCProduk = "spNCProduk";
    public static final String SP_NamaProduk = "spNamaProduk";
    public static final String SP_JumlahBeli = "spJumlahBeli";
    public static final String SP_TotalBerat = "spTotalBerat";
    public static final String SP_TotalHarga = "spTotalHarga";
    public static final String SP_IdProduk = "spIdProduk";
    public static final String SP_IDAProduk = "spIDAProduk";
    public static final String SP_IDCProduk = "spIDCProduk";
    public static final String SP_CurrencyProduk = "spCurrencyProduk";
    public static final String SP_HargaProduk = "spHargaProduk";
    public static final String SP_BeratProduk = "spBeratProduk";
    public static final String SP_Catatan = "spCatatan";
    public static final String SP_Rupiah = "spRupiah";
    public static final String SP_Origin = "spOrigin";
    public static final String SP_Tax = "spTax";
    public static final String SP_IdKeranjang = "sp_IdKeranjang";
    public static final String SP_IdDetailProduct = "sp_IdDetailProduct";

    //Ringkasan Belanja
    public static final String SP_TotalItemKeranjang = "spTotalItemKeranjang";
    public static final String SP_TotalWeightKeranjang = "spTotalWeightKeranjang";
    public static final String SP_TotalHargaKeranjang = "spTotalHargaKeranjang";
    public static final String SP_TransactionfeeKeranjang = "spTransactionfeeKeranjang";
    public static final String SP_ShippingfeeKeranjang = "spShippingfeeKeranjang";
    public static final String SP_DeliveryfeeKeranjang = "spDeliveryfeeKeranjang";
    public static final String SP_TotalPayKeranjang = "spTotalPayKeranjang";
    public static final String SP_ShipNegara = "spShipNegara";

    //Ringkasan Belanja2
    public static final String SP_TotalItemKeranjang2 = "spTotalItemKeranjang2";
    public static final String SP_TotalWeightKeranjang2 = "spTotalWeightKeranjang2";
    public static final String SP_TotalHargaKeranjang2 = "spTotalHargaKeranjang2";
    public static final String SP_Tax2 = "spTax2";

    //Order
    public static final String SP_TotalItemOrder = "spTotalItemOrder";
    public static final String SP_TotalWeightOrder = "spTotalWeightOrder";
    public static final String SP_HargaOrder = "spHargaOrder";
    public static final String SP_ShippingFeeOrder = "spShippingFeeOrder";
    public static final String SP_TotalHargaOrder = "spTotalHargaOrder";


    public static String getSpPanjang() {
        return SP_PANJANG;
    }

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_MEMBER_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPNama(){
        return sp.getString(SP_NAMA, "");
    }

    public String getSpBankid(){
        return sp.getString(SP_BANKID, "");
    }

    public String getSpIdagent(){
        return sp.getString(SP_IDAGENT, "");
    }

    public String getSpIdmember() {return sp.getString(SP_IDMEMBER, "");}

    public String getSpStatus() {return sp.getString(SP_STATUS, "");}

    public String getSpRole() {return sp.getString(SP_ROLE, "");}

    public String getSpLevel() {return sp.getString(SP_LEVEL, "");}
    public String getSpLimit() {return sp.getString(SP_LIMIT, "");}
    public String getSpLastmember() {return sp.getString(SP_LASTMEMBER, "");}
    public String getSpIdbank() {return sp.getString(SP_IDBANK, "");}

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }

    public Boolean getSPVerify(){
        return sp.getBoolean(SP_VERIFY, false);
    }

    public String getPanjang() {return sp.getString(SP_PANJANG, "");}

    public String getSP_IdCustomer() {
        return sp.getString(SP_IdCustomer, "");
    }

    public String getSP_IdAgentCustomer() {
        return sp.getString(SP_IdAgentCustomer, "");
    }

    public String getSP_NamaCustomer() {
        return sp.getString(SP_NamaCustomer,"");
    }

    public String getSP_TelponCustomer() {
        return sp.getString(SP_TelponCustomer,"");
    }

    public String getSP_AlamatCustomer() {
        return sp.getString(SP_AlamatCustomer,"");
    }

    public String getSP_KecamatanCustomer() {
        return sp.getString(SP_KecamatanCustomer,"");
    }

    public String getSP_KotaCustomer() {
        return sp.getString(SP_KotaCustomer,"");
    }

    public String getSP_ProvinsiCustomer() {
        return sp.getString(SP_ProvinsiCustomer,"");
    }

    public String getSP_KtpCustomer() {
        return sp.getString(SP_KtpCustomer,"");
    }

    public String getSP_NpwpCustomer() {
        return sp.getString(SP_NpwpCustomer,"");
    }

    public String getSP_KodeposCustomer() {
        return sp.getString(SP_KodeposCustomer,"");
    }

    public String getSP_IdTransaksi() {
        return sp.getString(SP_IdTransaksi,"");
    }

    public String getSP_PhoneNumber() {
        return sp.getString(SP_PhoneNumber, "");
    }

    public String getSP_Email() {
        return sp.getString(SP_Email, "");
    }

    public String getSP_FullName() {
        return sp.getString(SP_FullName, "");
    }

    public String getSP_CompanyName() {
        return sp.getString(SP_CompanyName, "");
    }

    public String getSP_GambarUser() {
        return sp.getString(SP_GambarUser, "");
    }

    public String getSP_NamaProduk() {
        return sp.getString(SP_NamaProduk, "");
    }

    public String getSP_JumlahBeli() {
        return sp.getString(SP_JumlahBeli, "");
    }

    public String getSP_TotalHarga() {
        return sp.getString(SP_TotalHarga, "");
    }

    public String getSP_TotalBerat() {
        return sp.getString(SP_TotalBerat, "");
    }

    public String getSP_IdProduk() {
        return sp.getString(SP_IdProduk, "");
    }

    public String getSP_HargaProduk() {
        return sp.getString(SP_HargaProduk, "");
    }

    public String getSP_BeratProduk() {
        return sp.getString(SP_BeratProduk, "");
    }

    public String getSP_NCProduk() {
        return sp.getString(SP_NCProduk, "");
    }

    public String getSP_IDAProduk() {
        return sp.getString(SP_IDAProduk, "");
    }

    public String getSP_IDCProduk() {
        return sp.getString(SP_IDCProduk, "");
    }

    public String getSP_CurrencyProduk() {
        return sp.getString(SP_CurrencyProduk, "");
    }

    public String getSP_Catatan() {
        return sp.getString(SP_Catatan, "");
    }

    public String getSP_Origin() {
        return sp.getString(SP_Origin, "");
    }

    public String getSP_Rupiah() {
        return sp.getString(SP_Rupiah, "");
    }

    public String getSP_Tax() {
        return sp.getString(SP_Tax, "");
    }

    public String getSP_IdKeranjang() {
        return sp.getString(SP_IdKeranjang, "");
    }

    public String getSP_TotalItemKeranjang() {
        return sp.getString(SP_TotalItemKeranjang, "");
    }

    public String getSP_TotalWeightKeranjang() {
        return sp.getString(SP_TotalWeightKeranjang, "");
    }

    public String getSP_TotalHargaKeranjang() {
        return sp.getString(SP_TotalHargaKeranjang, "");
    }

    public String getSP_TransactionfeeKeranjang() {
        return sp.getString(SP_TransactionfeeKeranjang, "");
    }

    public String getSP_ShippingfeeKeranjang() {
        return sp.getString(SP_ShippingfeeKeranjang,"");
    }

    public String getSP_DeliveryfeeKeranjang() {
        return sp.getString(SP_DeliveryfeeKeranjang, "");
    }

    public String getSP_TotalPayKeranjang() {
        return sp.getString(SP_TotalPayKeranjang, "");
    }

    public String getSP_TotalItemKeranjang2() {
        return sp.getString(SP_TotalItemKeranjang2, "");
    }

    public String getSP_TotalWeightKeranjang2() {
        return sp.getString(SP_TotalWeightKeranjang2, "");
    }

    public String getSP_TotalHargaKeranjang2() {
        return sp.getString(SP_TotalHargaKeranjang2, "");
    }

    public String getSP_Tax2() {
        return sp.getString(SP_Tax2, "");
    }

    public String getSP_ShipNegara() {return  sp.getString(SP_ShipNegara, "");}

    public String getSP_IdDetailProduct() {
        return sp.getString(SP_IdDetailProduct, "");
    }

    public String getSP_TotalItemOrder() {
        return sp.getString(SP_TotalItemOrder, "");
    }

    public String getSP_TotalWeightOrder() {
        return sp.getString(SP_TotalWeightOrder, "");
    }

    public String getSP_HargaOrder() {
        return sp.getString(SP_HargaOrder, "");
    }

    public String getSP_ShippingFeeOrder() {
        return sp.getString(SP_ShippingFeeOrder, "");
    }

    public String getSP_TotalHargaOrder() {
        return sp.getString(SP_TotalHargaOrder, "");
    }
}