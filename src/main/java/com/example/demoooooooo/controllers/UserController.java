package com.example.demoooooooo.controllers;

import com.example.demoooooooo.entities.Metric;
import com.example.demoooooooo.entities.UserEntity;
import com.example.demoooooooo.services.IUserSiervice;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.*;


import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/users")
//@Configurable
public class UserController {


    @Autowired
    private IUserSiervice iUserSiervice;


    // CREATE USER
    @PostMapping("/new")
    public UserEntity createNew(@RequestBody UserEntity user) {
        return iUserSiervice.createUser(user);
    }

    // UPDATE USER
    @PutMapping("/edit/{id}")
    public UserEntity updateByID(@RequestBody UserEntity updateUserById, @PathVariable Long id) {
        if (updateUserById.getId() != null) {
            return iUserSiervice.updateUser(updateUserById);
        } else {
            return insertToUpdate(updateUserById,id);  // NEXT ROW
        }
    }

    // INSERT IF YOU COULD NOT FIND TO UPDATE
    @PostMapping("/edit/{id}")
    public UserEntity insertToUpdate(@RequestBody UserEntity updateUserById, @PathVariable Long id) {
        return iUserSiervice.insert(iUserSiervice.findById(id).get(),id);
    }

    //FIND BY ADDRESS
    @GetMapping("/search-name/{name}")
    public List<UserEntity> findAllByname(@PathVariable String name) {
        return iUserSiervice.findName(name);
    }


    //FIND BY ADDRESS
    @GetMapping("/search-address/{add}")
    public List<UserEntity> findAllByAddress(@PathVariable String add) {
        return iUserSiervice.findAddress(add);
    }

    //FIND BY PHONE
    @GetMapping("/search-phone/{phone}")
    public List<UserEntity> findByPhone(@PathVariable String phone) {
        return iUserSiervice.findPhone(phone);
    }

    //FIND ALL
    @GetMapping("/get-list-metric")
    public List<Metric> getListMetric() {
        try {
            return iUserSiervice.getMetrics();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}