package com.example.demoooooooo.services;

import com.example.demoooooooo.entities.Metric;
import com.example.demoooooooo.entities.UserEntity;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.configurationprocessor.json.JSONException;

public interface IUserSiervice  {


    UserEntity createUser(UserEntity userEntity);

    UserEntity updateUser(UserEntity userEntity);

    UserEntity insert(UserEntity userEntity, Long id);
    Optional<UserEntity> findById(Long id);

    List<UserEntity> findAll();

    List<UserEntity> findName(String name);
    List<UserEntity> findAddress(String add);
    List<UserEntity> findPhone(String phone);

    String abcdef();

    List<Metric> getMetrics() throws FileNotFoundException, ParseException, JSONException;
}
