package com.example.user.myappfirebasse_example;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.myappfirebasse_example.adapter.StudentAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name;
    EditText mssv;
    Button btnCreate;
    ListView listView;
    List<Student> dsStudent;
    StudentAdapter adapter;

    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        name = findViewById(R.id.edt_hoten);
        mssv = findViewById(R.id.edt_MASV);

        dsStudent = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_dssv);

        adapter = new StudentAdapter(this, R.layout.item_student_row, dsStudent);
        listView.setAdapter(adapter);

        btnCreate = findViewById(R.id.btn);
        btnCreate.setOnClickListener(this);

        loadData();
    }

    @Override
    public void onClick(View view) {
        if (view == btnCreate) {
//            Map<String, String> studentMap = new HashMap<>();
//            studentMap.put("name", name.getText().toString());
//            studentMap.put("mssv", mssv.getText().toString());

            Student student = new Student(name.getText().toString(), mssv.getText().toString());

            mData.child("Student").push().setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        loadData();
                    }
                }
            });
        }
    }

    private void loadData() {
        mData.child("Student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dsStudent.isEmpty()){
                    dsStudent.clear();
                }
                for (DataSnapshot item : dataSnapshot.getChildren()){
                    dsStudent.add(item.getValue(Student.class));
                }
                loadStudentSuccess();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadStudentSuccess() {
        adapter.notifyDataSetChanged();
    }
}
