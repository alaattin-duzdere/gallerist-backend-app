package com.example.gallerist.exceptions;

import lombok.Getter;

@Getter
public enum MessageType {

    No_Record_Exist("1004","kayıt bulunamadı"),
    Token_Expired("1005","token süresi dolmuş"),
    UserName_Not_Found("1006","username bulunamadı"),
    Username_Or_Password_Invalid("1007","kullanıcı adı veya şifre hatalı"),
    Refresh_Token_Not_Found("1008","refresh token bulunamadı"),
    Refresh_Token_Expired("1009","refresh token süresi dolmuş"),
    Currency_Rates_Is_Occured("1010","döviz kuru alınırken hata oluştu"),
    General_Exception("9999","genel bir hata oluştu"),
    Customer_Amount_Is_Not_Enough("1011","müşteri bakiyesi yetersiz"),
    Car_Status_Is_Already_Saled("1012","araç zaten satılmış");

    private String code;

    private String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
