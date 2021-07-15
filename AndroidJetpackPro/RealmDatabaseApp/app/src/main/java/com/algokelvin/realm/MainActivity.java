package com.algokelvin.realm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.realm.db.RealmDb;
import com.algokelvin.realm.model.People;
import com.algokelvin.realm.model.Skill;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAdd, btnRead, btnUpdate, btnDelete, btnDeleteWithSkill, btnFilterByAge;
    EditText inName, inAge, inSkill;
    TextView textView, txtFilterBySkill, txtFilterByAge;
    Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RealmDb.initRealm(this);
        initViews();

        mRealm = Realm.getDefaultInstance();
    }

    private void initViews() {
        btnAdd = findViewById(R.id.btnAdd);
        btnRead = findViewById(R.id.btnRead);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnDeleteWithSkill = findViewById(R.id.btnDeleteWithSkill);
        btnFilterByAge = findViewById(R.id.btnFilterByAge);

        textView = findViewById(R.id.textViewEmployees);
        txtFilterBySkill = findViewById(R.id.txtFilterBySkill);
        txtFilterByAge = findViewById(R.id.txtFilterByAge);

        inName = findViewById(R.id.inName);
        inAge = findViewById(R.id.inAge);
        inSkill = findViewById(R.id.inSkill);

        btnAdd.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnDeleteWithSkill.setOnClickListener(this);
        btnFilterByAge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnAdd) {
            addPeople();
        } else if (id == R.id.btnRead) {
            readPeopleRecords();
        }
    }

    private void addPeople() {
        mRealm.executeTransaction(realm1 -> {
            try {
                String name = inName.getText().toString().trim();
                String age = inAge.getText().toString().trim();
                if (!name.isEmpty() && !age.isEmpty()) {
                    People people = new People();
                    people.name = name;
                    people.age = Integer.parseInt(age);

                    String languageKnown = inSkill.getText().toString().trim();
                    if (!languageKnown.isEmpty()) {
                        Skill skill = realm1.where(Skill.class).equalTo(Skill.PROPERTY_SKILL, languageKnown).findFirst();
                        if (skill == null) {
                            skill = realm1.createObject(Skill.class, languageKnown);
                            realm1.copyToRealm(skill);
                        }
                        people.skills = new RealmList<>();
                        people.skills.add(skill);
                    }

                    realm1.copyToRealm(people);
                }
            } catch (RealmPrimaryKeyConstraintException e) {
                Toast.makeText(this, "Primary Key exists, Press Update instead", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void readPeopleRecords() {
        mRealm.executeTransaction(realm -> {
            RealmResults<People> results = realm.where(People.class).findAll();
            textView.setText("");
            for (People people : results) {
                textView.append(people.name + " age: " + people.age + " skill: " + people.skills.size());
            }
        });
    }
}