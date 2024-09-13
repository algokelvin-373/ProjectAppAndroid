package com.algokelvin.crudlocaldata.db.task.inter;

import com.algokelvin.crudlocaldata.db.entity.User;

import java.util.List;

public interface SelectUserTask {
    void getListUser(List<User> listUser);
}
