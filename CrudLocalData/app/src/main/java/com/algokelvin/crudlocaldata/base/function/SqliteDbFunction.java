package com.algokelvin.crudlocaldata.base.function;

import com.algokelvin.crudlocaldata.db.AppDbSqlite;
import com.algokelvin.crudlocaldata.db.entity.UserSqlite;
import com.algokelvin.crudlocaldata.dummy.DataUserDummy;

import java.util.List;

public class SqliteDbFunction {
    private final AppDbSqlite dbSqlite;

    public SqliteDbFunction(AppDbSqlite dbSqlite) {
        this.dbSqlite = dbSqlite;
    }

    public void insertToSqlite() {
        List<UserSqlite> data = DataUserDummy.dataUserSqliteSampleOne();
        dbSqlite.insertMultipleData(data);
    }
}
