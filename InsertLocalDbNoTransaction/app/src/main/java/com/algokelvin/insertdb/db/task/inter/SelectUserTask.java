package com.algokelvin.insertdb.db.task.inter;

import com.algokelvin.insertdb.db.entity.User;

import java.util.List;

public interface SelectUserTask {
    void getListUser(List<User> listUser);
}
