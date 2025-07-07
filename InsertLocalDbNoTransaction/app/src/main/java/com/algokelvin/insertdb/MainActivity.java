package com.algokelvin.insertdb;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.algokelvin.insertdb.base.RoomDbActivity;
import com.algokelvin.insertdb.base.SqliteDbActivity;
import com.algokelvin.insertdb.databinding.ActivityMainBinding;
import com.algokelvin.insertdb.db.AppDbSqlite;
import com.algokelvin.insertdb.db.entity.DataTest;
import com.algokelvin.insertdb.db.entity.User;
import com.algokelvin.insertdb.db.entity.UserSqlite;
import com.algokelvin.insertdb.db.task.UserTask;
import com.algokelvin.insertdb.db.task.inter.SelectUserTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.Manifest;

public class MainActivity extends AppCompatActivity {
    private AppDbSqlite db;
    private ActivityMainBinding binding;
    private static final int REQUEST_CODE_READ_STORAGE = 1;
    private static final int REQUEST_MANAGE_EXTERNAL_STORAGE = 2;
    private UserTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        task = new UserTask(this);
        db = new AppDbSqlite(this);
        setContentView(binding.getRoot());
        initialize(binding);
    }

    private void initialize(ActivityMainBinding binding) {

        binding.btnRoom.setOnClickListener(v -> {
            Intent toDbRoom = new Intent(this, RoomDbActivity.class);
            startActivity(toDbRoom);
        });

        binding.btnSqlite.setOnClickListener(v -> {
            Intent toDbSqlite = new Intent(this, SqliteDbActivity.class);
            startActivity(toDbSqlite);
        });

        binding.btnAction.setOnClickListener(v -> {
            actionReadCsvInsertDbRoom();
//            ProgressBarAsync progressBarAsync = new ProgressBarAsync();
//            progressBarAsync.execute();
        });

        binding.btnInsertSqlite.setOnClickListener(v -> {
            insertDbSqliteTraining();
        });

        binding.btnShow.setOnClickListener(v -> {
            task.getAllUsers(new SelectUserTask() {
                @Override
                public void getListUser(List<User> listUser) {
                    int count = listUser.size();
                    if (count > 0) {
                        for (User user: listUser) {
                            System.out.println(user.getId()+" -- "+user.getName());
                        }
                        Toast.makeText(MainActivity.this, "You have "+count+" data", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Data still empty", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    private void actionReadCsvInsertDbRoom() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_STORAGE);
            } else {
                readFileCsv();
            }
        } else {
            readFileCsv();
        }
    }

    // Inserting using SQLite
    private void insertDbSqliteTraining() {
        int count = 200000; // 1000000

        List<UserSqlite> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < 5; j++) {
                UserSqlite user = new UserSqlite();
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

        long start = System.currentTimeMillis();
        db.insertMultipleData(data);
        long end = System.currentTimeMillis();

        System.out.println("Time Insert: "+ (end - start));
        Toast.makeText(this, "Add : "+ data.size() +" \n" +
                "Time : "+ (end - start) +" ms", Toast.LENGTH_SHORT).show();
    }

    private void readFileCsv() {
        String directory = "trainingcsv/data/";
        String fileName = "filetester.csv";
        File csvFile = new File("/storage/emulated/0/trainingcsv/data/filetester_all.csv"); // Path ke file CSV di penyimpanan internal
        String line = "";
        String csvSplitBy = "\\|";

        BufferedReader br = null;
        FileReader fr = null;

        try {
            fr = new FileReader(csvFile);
            br = new BufferedReader(fr);

            long start = System.currentTimeMillis();
            List<DataTest> dataTestList = new ArrayList<>();
            int count = 1;
            int total = 0;
            while ((line = br.readLine()) != null) {
                String[] dataArray = line.split(csvSplitBy);

                if (count == 1) {
                    total = Integer.parseInt(dataArray[1]);
                } else {
                    DataTest data = new DataTest();
                    data.setData1(!dataArray[0].isEmpty() ? dataArray[0] : "");
                    data.setData2(!dataArray[1].isEmpty() ? dataArray[1] : "");
                    data.setData3(!dataArray[2].isEmpty() ? dataArray[2] : "");
                    data.setData4(!dataArray[3].isEmpty() ? dataArray[3] : "");
                    data.setData5(!dataArray[4].isEmpty() ? dataArray[4] : "");
                    data.setData6(!dataArray[5].isEmpty() ? dataArray[5] : "");
                    data.setData7(!dataArray[6].isEmpty() ? dataArray[6] : "");
                    data.setData8(!dataArray[7].isEmpty() ? dataArray[7] : "");
                    data.setData9(!dataArray[8].isEmpty() ? dataArray[8] : "");
                    data.setData10(!dataArray[9].isEmpty() ? dataArray[9] : "");
                    data.setData11(!dataArray[10].isEmpty() ? dataArray[10] : "");
                    data.setData12(!dataArray[11].isEmpty() ? dataArray[11] : "");
                    data.setData13(!dataArray[12].isEmpty() ? dataArray[12] : "");
                    data.setData14(!dataArray[13].isEmpty() ? dataArray[13] : "");
                    data.setData15(!dataArray[14].isEmpty() ? dataArray[14] : "");
                    data.setData16(!dataArray[15].isEmpty() ? dataArray[15] : "");
                    data.setData17(!dataArray[16].isEmpty() ? dataArray[16] : "");
                    //data.setData18(!dataArray[17].isEmpty() ? dataArray[17] : "");

                    dataTestList.add(data);
                }

                count++;
                if (count % 10000 == 0) {
                    dataTestList = new ArrayList<>();
                    float percent = (float) (count * 100) / total;
                    binding.progressBar.setProgress((int) percent);
                }
            }
            long end = System.currentTimeMillis();
            long times = end - start;
            System.out.println("Timer Read: "+ times);
            Toast.makeText(this, "Time: "+times+ " ms", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class ProgressBarAsync extends AsyncTask<Void, Integer, Void> {
        private int total = 0;

        public ProgressBarAsync() {
            createDataSample();
        }

        public void createDataSample() {
            int count = 200000; // 1.000.000

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
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < total; i++) {
                // Simulasikan operasi latar belakang dengan tidur selama 50ms
//                try {
//                    Thread.sleep(50);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                if ((i+1) % 25000 == 0) {
                    float part = (float) i /total;
                    int percent = (int) (part * 100);
                    publishProgress(percent);
                }


                // Publikasikan kemajuan
                //publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Perbarui UI dengan kemajuan yang diterima
            int progress = values[0];
            System.out.println("Value progress: "+ progress);
            // Misalnya, perbarui ProgressBar atau TextView
            binding.progressBar.setProgress(progress);
            binding.percentProgressBar.setText("Progress: "+ progress +" %");
//            progressBar.setProgress(progress);
//            textView.setText("Progress: " + progress + "%");
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            binding.percentProgressBar.setText("Loading Completed");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_STORAGE) {
            //if (grantResults.length > 0 && grantResults[0] == -1) { // just tester
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readFileCsv();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}