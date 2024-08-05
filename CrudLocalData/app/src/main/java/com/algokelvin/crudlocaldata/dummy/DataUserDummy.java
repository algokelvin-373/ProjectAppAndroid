package com.algokelvin.crudlocaldata.dummy;

import com.algokelvin.crudlocaldata.db.entity.User;

import java.util.ArrayList;
import java.util.List;

public class DataUserDummy {
    public static List<User> dataUserSampleOne() {
        List<User> data = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 5; j++) {
                User user = new User();
                if (j == 0) {
                    user.setName("Kelvin");
                    user.setDescription("Android Developer");
                } else if (j == 1) {
                    user.setName("Andy");
                    user.setDescription("Web Developer");
                } else if (j == 2) {
                    user.setName("Vira");
                    user.setDescription("Cyber Security");
                } else if (j == 3) {
                    user.setName("Tiara");
                    user.setDescription("Singer");
                } else {
                    user.setName("Jonny");
                    user.setDescription("Project Manager");
                }
                data.add(user);
            }
        }
        return data;
    }
}
