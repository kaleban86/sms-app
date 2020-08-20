package com.app.sms.sending.sms;


import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.Properties;

import static com.app.dao.DaoImpl.getConnection;

public class MailSms implements Runnable {


    static final String ENCODING = "UTF-8";

    public static void mailSend() throws UnsupportedEncodingException, MessagingException {


        String subject = "sms-app";
        String content = "Error-connect-msql";
        String smtpHost = "smtp.gmail.com";
        String address = "nikolaignap@gmail.com";
        String login = "nikolaignap@gmail.com";
        String password = "kaleban530433";
        String smtpPort = "465";
        sendSimpleMessage(login, password, address, address, content, subject, smtpPort, smtpHost);

    }


    public static void sendSimpleMessage(String login, String password, String from, String to, String
            content, String subject, String smtpPort, String smtpHost)
            throws MessagingException, UnsupportedEncodingException, AddressException {
        Authenticator auth = new MyAuthenticator(login, password);

        Properties props = System.getProperties();
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.mime.charset", ENCODING);
        Session session = Session.getDefaultInstance(props, auth);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setSubject(subject);
        msg.setText(content);
        Transport.send(msg);
    }


    static class MyAuthenticator extends Authenticator {
        private String user;
        private String password;

        MyAuthenticator(String user, String password) {
            this.user = user;
            this.password = password;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            String user = this.user;
            String password = this.password;
            return new PasswordAuthentication(user, password);
        }
    }


    public void run() {
        String phoneanmber = "79827956841";
        try {

            try (Connection c = getConnection()) {
                String sql = "Select Count(*) From phones WHERE PhoneNumber = ? AND Admin = 1";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, phoneanmber);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int COUNTphonenanmber = rs.getInt(1);


                }
                rs.next();

            } catch (Exception ex) {
                mailSend();
                throw new RuntimeException(ex);

            }


        } catch (RuntimeException | UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
        }


    }




}

