package com.app.dao;

import com.app.sms.reader.impl.SimpleSmsReader;


import java.io.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static com.app.sms.reader.impl.ClientReadingSms.clientReadingSms;

public class DaoImpl extends SimpleSmsReader implements Dao {



    //Получение idPhone
    public boolean idPhone24Hour(String Phone, String SMS) {
        try (Connection c = getConnection()) {
            String sql1 = "select idPhone from phones where PhoneNumber = ? ";
            PreparedStatement ps1 = c.prepareStatement(sql1);
            ps1.setString(1, Phone);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                String id = rs1.getString("idPhone");

                id24hour(Phone, id, SMS);


            }


        } catch (Exception ex) {
            throw new RuntimeException(ex);

        }


        return false;
    }


    //Проверка количество sms в сутки
    public  boolean id24hour(String Phone, String idPhone, String SMS) {
        try (Connection c = getConnection()) {
            String sql = "Select count(*) from log_sms WHERE DateEvent >= CURDATE() - INTERVAL 24 hour and idPhone=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, idPhone);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int COUNTphonenanmber = rs.getInt(1);

                if (COUNTphonenanmber < 5) {


                    clientReadingSms(SMS, Phone);


                } else {


                    hor24(Phone);


                    return false;

                }
                return COUNTphonenanmber > 0;


            }


        } catch (Exception ex) {
            throw new RuntimeException(ex);

        }

        return false;
    }


    // Номер в черном Списке ?

    @Override
    public  boolean blackList(String phoneanmberblackList, String SMS) {
        try (Connection c = getConnection()) {
            String sql = "Select Count(*) From phones WHERE PhoneNumber = ? AND InBlacklist = 1; ";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, phoneanmberblackList);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int COUNTphonenanmber = rs.getInt(1);

                if (COUNTphonenanmber > 0) {


                } else {


                    idPhone24Hour(phoneanmberblackList, SMS);


                }
                return COUNTphonenanmber > 0;


            }
            rs.close();
            ps.close();

        } catch (Exception ex) {
            throw new RuntimeException(ex);

        }


        return false;
    }


    //Разбор текста клиента
    @Override
    public boolean smsSearchKeyBdClient(String SMS, String Phone) {
        boolean result = false;
        try (Connection c = getConnection()) {
            String sql1 = "select idKey from usb_keys where SerialNum = ? ";
            PreparedStatement ps1 = c.prepareStatement(sql1);
            ps1.setString(1, SMS);
            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()) {
                String id = rs1.getString("idKey");
                dateActivation(id, Phone);
                result = true;
            }

            if(!result){
                noKeyBd(Phone);
            }



        } catch (Exception ex) {

            throw new RuntimeException(ex);

        }


        return result;
    }


    //Разбор текста admin
    @Override
    public boolean smsSearchKeyBdAdmin(String SMS, String Phone) {
        boolean result = false;
        try (Connection c = getConnection()) {
            String sql1 = "select idKey from usb_keys where SerialNum = ? ";
            PreparedStatement ps1 = c.prepareStatement(sql1);
            ps1.setString(1, SMS);
            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()) {
                String id = rs1.getString("idKey");
                dateActivation(id, Phone);
                result = true;
            }

            if(!result){
                noKeyBd(Phone);
            }



        } catch (Exception ex) {

            throw new RuntimeException(ex);

        }


        return result;
    }

    // Проверяем есть номер в PhoneNumber
    @Override
    public boolean phone(String phone, String SMS, String DateEvent) {
        try (Connection c = getConnection()) {
            String sql = "Select Count(*) From phones WHERE PhoneNumber = ? ";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int COUNTphonenanmber = rs.getInt(1);

                if (COUNTphonenanmber > 0) {


                    String sql1 = "select idPhone from phones where PhoneNumber = ? ";
                    PreparedStatement ps1 = c.prepareStatement(sql1);
                    ps1.setString(1, phone);
                    ResultSet rs1 = ps1.executeQuery();
                    while (rs1.next()) {
                        String idPhone = rs1.getString("idPhone");

                        save(idPhone, phone, SMS, DateEvent);


                    }


                } else {

                    save(phone);
                    idPhone(phone, SMS, DateEvent);


                }
                return COUNTphonenanmber > 0;


            }


        } catch (Exception ex) {
            throw new RuntimeException(ex);

        }


        return false;
    }


    //Сохранение номера в базе
    public static void save(String phoneanumber) throws IOException {
        try (Connection c = getConnection()) {
            String sql = "INSERT INTO phones (PhoneNumber )  VALUES  (?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, phoneanumber);
            ps.execute();
        } catch (Exception ex) {
            throw new RuntimeException(ex);

        }
    }


    //Получение idPhone
    public void idPhone(String Phone, String SMS, String Date) throws SQLException {

        try (Connection c = getConnection()) {
            String sql = "select idPhone from phones where PhoneNumber = ? ";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, Phone);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String idPhone = rs.getString("idPhone");
                save(idPhone, Phone, SMS, Date);


            }


        }

    }


    //Сохранение текста sms,даты,номера телефона,проверка  номера телефона на принадлежность к группе пользователей
    public boolean save(String idPhone, String PhoneNumber, String SMS, String DateEvent) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try (Connection c = getConnection()) {
            // c.setAutoCommit(false);

            String sql = "Select Count(*) From phones WHERE PhoneNumber = ? AND Admin = 1 ";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, PhoneNumber);
            ResultSet rs = ps.executeQuery();
            boolean phoneNumberExists = false;
            while (rs.next()) {
                int COUNTphonenanmber = rs.getInt(1);
                phoneNumberExists = COUNTphonenanmber > 0;
            }
            if (phoneNumberExists) {


                sql = "INSERT INTO log_sms (idPhone, SMS, DateEvent) VALUES(?, ?, ? )";
                ps = c.prepareStatement(sql);
                ps.setString(1, idPhone);
                ps.setString(2, SMS);
                Date date = format.parse(DateEvent);
                ps.setTimestamp(3, new Timestamp(date.getTime()));
                ps.execute();
                ps.close();
                rs.close();


                readSmsAdministrator(SMS, PhoneNumber);
            } else {


                sql = "INSERT INTO log_sms (idPhone, SMS, DateEvent) VALUES (?, ?, ? )";
                ps = c.prepareStatement(sql);
                ps.setString(1, idPhone);
                ps.setString(2, SMS);
                Date date = format.parse(DateEvent);
                ps.setTimestamp(3, new Timestamp(date.getTime()));
                ps.execute();


                blackList(PhoneNumber, SMS);
            }
            // c.commit();

        } catch (Exception ex) {
            throw new RuntimeException(ex);

        }
        return false;
    }



    //Проверка даты активации и типа ключа
    @Override
    public  Boolean dateActivation(String code, String Phone) {
        try (Connection c = getConnection()) {
            String sql1 = "Select Count(*) From last_key_activation WHERE idKey  like  ? ";
            PreparedStatement ps1 = c.prepareStatement(sql1);
            ps1.setString(1, code + "%");
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                int COUNTphonenanmber = rs.getInt(1);


                if (COUNTphonenanmber > 0) {


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


                            String sql3 = "SELECT DateActivation FROM last_key_activation WHERE DateActivation =" +
                                    " (SELECT MAX(DateActivation) FROM last_key_activation where  idKey like  ?)";
                            PreparedStatement ps3 = c.prepareStatement(sql3);
                            ps3.setString(1, code + "%");
                            ResultSet resultSet1 = ps3.executeQuery();
                            while (resultSet1.next()) {
                                String DateActivation = resultSet1.getString(1);


                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                long timeUp = format.parse(DateActivation).getTime();
                                long diff = System.currentTimeMillis() - timeUp;
                                long diffDays = diff / (24 * 60 * 60 * 1000);

                                if (diffDays >= -62) {


                                    key(code, code, Phone, DateActivation);
                                    break;

                                } else {



                                    oldKey(code, Phone);


                                    break;


                                }

                            }

                            break;
                        }


                    }


                }


            }


        } catch (Exception ex) {
            throw new RuntimeException(ex);


        }

        return false;
    }







    public static Boolean oldKey(String codeKey, String phone) {
        try (Connection c = getConnection()) {
            String sql =("select ActivationCode, DateActivation from\n" +
                    "    last_key_activation where idKey =\n" +
                    "         (SELECT max(idKey) from last_key_activation where idKey like  ? OR idKey like  ? )\n" +
                    "order by DateActivation desc  limit 1");
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, codeKey + "%");
            String param2 = null;
            if(codeKey.length()==3){
                param2 ="0"+codeKey;
            }else{
                param2 ="00"+codeKey;
            }
            ps.setString(2, param2 + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String activationCode = rs.getString("ActivationCode");
                String b = activationCode.replaceAll(" ", " ");
                String dateActivation = rs.getString("DateActivation");
                String[] str_array = dateActivation.split(" ");
                String stringa = str_array[0];
                String[] Arr = stringa.split("-");
                String Str = String.join(".", Arr[2], Arr[1], Arr[0]);


                oldKeySend(phone, b, Str);




            }


        } catch (Exception ex) {
            throw new RuntimeException(ex);

        }


        return false;
    }







    //Получение Code  для активации нового ключа
    public static String key(String codeKey, String idKey, String Phone, String DateActivation) {


        try (Connection con = getConnection();
             Statement statement = con.createStatement()) {
            PreparedStatement ps = con.prepareStatement("select Code from usb_keys where idKey = ?");
            ps.setString(1, codeKey);
            PreparedStatement ps1 = con.prepareStatement("select ActivationCode from last_key_activation where idKey = ?");
            ps1.setString(1, idKey);
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


                    idKey(str, b, Phone, DateActivation, codeKey);


                }


            }


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }


        return codeKey;
    }

    public static boolean idKey(String serialNumber, String keyName, String Phone, String DateActivati, String codeKey)  {


        try {
            activationDao(serialNumber, keyName, Phone, DateActivati, codeKey);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return false;
    }


    //Генерация нового ключа активации
    public static void activationDao(String serialNumber, String keyName, String Phone, String DateActivati, String idKey) throws IOException, InterruptedException {
        LocalDate futureDate = LocalDate.now().plusMonths(12);
        String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        ProcessBuilder builder = new ProcessBuilder("/home/nikolai/Документы/tdes_ecb.exe", keyName, serialNumber, formattedDate);
        // задаем переменную окружения руками
        builder.environment().put("COWPATH", "/home/nikolai/Документы/tdes_ecb.exe");
        // указываем перенаправление stderr в stdout, чтобы проще было отлаживать
        builder.redirectErrorStream(true);
        Process process = builder.start();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {


                idPhoneDB(Phone, line, idKey);
            }


        }
        // ждем завершения процесса
        process.waitFor();


    }


    //Получение idPhone, отправка нового ключа пользователю
    public static boolean idPhoneDB(String Phone, String key, String idKEy) {
        try (Connection c = getConnection()) {
            String sql1 = "select idPhone from phones where PhoneNumber = ? ";
            PreparedStatement ps1 = c.prepareStatement(sql1);
            ps1.setString(1, Phone);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                String id = rs1.getString("idPhone");
                LocalDateTime futureDate = LocalDateTime.now().plusMonths(12);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd 12:00:00");

                String Data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                saveDB(dtf.format(futureDate), key, id, Data, idKEy);
                keySmsSend(key, Phone);


                break;


            }


        } catch (Exception ex) {
            throw new RuntimeException(ex);

        }


        return false;
    }


    //Сохранение нового кода ключа в базе
    public static void saveDB(String DateActivation, String ActivationCode, String idPhone, String DateEvent, String idKey)  {

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


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/usb_key", "root", "DtcyfJgznmGhbikf007");


    }


}



/*

/root/KeyGenerator/
DtcyfJgznmGhbikf007
 */




