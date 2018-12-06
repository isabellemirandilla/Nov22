package com.example.isabelle.nov22;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference root;
    List<String> keylist;
    EditText eFname, eLname, eGrade;
    ArrayList<String> keyList;
    int index;
    //deletekey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseDatabase.getInstance();
        root = db.getReference("LabGrade");
        keylist = new ArrayList<String>();
        eFname = findViewById(R.id.etFname);
        eLname = findViewById(R.id.etLname);
        eGrade = findViewById(R.id.etScore);
        keyList = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        //String key = root.push().getKey();
        //root.child("value2").setValue("Peter");

        /**root.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
        String rootValue = dataSnapshot.getValue(String.class);
        eFname.setText(rootValue);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
        });**/
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ss : dataSnapshot.getChildren()){
                    keyList.add(ss.getKey());
                }
                Toast.makeText(MainActivity.this, keyList.get(0), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void addRecord(View v) {

        String fname = eFname.getText().toString().trim();
        String lname = eLname.getText().toString().trim();
        Long grade = Long.parseLong(eGrade.getText().toString().trim());
        StuGrades sgrade = new StuGrades(fname, lname, grade);
        root.push().setValue(sgrade);
        Toast.makeText(this,"Record inserted", Toast.LENGTH_LONG).show();

    }

    public void moveFirst(View v) {
        index = 0;
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StuGrades stud = dataSnapshot.child(keyList.get(index)).getValue(StuGrades.class);
                eFname.setText(stud.getFname());
                eLname.setText(stud.getLname());
                eGrade.setText(stud.getGrade().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void movePrevious(View v) {
        if(index == 0) {
            index = 0;
            Toast.makeText(this,"First record...", Toast.LENGTH_LONG).show();
        } else {
            index--;
        }
        index--;
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StuGrades stud = dataSnapshot.child(keyList.get(index)).getValue(StuGrades.class);
                eFname.setText(stud.getFname());
                eLname.setText(stud.getLname());
                eGrade.setText(stud.getGrade().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void moveNext(View v) {
        index++;
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(index == (int) (dataSnapshot.getChildrenCount() - 1)){
                    index = (int) (dataSnapshot.getChildrenCount() - 1);
                    Toast.makeText(MainActivity.this,"Last record...", Toast.LENGTH_LONG).show();
                } else {
                    index++;
                }
                StuGrades stud = dataSnapshot.child(keyList.get(index)).getValue(StuGrades.class);
                eFname.setText(stud.getFname());
                eLname.setText(stud.getLname());
                eGrade.setText(stud.getGrade().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void moveLast(View v) {
        index++;
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                index = (int) (dataSnapshot.getChildrenCount() - 1);
                StuGrades stud = dataSnapshot.child(keyList.get(index)).getValue(StuGrades.class);
                eFname.setText(stud.getFname());
                eLname.setText(stud.getLname());
                eGrade.setText(stud.getGrade().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void editRecord (View v){
        String fname = eFname.getText().toString().trim();
        String lname = eLname.getText().toString().trim();
        Long grade = Long.parseLong(eGrade.getText().toString().trim());
        StuGrades stu = new StuGrades(fname, lname, grade);
        root.child(keyList.get(index)).setValue(stu);
        Toast.makeText(MainActivity.this,"Record Updated...", Toast.LENGTH_LONG).show();
    }

    public void deleteRecord (View v){
        root.child(keyList.get(index)).removeValue();
        eFname.setText("");
        eLname.setText("");
        eGrade.setText("");
        Toast.makeText(MainActivity.this,"Record Deleted...", Toast.LENGTH_LONG).show();
    }
}