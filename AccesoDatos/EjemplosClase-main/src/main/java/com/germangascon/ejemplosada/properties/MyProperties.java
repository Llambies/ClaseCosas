package com.germangascon.ejemplosada.properties;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class MyProperties {
    private final Properties properties;
    private static final String FILE_NAME = "config.properties";
    public MyProperties() {
        properties = new Properties();

        try (FileReader fr = new FileReader(FILE_NAME)) {
            properties.load(fr);
        } catch (IOException ioe) {
        }
        String passwd = properties.getProperty("passwd", "1234");
        System.out.println(passwd);

        properties.setProperty("passwd", passwd);
        try (FileWriter fw = new FileWriter(FILE_NAME)) {
            properties.store(fw,"");
        } catch (IOException ioe) {

        }
    }
}
