package com.app.sms.reader.impl;


import com.app.dao.Dao;
import com.app.dao.DaoImpl;
import com.app.sms.reader.SmsReader;
import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class SimpleSmsReader extends ReadSmsAdministrator implements SmsReader {


    Path path = Paths.get("/var/spool/sms/incoming/");

    @Override
    public String readSms() throws IOException, SQLException {
        WatchService watchService = null;
        try {
            watchService = path.getFileSystem().newWatchService();
            path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);


        } catch (IOException e1) {
            e1.printStackTrace();
        }
        // Infinite loop
        for (; ; ) {
            WatchKey key = null;
            try {
                key = watchService.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Iterations for each event
            for (WatchEvent event : key.pollEvents()) {
                switch (event.kind().name()) {
                    case "OVERFLOW":
                        System.out.println("We lost some events");
                        break;
                    case "ENTRY_CREATE":
                        smsReader();
                        break;

                    case "ENTRY_MODIFY":

                        break;
                }
            }
            // Resetting the key is important for future notifications
            key.reset();


        }
    }


    public void smsReader() throws  NumberFormatException  {


        try {
            List<Long> integers = new ArrayList<>();
            // Reading lines from a file.
            String fileName = defineFileName(path.toString());
            List<String> lines = Files.readAllLines(Paths.get("/var/spool/sms/incoming/" + fileName));
            String line1 = lines.get(0);
            String line2 = lines.get(3);

            String TextMessages = lines.get(13);

            if (line1.equals("From: DanycomInfo") ){
                File file = new File("/var/spool/sms/incoming/" + fileName);


                if (file.delete()) {


                    System.out.printf("del");
                }

            }
           

            //Rider Phone
            line1 = line1;
            Pattern pattern = Pattern.compile("\\d+\\S?\\d*");
            Matcher matcher = pattern.matcher(line1);
            while (matcher.find()) {
                String Phone = matcher.group(0);
                if (Phone.replaceAll("\\D", " ").length() == Phone.length()) {


                    try {
                        integers.add(Long.parseLong(Phone));


                    } catch (NumberFormatException e) {


                    }


                }


                String Data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());



                Dao dao = new DaoImpl();
                dao.phone(Phone, TextMessages, Data);


                //Delete after reading
                File file = new File("/var/spool/sms/incoming/" + fileName);


                if (file.delete()) {


                }


                break;


            }


        } catch (IndexOutOfBoundsException e) {


        } catch (IOException e) {

        }

    }


    private String defineFileName(String dirpas) {
        File file = new File(dirpas);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File a = files[i];
            String fileName = a.getName();
            return fileName;
        }
        return null;
    }


}

