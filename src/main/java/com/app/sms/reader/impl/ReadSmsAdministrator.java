package com.app.sms.reader.impl;

import com.app.dao.Dao;
import com.app.dao.DaoImpl;
import com.app.sms.reader.SmsReader;
import com.app.sms.sending.sms.TerminalSendSMS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.app.dao.DaoImpl.getConnection;

public abstract class ReadSmsAdministrator extends TerminalSendSMS implements SmsReader {


    public static void readSmsAdministrator(String SMS, String Phone) throws SQLException, InterruptedException, IOException, ParseException {

        try {


            String sms = "?";
            if (SMS.trim().equals(sms)) {
                smsvid5(Phone);


            }


            SMS = SMS;
            Pattern pattern = Pattern.compile("(0?[1.9]|1[12])");
            Matcher matcher = pattern.matcher(SMS);
            try {


                while (matcher.find()) {
                    String Text3 = matcher.group(0);

                    if (Text3.replaceAll("\\D", " ").length() == Text3.length()) {


                        String string = SMS.trim();
                        String[] str_array = string.split(" ");
                        String key = str_array[0];
                        String date = str_array[1];
                        String Str = (date);
                        String[] Arr = Str.split("\\.");
                        Str = String.join("", Arr[2], Arr[1], Arr[0]);



                        int s = Integer.parseInt(key);
                        if (s > 0) {


                            typeKey(key, Str, Phone);
                            break;
                        }


                    }


                }

            } catch (ArrayIndexOutOfBoundsException e) {
            }


            SMS = SMS;
            Pattern pattern2 = Pattern.compile("on");
            Matcher matcher2 = pattern2.matcher(SMS);
            while (matcher2.find()) {
                String Text2 = matcher2.group(0);
                if (Text2.replaceAll("\\D", " ").length() == Text2.length()) {


                    String taboo = "on";
                    String s = SMS.trim();

                    for (char c : taboo.toCharArray()) {
                        s = s.replace("on", " ");

                        String key = "2";
                        saveKey(s, key);


                    }
                    typKeyMain(Phone, s);
                    break;


                }
            }


            SMS = SMS;
            Pattern pattern3 = Pattern.compile("off");
            Matcher matcher3 = pattern3.matcher(SMS);
            while (matcher3.find()) {
                String Text1 = matcher3.group(0);
                if (Text1.replaceAll("\\D", " ").length() == Text1.length()) {


                    String taboo = "off";
                    String s = SMS.trim();

                    for (char c : taboo.toCharArray()) {
                        s = s.replace("off", " ");

                        String key = "3";
                        saveKey(s, key);


                    }

                    typKeySpare(Phone, s);
                    break;


                }
            }


            SMS = SMS;
            Pattern pattern4 = Pattern.compile("[?]");
            Matcher matcher4 = pattern4.matcher(SMS);
            while (matcher4.find()) {


                String taboo = "?";
                String s = SMS.trim();

                for (char c : taboo.toCharArray()) {
                    s = s.replace("?", " ");


                    typeKey(s, Phone);
                    break;

                }
                break;


            }


        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        if (SMS.trim().length() == 4) {


            Dao dao = new DaoImpl();
            dao.smsSearchKeyBdAdmin(SMS, Phone);
        }

    }


    public static void saveKey(String s, String key) {
        try (Connection c = getConnection()) {
            String sql = "update last_key_type set idKeyType=? where idLastKeyType =?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, key);
            ps.setString(2, s);

            ps.executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException(ex);

        }
    }

    public static String typeKey(String ActivationCode, String Phone) {
        boolean result = false;
        try (Connection c = getConnection()) {
            String sql1 = "select idKey from usb_keys where SerialNum = ? ";
            PreparedStatement ps1 = c.prepareStatement(sql1);
            ps1.setString(1, ActivationCode);
            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()) {

                String id = rs1.getString("idKey");
                String sql = "select idKeyType from last_key_type where DateEvent  = (SELECT MAX(DateEvent)from last_key_type where idKey = ?)";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String keyType = rs.getString("idKeyType");

                    if (keyType.equals("1")){
                        keyType="Сервисный";
                    }else if (keyType.equals("2")){
                        keyType="Основной";
                    }else if (keyType.equals("3")){
                        keyType="Запасной";
                    }

                    sql = "select idOrg from usb_keys where SerialNum = ? ";
                    ps = c.prepareStatement(sql);
                    ps.setString(1, ActivationCode);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        String name = rs.getString(1);

                        sql = "select Name from org where idOrg = ? ";
                        ps = c.prepareStatement(sql);
                        ps.setString(1, name);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            String nameOrg = rs.getString(1);


                            sql = "SELECT DateActivation FROM last_key_activation WHERE DateActivation = (SELECT MAX(DateActivation) FROM last_key_activation  where  ActivationCode like  ?)";
                            ps = c.prepareStatement(sql);
                            ps.setString(1, ActivationCode + "%");
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                String DateActivation = rs.getString(1);

                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                long timeUp = format.parse(DateActivation).getTime();
                                long diff = System.currentTimeMillis() - timeUp;
                                long diffDays = diff / (24 * 60 * 60 * 1000);



                                String[] str_array = DateActivation.split(" ");
                                String stringa = str_array[0];
                                String[] Arr = stringa.split("-");
                                String Str = String.join(".", Arr[2], Arr[1], Arr[0]);


                                info(Phone, ActivationCode, nameOrg,Str, diffDays-diffDays-diffDays, keyType);



                                return null;


                            }
                            break;

                        }


                    }


                }

            }

            if(!result){
                System.out.println("no");
            }




        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return ActivationCode;
    }




    public static String info(String Phone, String ActivationCode, String nameOrg, String DateActivation, long diffDays, String keyType) throws SQLException {


        smsInfo(Phone, ActivationCode, nameOrg, DateActivation, diffDays, keyType);


        return null;
    }


    public static void typeKey(String code, String date, String Phone) throws SQLException {


        try (Connection c = getConnection()) {
            String sq2 = "select idKeyType from last_key_type where DateEvent  = (SELECT MAX(DateEvent)from last_key_type where idKey = ?) ";
            PreparedStatement ps2 = c.prepareStatement(sq2);
            ps2.setString(1, code);
            ResultSet resultSet = ps2.executeQuery();
            while (resultSet.next()) {
                String keyType = resultSet.getString(1);

                String mainKey = "2";
                String spareKey = "3";
                String serviceKey = "1";
                if (keyType.equals(serviceKey)) {
                    serviceKey(Phone);


                    break;

                }
                if (keyType.equals(spareKey)) {


                    spareKey(Phone);
                    break;

                }
                if (keyType.equals(mainKey)) {

                    boolean result = false;
                    try  {
                        String sql1 = "select idKey from usb_keys where SerialNum = ? ";
                        PreparedStatement ps1 = c.prepareStatement(sql1);
                        ps1.setString(1, code);
                        ResultSet rs1 = ps1.executeQuery();

                        while (rs1.next()) {
                            String id = rs1.getString("idKey");
                            keyA(id, date, Phone);
                            result = true;
                        }

                        if(!result){
                            noKeyBd(Phone);
                        }



                    } catch (Exception ex) {

                        throw new RuntimeException(ex);

                    }





                }
                break;

            }


        }
    }

    public static String keyA(String codeKey, String date, String Phone) {



        try (Connection con = getConnection();
             Statement statement = con.createStatement()) {
            PreparedStatement ps = con.prepareStatement("select Code from usb_keys where idKey = ?");
            ps.setString(1, codeKey);
            PreparedStatement ps1 = con.prepareStatement("select ActivationCode from last_key_activation where idKey = ?");
            ps1.setString(1, codeKey);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                byte[] Code = rs.getBytes("Code");
                final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
                byte[] bytes = Code;
                char[] hexChars = new char[bytes.length * 2];
                for (int j = 0; j < bytes.length; j++) {
                    int v = bytes[j] & 0xFF;
                    hexChars[j * 2] = HEX_ARRAY[v >>> 4];
                    hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
                }

                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    String code = rs1.getString("ActivationCode");
                    String b = code.replaceAll(" ", "");
                    char[] charArray = hexChars;
                    String str = String.valueOf(charArray);







                    activationA(str, b, Phone, date, codeKey);
                }


            }


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }


        return codeKey;
    }





    public static void activationA(String serialNumber, String keyName,  String phone,String date, String idKey) throws IOException, InterruptedException {
        LocalDate futureDate = LocalDate.now().plusMonths(12);
        String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        ProcessBuilder builder = new ProcessBuilder("/home/nikolai/Документы/tdes_ecb.exe", keyName, serialNumber, date);
        // задаем переменную окружения руками
        builder.environment().put("COWPATH", "/home/nikolai/Документы/tdes_ecb.exe");
        // указываем перенаправление stderr в stdout, чтобы проще было отлаживать
        builder.redirectErrorStream(true);
        Process process = builder.start();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {


                idPhoneDBA(phone, line, idKey, date);
            }


        }
        // ждем завершения процесса
        process.waitFor();


    }


    public static boolean idPhoneDBA(String Phone, String key, String idKEy, String Date) {
        try (Connection c = getConnection()) {
            String sql1 = "select idPhone from phones where PhoneNumber = ? ";
            PreparedStatement ps1 = c.prepareStatement(sql1);
            ps1.setString(1, Phone);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                String id = rs1.getString("idPhone");
                LocalDateTime futureDate = LocalDateTime.now().plusMonths(12);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 12:00:00");

                String Data = new SimpleDateFormat("yyyy-MM-dd 12:00:00").format(Calendar.getInstance().getTime());
                saveDB(dtf.format(futureDate), key, id, Data, idKEy);
                keySmsSend(key, Phone);
                break;


            }


        } catch (Exception ex) {
            throw new RuntimeException(ex);

        }


        return false;
    }


    public static void saveDB(String DateActivation, String ActivationCode, String idPhone, String DateEvent, String idKey) {


        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try (Connection c = getConnection()) {
            String sql = "INSERT INTO last_key_activation (DateActivation,ActivationCode,idPhone,DateEvent,idKey) values (?,?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, String.valueOf(DateActivation));
            ps.setString(2, ActivationCode);
            ps.setString(3, idPhone);
            Date date = format.parse(DateEvent);
            ps.setTimestamp(4, new Timestamp(date.getTime()));
            ps.setString(5, idKey);
            ps.execute();
        } catch (Exception ex) {
            throw new RuntimeException(ex);

        }


    }


}




/*
/root/KeyGenerator/tdes_ecb.exe
 */

























