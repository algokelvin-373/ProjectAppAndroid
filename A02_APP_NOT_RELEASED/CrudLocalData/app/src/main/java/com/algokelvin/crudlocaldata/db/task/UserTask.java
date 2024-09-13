package com.algokelvin.crudlocaldata.db.task;

import android.content.Context;
import android.os.AsyncTask;

import com.algokelvin.crudlocaldata.db.AppDb;
import com.algokelvin.crudlocaldata.db.dao.UserDao;
import com.algokelvin.crudlocaldata.db.entity.User;
import com.algokelvin.crudlocaldata.db.task.inter.DeleteUserTask;
import com.algokelvin.crudlocaldata.db.task.inter.InsertUserTask;
import com.algokelvin.crudlocaldata.db.task.inter.SelectUserTask;

import java.util.List;

public class UserTask {
    private final UserDao dao;

    public UserTask(Context context) {
        AppDb db = AppDb.getDatabase(context);
        this.dao = db.userDao();
    }

    public void insert(List<User> listUser, InsertUserTask task) {
        new InsertAsyncTask(dao, listUser, task).execute();
    }

    public void getAllUsers(SelectUserTask task) {
        new SelectAsyncTask(dao, task).execute();
    }

    public void deleteUser(User user, DeleteUserTask task) {
        new DeleteAsyncTask(dao, user, task).execute();
    }

    public void deleteAllUser(DeleteUserTask task) {
        new DeleteAllAsyncTask(dao, task).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<Void, Void, Void> {
        private final UserDao userDao;
        private final List<User> listUser;
        private InsertUserTask task;

        public InsertAsyncTask(UserDao userDao, List<User> listUser, InsertUserTask task) {
            this.userDao = userDao;
            this.listUser = listUser;
            this.task = task;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            long start = System.currentTimeMillis();
            userDao.insert(listUser);
            long end = System.currentTimeMillis();
            System.out.println("DB Test: Success Insert - "+ (end - start)+" ms");
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            task.getSuccessInsert("Success Insert Data");
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

    private static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private final UserDao userDao;
        private final DeleteUserTask task;
        private final User user;

        public DeleteAsyncTask(UserDao userDao, User user, DeleteUserTask task) {
            this.userDao = userDao;
            this.task = task;
            this.user = user;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteUser(user);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            task.successDelete("Success Delete Data");
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final UserDao userDao;
        private final DeleteUserTask task;

        public DeleteAllAsyncTask(UserDao userDao, DeleteUserTask task) {
            this.userDao = userDao;
            this.task = task;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            task.successDelete("Success Delete All Users");
        }
    }
}
