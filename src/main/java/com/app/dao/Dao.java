package com.app.dao;


import java.io.IOException;


public interface Dao {

    public static void save(String TextMessages, String Data, String Phone) throws IOException {
    }

    public static String text(String text) {
        return text;
    }


    public abstract boolean smsSearchKeyBdClient(String text, String Phone);


    public abstract boolean smsSearchKeyBdAdmin(String SMS, String Phone);

    public abstract boolean phone(String phone, String SMS, String DateEvent);
    public abstract boolean blackList(String phoneanmberblackList, String SMS);
    public abstract boolean id24hour(String Phone, String idPhone, String SMS);
    public abstract Boolean dateActivation(String code, String Phone);



}
