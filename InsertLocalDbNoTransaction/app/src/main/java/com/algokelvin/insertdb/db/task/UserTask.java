package com.algokelvin.insertdb.db.task;

import android.content.Context;
import android.os.AsyncTask;

import com.algokelvin.insertdb.db.AppDb;
import com.algokelvin.insertdb.db.dao.UserDao;
import com.algokelvin.insertdb.db.entity.User;
import com.algokelvin.insertdb.db.task.inter.InsertUserTask;
import com.algokelvin.insertdb.db.task.inter.SelectUserTask;

import java.util.List;

public class UserTask {
    private final UserDao dao;
    private static long timer;

    public UserTask(Context context) {
        AppDb db = AppDb.getDatabase(context);
        this.dao = db.userDao();
    }

    public void insert(List<User> listUser, InsertUserTask insert) {
        new InsertAsyncTask(dao, listUser, insert).execute();
    }

    public void getAllUsers(SelectUserTask task) {
        new SelectAsyncTask(dao, task).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<Void, Void, Void> {
        private final UserDao userDao;
        private final List<User> listUser;
        private InsertUserTask insert;

        public InsertAsyncTask(UserDao userDao, List<User> listUser, InsertUserTask insert) {
            this.userDao = userDao;
            this.listUser = listUser;
            this.insert = insert;
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
            timer = end - start;
            System.out.println("DB Test: Success Insert - "+ (end - start)+" ms");
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            insert.getTimeExecute(timer);
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
