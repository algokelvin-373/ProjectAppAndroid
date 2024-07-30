package com.algokelvin.crudlocaldata.db.task;

import android.content.Context;
import android.os.AsyncTask;

import com.algokelvin.crudlocaldata.db.AppDb;
import com.algokelvin.crudlocaldata.db.dao.UserDao;
import com.algokelvin.crudlocaldata.db.entity.User;
import com.algokelvin.crudlocaldata.db.task.inter.SelectUserTask;

import java.util.List;

public class UserTask {
    private final UserDao dao;

    public UserTask(Context context) {
        AppDb db = AppDb.getDatabase(context);
        this.dao = db.userDao();
    }

    public void insert(List<User> listUser) {
        new InsertAsyncTask(dao, listUser).execute();
    }

    public void getAllUsers(SelectUserTask task) {
        new SelectAsyncTask(dao, task).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<Void, Void, Void> {
        private final UserDao userDao;
        private final List<User> listUser;

        public InsertAsyncTask(UserDao userDao, List<User> listUser) {
            this.userDao = userDao;
            this.listUser = listUser;
        }

        /*@Override
        protected Void doInBackground(final User... users) {
            userDao.insert(users);
            System.out.println("DB Test: Success Insert");
            return null;
        }*/

        @Override
        protected Void doInBackground(Void... voids) {
            long start = System.currentTimeMillis();
            userDao.insert(listUser);
            long end = System.currentTimeMillis();
            System.out.println("DB Test: Success Insert - "+ (end - start)+" ms");
            return null;
        }
    }

    private static class SelectAsyncTask extends AsyncTask<Void, Void, List<User>> {
        private final UserDao userDao;
        private final SelectUserTask task;

        public SelectAsyncTask(UserDao userDao, SelectUserTask task) {
            this.userDao = userDao;
            this.task = task;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            return userDao.getAllUsers();
        }

        @Override
        protected void onPostExecute(List<User> users) {
            super.onPostExecute(users);
            task.getListUser(users);
        }
    }
}
