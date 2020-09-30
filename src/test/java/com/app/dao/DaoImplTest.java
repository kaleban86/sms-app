package com.app.dao;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DaoImplTest extends DaoImpl {

    @Test
    public void m(){
        Dao dao = new DaoImpl();
        boolean res = dao.smsSearchKeyBdClient("0000", "79827956841");
        assertFalse(res);

        res = dao.smsSearchKeyBdClient("0012", "79827956841");
        assertTrue(res);


    }

    @Test
    public void phone() {
        Dao dao = new DaoImpl();
        boolean res = dao.phone("79827956841","0015","2017-12-28 12:00:00");
        assertTrue(res);



    }



    @Test
    public void blackList(){

        Dao dao = new DaoImpl();
        boolean res = dao.blackList("79827956841","text");
        assertFalse(res);


    }



}
