package com.app.sms.sending.sms;

import java.io.*;
import java.sql.*;

import static com.app.dao.DaoImpl.getConnection;

public class TerminalSendSMS     implements SendSms {




    public static String noKeyBd(String Phoneanmber) throws SQLException, IOException {



        String bash = "bash";
        String cc = "-c";
        String send = "sendsms ";
        String gap = " ";
        String text = "Ошибка! Данный номер USB ключа не найден. ";
        String main = send + gap + Phoneanmber + gap + "'" + text + "'";


        updateAnswer(text);
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(new String[]{bash, cc, main});
        } catch (IOException ex) {
            ex.printStackTrace();
        }



        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;

        while (true) {
            try {
                if (!((line = input.readLine()) != null)) break;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println(line);
        }

        int exitVal = 0;
        try {
            exitVal = p.waitFor();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }


       System.out.println("Exited with error code " + exitVal);



        return text;
    }



    public static boolean nouKeySms(String Phoneanmber) throws SQLException {


                String bash = "bash";
                String cc = "-c";
                String send = "sendsms ";
                String gap = " ";
                String text = "Ошибка! Введите номер USB ключа. ";
                String main = send + gap + Phoneanmber + gap + "'" + text + "'";
                updateAnswer(text);
                Process p = null;
                try {
                    p = Runtime.getRuntime().exec(new String[]{bash, cc, main});

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = null;

                while (true) {
                    try {
                        if (!((line = input.readLine()) != null)) break;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println(line);
                }

                int exitVal = 0;
                try {
                    exitVal = p.waitFor();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.out.println("Exited with error code " + exitVal);







        return false;
    }





    public static boolean smsvid5(String Phoneanmber) throws SQLException {

        System.out.println("dddd");

        String bash = "bash";
        String cc = "-c";
        String send = "sendsms ";
        String gap = " ";
        String text = "nnnn | nnnn? | nnnn dd.mm.yyyy | nnnn on / off";
        String main = send + gap + Phoneanmber + gap + "'" + text + "'";
        updateAnswer(text);
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(new String[]{bash, cc, main});

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;

        while (true) {
            try {
                if (!((line = input.readLine()) != null)) break;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println(line);
        }

        int exitVal = 0;
        try {
            exitVal = p.waitFor();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Exited with error code " + exitVal);


        return false;
    }


    public static boolean spareKey(String Phoneanmber) throws SQLException {


        String bash = "bash";
        String cc = "-c";
        String send = "sendsms ";
        String gap = " ";
        String text = "Ошибка! Данный USB ключ запасной, можно акитивировать только основной!";
        String main = send + gap + Phoneanmber + gap + "'" + text + "'";
        updateAnswer(text);
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(new String[]{bash, cc, main});

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;

        while (true) {
            try {
                if (!((line = input.readLine()) != null)) break;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println(line);
        }

        int exitVal = 0;
        try {
            exitVal = p.waitFor();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Exited with error code " + exitVal);


        return false;
    }


    public static boolean serviceKey(String Phoneanmber) throws SQLException {


        String bash = "bash";
        String cc = "-c";
        String send = "sendsms ";
        String gap = " ";
        String text = "Ошибка! Данный USB ключ Вам запрещено активировать.";
        String main = send + gap + Phoneanmber + gap + "'" + text + "'";
        updateAnswer(text);
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(new String[]{bash, cc, main});

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;

        while (true) {
            try {
                if (!((line = input.readLine()) != null)) break;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println(line);
        }

        int exitVal = 0;
        try {
            exitVal = p.waitFor();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Exited with error code " + exitVal);


        return false;
    }






    public static boolean oldKeySend(String Phoneanmber, String codeOld,String data) throws SQLException {

                String bash = "bash";
                String cc = "-c";
                String send = "sendsms ";
                String gap = " ";
                String text = "Код активации: " +codeOld+", до "+ data;
                String main = send + gap + Phoneanmber + gap + "'" + text + "'";
                updateAnswer(text);
                Process p = null;
                try {
                    p = Runtime.getRuntime().exec(new String[]{bash, cc, main});

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = null;

                while (true) {
                    try {
                        if (!((line = input.readLine()) != null)) break;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println(line);
                }

                int exitVal = 0;
                try {
                    exitVal = p.waitFor();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                System.out.println("Exited with error code " + exitVal);

                return false;
            }





    public static boolean smsInfo(String Phoneanmber, String ActivationCode, String nameOrg, String DateActivation, long diffDays, String keyType) throws SQLException {


        String bash = "bash";
        String cc = "-c";
        String send = "sendsms ";
        String gap = " ";
        String text = ActivationCode + "," + nameOrg + ","+DateActivation+", "+diffDays+" дней ," + keyType;
        String main = send + gap + Phoneanmber + gap + "'" + text + "'";
        updateAnswer(text);
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(new String[]{bash, cc, main});

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;

        while (true) {
            try {
                if (!((line = input.readLine()) != null)) break;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println(line);
        }

        int exitVal = 0;
        try {
            exitVal = p.waitFor();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Exited with error code " + exitVal);


        return false;
    }

    public static boolean typKeySpare(String Phoneanmber, String key) throws SQLException {


        String bash = "bash";
        String cc = "-c";
        String send = "sendsms ";
        String gap = " ";
        String text = "Ключ " + key + "сменен на Запасной.";
        String main = send + gap + Phoneanmber + gap + "'" + text + "'";
        updateAnswer(text);
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(new String[]{bash, cc, main});

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;

        while (true) {
            try {
                if (!((line = input.readLine()) != null)) break;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println(line);
        }

        int exitVal = 0;
        try {
            exitVal = p.waitFor();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Exited with error code " + exitVal);


        return false;
    }

    public static boolean typKeyMain(String Phoneanmber, String key) throws SQLException {


        String bash = "bash";
        String cc = "-c";
        String send = "sendsms ";
        String gap = " ";
        String text = "Ключ " + key + "сменен на Основной.";
        String main = send + gap + Phoneanmber + gap + "'" + text + "'";
        updateAnswer(text);
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(new String[]{bash, cc, main});

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;

        while (true) {
            try {
                if (!((line = input.readLine()) != null)) break;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println(line);
        }

        int exitVal = 0;
        try {
            exitVal = p.waitFor();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Exited with error code " + exitVal);


        return false;
    }


    public static boolean keySmsSend(String ActivationCode, String Phoneanmber) throws SQLException {


        String bash = "bash";
        String cc = "-c";
        String send = "sendsms ";
        String gap = " ";
        String text = "Код ключа " + ActivationCode;
        String main = send + gap + Phoneanmber + gap + "'" + text + "'";
        updateAnswer(text);
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(new String[]{bash, cc, main});

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;

        while (true) {
            try {
                if (!((line = input.readLine()) != null)) break;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println(line);
        }

        int exitVal = 0;
        try {
            exitVal = p.waitFor();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Exited with error code " + exitVal);


        return false;
    }

    public static boolean hor24(String Phoneanmber) throws SQLException {

                String bash = "bash";
                String cc = "-c";
                String send = "sendsms ";
                String gap = " ";
                String text = "Превышен лимит обращения к сервису.";
                String main = send + gap + Phoneanmber + gap + "'" + text + "'";
                updateAnswer(text);

                Process p = null;

                try {
                    p = Runtime.getRuntime().exec(new String[]{bash, cc, main});


                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = null;

                while (true) {
                    try {
                        if (!((line = input.readLine()) != null)) break;

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    System.out.println(line);
                }

                int exitVal = 0;
                try {
                    exitVal = p.waitFor();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                System.out.println("Exited with error code " + exitVal);




        return false;

    }




    public static boolean updateAnswer(String update) throws SQLException {

        try (Connection c = getConnection()) {
            PreparedStatement statement = c.prepareStatement("UPDATE log_sms\n" +
                    "SET Answer= ? where idLog = (SELECT * FROM (SELECT MAX(idLog) FROM log_sms) as t) ");
            statement.setString(1, update);



            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("updated successfully!");
            }


        } catch (Exception ex) {
            throw new RuntimeException(ex);

        }


        return false;


    }








}