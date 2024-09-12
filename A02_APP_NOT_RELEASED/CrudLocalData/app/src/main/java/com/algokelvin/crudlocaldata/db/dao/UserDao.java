package com.algokelvin.crudlocaldata.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.algokelvin.crudlocaldata.db.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<User> user);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Delete
    void deleteUser(User user);

    @Query("DELETE FROM users")
    void deleteAll();
}