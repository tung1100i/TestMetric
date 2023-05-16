package com.example.demoooooooo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import com.example.demoooooooo.entities.UserEntity;
import com.example.demoooooooo.repositories.UserRepository;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.ParseException;

@SpringBootApplication
public class DemooooooooApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemooooooooApplication.class, args);
    }


}
