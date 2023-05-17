package com.example.demoooooooo.services;

import com.example.demoooooooo.entities.Metric;
import com.example.demoooooooo.entities.Target;
import com.example.demoooooooo.entities.UserEntity;
import com.example.demoooooooo.repositories.UserRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
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
        List<Metric> lstPanel = new ArrayList<>();
        try {
            JsonArray layout = getJsonObject().getAsJsonArray("panels");
            JsonArray panels;
            JsonArray lstPanels;
            int i = 0;
            while (i < layout.size()) {
                panels = layout.get(i).getAsJsonObject().getAsJsonArray("panels");
                List<Target> lstMetricsPanel = new ArrayList<>();
                if (panels.size() == 0) {
                    int j = i + 1;
                    if (!layout.get(j).getAsJsonObject().get("type").getAsString().equals("row")) {
                        List<Target> lstMetrics = new ArrayList<>();
                        while ((j < layout.size() - 1) && !layout.get(j).getAsJsonObject().get("type").getAsString().equals("row")) {
                            JsonArray targets = layout.get(j).getAsJsonObject().getAsJsonArray("targets");
                            List<String> lstQuery = new ArrayList<>();
                            if (!(targets.isEmpty() || targets.isJsonNull())) {
                                targets.forEach(item -> lstQuery.add(item.getAsJsonObject().get("expr").getAsString()));
                                lstMetrics.add(new Target(layout.get(j).getAsJsonObject().get("title").getAsString(),
                                    layout.get(j).getAsJsonObject().get("type").getAsString(), lstQuery));
                            }
                            j++;
                        }
                        lstPanel.add(new Metric(layout.get(i).getAsJsonObject().get("title").getAsString(),
                            layout.get(i).getAsJsonObject().get("type").getAsString(), lstMetrics));
                        i = j - 1;
                    }
                } else {
                    lstPanels = layout.get(i).getAsJsonObject().getAsJsonArray("panels");
                    for (JsonElement nextPanel : lstPanels) {
                        List<String> lstQueryMetric = new ArrayList<>();
                        JsonArray targets = nextPanel.getAsJsonObject().getAsJsonArray("targets");
                        if (!(targets.isEmpty() || targets.isJsonNull())) {
                            targets.forEach(item -> lstQueryMetric.add(item.getAsJsonObject().get("expr").getAsString()));
                        }
                        lstMetricsPanel.add(new Target(nextPanel.getAsJsonObject().get("title").getAsString(),
                            nextPanel.getAsJsonObject().get("type").getAsString(), lstQueryMetric));
                    }
                    lstPanel.add(new Metric(layout.get(i).getAsJsonObject().get("title").getAsString(),
                        layout.get(i).getAsJsonObject().get("type").getAsString(), lstMetricsPanel));
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstPanel;
    }

    private JsonObject getJsonObject() {
        String parser;
        try {
            parser = new String(Files.readAllBytes(Paths.get("D:\\Java_code\\Projects\\demoooooooo\\metrics.json")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return JsonParser.parseString(parser).getAsJsonObject();
    }
}
