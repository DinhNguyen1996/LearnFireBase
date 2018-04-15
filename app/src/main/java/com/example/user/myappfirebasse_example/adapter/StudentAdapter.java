package com.example.user.myappfirebasse_example.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.myappfirebasse_example.R;
import com.example.user.myappfirebasse_example.Student;

import java.util.List;

/**
 * Created by thanh on 15/04/2018.
 */

public class StudentAdapter extends ArrayAdapter<Student> {

    private Context context;
    private List<Student> items;
    private int resource;

    public StudentAdapter(Context context, int resource, List<Student> items) {
        super(context, resource, items);
        this.context = context;
        this.items = items;
        this.resource  =resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_student_row, null);
        }

        Student student = items.get(position);

        if (student != null) {
            TextView tvName = v.findViewById(R.id.tv_name);
            TextView tvMssv = v.findViewById(R.id.tv_mssv);

            tvName.setText(student.getName());
            tvMssv.setText(student.getMssv());
        }

        return v;
    }

}
