package com.algokelvin.insertdb.base;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.insertdb.databinding.ActivityRoomDbBinding;
import com.algokelvin.insertdb.db.AppDb;
import com.algokelvin.insertdb.db.dao.UserDao;
import com.algokelvin.insertdb.db.entity.User;
import com.algokelvin.insertdb.db.task.UserTask;
import com.algokelvin.insertdb.db.task.inter.InsertUserTask;

import java.util.ArrayList;
import java.util.List;

public class RoomDbActivity extends AppCompatActivity implements InsertUserTask {
    private ActivityRoomDbBinding binding;
    private UserDao dao;
    private UserTask task;
    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoomDbBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppDb db = AppDb.getDatabase(this);
        dao = db.userDao();
        task = new UserTask(this);
        initialize(binding);
    }

    private void initialize(ActivityRoomDbBinding binding) {
        binding.btnInsertRoom.setOnClickListener(v -> {
            binding.layoutLoading.setVisibility(View.VISIBLE);
            insertDbRoomTraining();
        });

        binding.btnInsertRoomLoading.setOnClickListener(v -> {
            InsertRoomAsync insertRoomAsync = new InsertRoomAsync(dao);
            insertRoomAsync.execute();
        });
    }

    // Inserting Using Room
    private void insertDbRoomTraining() {
        int count = 200000; // 1.000.000 data

        List<User> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
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
        total = data.size();

        // Insert to Room
        task.insert(data, this);
    }

    @Override
    public void getTimeExecute(long timer) {
        String totalData = "Total: "+ total;
        String time = "Time: "+ timer/1000 +" detik";
        binding.layoutLoading.setVisibility(View.GONE);
        binding.txtResult.setVisibility(View.VISIBLE);
        binding.txtTotalData.setVisibility(View.VISIBLE);
        binding.txtTimeExecution.setVisibility(View.VISIBLE);

        binding.txtTotalData.setText(totalData);
        binding.txtTimeExecution.setText(time);
    }

    public class InsertRoomAsync extends AsyncTask<Void, Integer, Void> {
        private final String startMsg = "Ready to Insert Data";
        private final UserDao userDao;
        private int totalPart = 0;
        private final long start;

        public InsertRoomAsync(UserDao userDao) {
            this.userDao = userDao;
            binding.percentProgressBar.setText(startMsg);
            start = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            int count = 200000; // 1.000.000
            int totalData = count * 5;

            // Method 1: Add One by One Data and Insert to DB Room every 25000 data
            List<User> data = new ArrayList<>();
            for (int i = 0; i < count; i++) {
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

                    // Ready to insert db for total 25000
                    if (data.size() % 25000 == 0) {
                        totalPart += data.size(); // Set total part data
                        userDao.insert(data); // Insert data to Room

                        data = new ArrayList<>(); // Making list data become new object

                        float part = (float) totalPart / totalData;
                        int percent = (int) (part * 100);
                        publishProgress(percent);
                    }
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            // Update UI ProgressBar and TextView
            int progress = values[0];
            String processMsg = startMsg +"\n" +
                    "Data Insert: "+ totalPart +" data \n" +
                    "Progress: "+ progress +" %";
            binding.progressBar.setProgress(progress);
            binding.percentProgressBar.setText(processMsg);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            long end = System.currentTimeMillis();
            long timerExecute = end - start;
            String lastMessage = "Loading Completed \n" +
                    "Time Execute: "+ timerExecute / 1000 + " detik";
            binding.percentProgressBar.setText(lastMessage);
        }
    }
}