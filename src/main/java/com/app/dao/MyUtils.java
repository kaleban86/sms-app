package com.app.dao;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyUtils {


    public static String readConfig(String filePath) {
        try {
            URL url = MyUtils.class.getResource(filePath);
            Path path = Paths.get(url.toURI());
            String sql = new String(Files.readAllBytes(path));
            return sql;
        }catch(Exception ex){
            throw  new RuntimeException(ex);
        }
    }

}
