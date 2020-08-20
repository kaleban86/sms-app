package com.app.sms.sending.sms;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class SendSmsBalance {


    public void balance() throws InterruptedException {
        String Phoneanmber = "s111";
        String bash = "bash";
        String cc = "-c";
        String send = "sendsms ";
        String gap = " ";
        String text = "11";
        String main = send + gap + Phoneanmber + gap + "'" + text + "'";


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


    }




    public static boolean voidBalance() throws InterruptedException {

        try (Connection c = getConnection1()) {
            String sql1 = "SELECT SMS FROM log_sms WHERE idLog=(SELECT MAX(idLog) FROM log_sms order by SMS limit 1)";
            PreparedStatement ps1 = c.prepareStatement(sql1);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                String sms = rs1.getString("SMS");


              balanceSend(sms);


            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);

        }


        return false;
    }

    private static Connection getConnection1() throws SQLException {


        return DriverManager.getConnection("jdbc:mysql://localhost:3306/usb_key", "root", "DtcyfJgznmGhbikf007");


    }


    public static void  balanceSend(String sms) throws InterruptedException {
        String Phoneanmber = "79827956841";
        String bash = "bash";
        String cc = "-c";
        String send = "sendsms ";
        String gap = " ";
        String text = sms;
        String main = send + gap + Phoneanmber + gap + "'" + text + "'";


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


    }




}



