package com.example.user.myappfirebasse_example;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name;
    EditText mssv;
    Button btnCreate;

    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        name = findViewById(R.id.edt_hoten);
        mssv = findViewById(R.id.edt_MASV);

        btnCreate = findViewById(R.id.btn);
        btnCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnCreate) {
//            Map<String, String> studentMap = new HashMap<>();
//            studentMap.put("name", name.getText().toString());
//            studentMap.put("mssv", mssv.getText().toString());

            Student student= new Student(name.getText().toString(), mssv.getText().toString());

            mData.child("Student").push().setValue(student).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(StudentActivity.this, "Create student Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StudentActivity.this, "Create student Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
