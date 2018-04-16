package com.example.user.myappfirebasse_example;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.myappfirebasse_example.adapter.StudentAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
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

    ArrayList<String> ListID;
    Button btnDelete;

    Button btnEdit;
    Student studentEDITED;
    int position = -1;
//
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        name = findViewById(R.id.edt_hoten);
        mssv = findViewById(R.id.edt_MASV);

        ListID = new ArrayList<>();

        dsStudent = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_dssv);

        adapter = new StudentAdapter(this, R.layout.item_student_row, dsStudent);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                Student st = (Student)listView.getItemAtPosition(i);
                name.setText(st.getName());
                mssv.setText(st.getMssv());
            }
        });

        btnCreate = findViewById(R.id.btn);
        btnCreate.setOnClickListener(this);

        btnDelete = findViewById(R.id.btn_xoa);
        btnDelete.setOnClickListener(this);

        btnEdit = findViewById(R.id.btn_sua);
        btnEdit.setOnClickListener(this);

        loadData();
    }

    @Override
    public void onClick(View view) {
        if (view == btnCreate) {

            Student student = new Student(name.getText().toString(), mssv.getText().toString());

            mData.child("Student").push().setValue(student);
        }
        if(view == btnDelete){
            if(position >= 0) {
                mData.child("Student").child(ListID.get(position)).removeValue();

            }
        }
        if(view == btnEdit){
            if(position >= 0) {
                studentEDITED = new Student(name.getText().toString(), mssv.getText().toString());
                mData.child("Student").child(ListID.get(position)).setValue(studentEDITED);
            }
        }
    }

    private void loadData() {
        mData.child("Student").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ListID.add(dataSnapshot.getKey());
                dsStudent.add(dataSnapshot.getValue(Student.class));
                loadStudentSuccess();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                dsStudent.get(position).setName(studentEDITED.getName());
                dsStudent.get(position).setMssv(studentEDITED.getMssv());
                loadStudentSuccess();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ListID.remove(position);
                dsStudent.remove(position);
                position = -1;
                loadStudentSuccess();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

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
