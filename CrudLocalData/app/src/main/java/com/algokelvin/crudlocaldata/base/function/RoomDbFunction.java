package com.algokelvin.crudlocaldata.base.function;

import android.app.Activity;

import com.algokelvin.crudlocaldata.db.entity.User;
import com.algokelvin.crudlocaldata.db.task.UserTask;
import com.algokelvin.crudlocaldata.db.task.inter.InsertUserTask;
import com.algokelvin.crudlocaldata.dummy.DataUserDummy;

import java.util.List;

public class RoomDbFunction {
    private final UserTask task;

    public RoomDbFunction(UserTask task) {
        this.task = task;
    }

    public void insertToRoom(InsertUserTask insertUserTask) {
        List<User> data = DataUserDummy.dataUserSampleOne();
        task.insert(data, insertUserTask);
    }
}
