package com.app.starter;

import com.app.dao.Dao;
import com.app.dao.DaoImpl;
import com.app.sms.reader.SmsReader;
import com.app.sms.reader.impl.SimpleSmsReader;
import com.app.sms.sending.sms.MailSms;
import com.app.sms.writer.SmsWriter;
import com.app.sms.writer.impl.SimpleSmsWriter;
import com.app.text.Decoder;
import com.app.text.decoder.impl.SimpleDecoder;

import com.app.workflow.SmsWorkflow;
import com.app.workflow.impl.SimpleSmsWorkflow;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, MessagingException, SQLException {




        SmsReader smsReader = new SimpleSmsReader();
        Decoder decoder = new SimpleDecoder();
        Dao dao = new DaoImpl();
        SmsWriter smsWriter = new SimpleSmsWriter();








        //MailSms mailSms = new MailSms();

       // mailSms.run();
       // ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        //service.scheduleAtFixedRate(mailSms,7,7, TimeUnit.HOURS);



        SmsWorkflow smsWorkflow = new SimpleSmsWorkflow(smsReader, decoder, dao, smsWriter);
        smsWorkflow.processSms();





    }
}
