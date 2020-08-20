package com.app.sms.reader.impl;
import com.app.dao.Dao;
import com.app.dao.DaoImpl;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientReadingSms extends SimpleSmsReader {


    public static void clientReadingSms(String SMS,String Phone) throws SQLException, InterruptedException {





        SMS=SMS;
        Pattern pattern = Pattern.compile("\\d+\\S?\\d*\\d+\\S?\\d*\\d+\\S?\\d");
        Matcher matcher = pattern.matcher(SMS);
        while (matcher.find()) {
            String textsms = matcher.group(0);
            if (textsms.replaceAll("\\D", " ").length() == textsms.length()) {



                Dao dao = new DaoImpl();
                dao.smsSearchKeyBdClient(textsms,Phone);


                }


            }









    }




}










