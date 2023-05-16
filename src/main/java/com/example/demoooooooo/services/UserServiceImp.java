package com.example.demoooooooo.services;

import com.example.demoooooooo.entities.Metric;
import com.example.demoooooooo.entities.UserEntity;
import com.example.demoooooooo.repositories.UserRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements IUserSiervice {

    //BEAN
    @Autowired
    UserRepository userRepository;

    // CREATE USER
    @Override
    public UserEntity createUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    // UPDATE USER
    @Override
    public UserEntity updateUser(UserEntity userEntityToUpdate) {
        UserEntity userEntity1 = new UserEntity();
        if (userEntityToUpdate.getId() != null) {
            userEntity1.setId(userEntityToUpdate.getId());
            userEntity1.setAddress(userEntityToUpdate.getAddress());
            userEntity1.setName(userEntityToUpdate.getName());
            userEntity1.setGender(userEntityToUpdate.getGender());
            userEntity1.setPhone(userEntityToUpdate.getPhone());
            userRepository.save(userEntity1);
        }
        return userEntity1;
    }

    // INSERT IF YOU COULD NOT FIND TO UPDATE
    @Override
    public UserEntity insert(UserEntity userEntity, Long id) {

        UserEntity userEntity1 = new UserEntity();

        userEntity1.setId(id);
        userEntity1.setName(userEntity.getName());
        userEntity1.setAddress(userEntity.getAddress());
        userEntity1.setGender(userEntity.getGender());
        userEntity1.setPhone(userEntity.getPhone());
        userRepository.insertById(
            userEntity1.getId(),
            userEntity1.getName(),
            userEntity1.getAddress(),
            userEntity1.getGender(),
            userEntity1.getPhone());
        return userRepository.save(userEntity1);
    }

    // FIND BY ID
    @Override
    public Optional<UserEntity> findById(Long id) {
        if (id != null) {
            return userRepository.findById(id);
        }
        return null;
    }


    // FIND ALL
    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    //FIND BY NAME
    @Override
    public List<UserEntity> findName(String name) {
        if (name != null) {
            return userRepository.findAllByName(name);
        }
        return null;
    }

    //FIND BY ADDRESS
    @Override
    public List<UserEntity> findAddress(String add) {
        return userRepository.findAllByAddress(add);
    }

    //FIND BY PHONE
    @Override
    public List<UserEntity> findPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public String abcdef() {
        System.out.println(DateFormatUtils.format(new Date(), "HH:mm dd-MM-yyyy"));
        return DateFormatUtils.format(new Date(), "HH:mm dd-MM-yyyy");
    }

    @Override
    public List<Metric> getMetrics() {
        List<Metric> metrics = new ArrayList<>();

        String parser;
        try {
            parser = new String(Files.readAllBytes(Paths.get("D:\\Java_code\\Projects\\demoooooooo\\metrics.json")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonObject inputObject = JsonParser.parseString(parser).getAsJsonObject();

        try {
            JsonArray rows = inputObject.getAsJsonArray("rows");
            JsonArray panels;
            JsonObject object;
            JsonArray targets;
            for (JsonElement row : rows) {
                panels = row.getAsJsonObject().getAsJsonArray("panels");
                for (JsonElement panel : panels) {
                    List<String> lstQuery = new ArrayList<>();
                    object = panel.getAsJsonObject();
                    targets = object.getAsJsonArray("targets");
                    if (targets != null) {
                        for (JsonElement target : targets) {
                            lstQuery.add(target.getAsJsonObject().get("expr").getAsString());
                        }
                        metrics.add(new Metric(object.get("title").getAsString(), object.get("type").getAsString(), lstQuery));
                    } else {
                        metrics.add(new Metric(object.get("title").getAsString(), object.get("type").getAsString(), new ArrayList<>()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return metrics;
    }
}
