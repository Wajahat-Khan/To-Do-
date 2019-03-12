package com.example.wajahat.to_do;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wajahat.to_do.Data.message;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Random;

public class PostTask extends AppCompatActivity {

    private static final String TAG = "Post";
    private TextView date_t;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Button submit;
    private EditText to;
    private EditText task;
    private TextView deadline;
    private String user;
    private int id = 1;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_task);
        date_t=findViewById(R.id.textView7);
        submit=findViewById(R.id.button);

        to=findViewById(R.id.editText);
        task=findViewById(R.id.editText2);
        deadline=findViewById(R.id.textView7);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        user= bundle.getString("Username");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Tasks");

        date_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(PostTask.this, android.R.style.Theme_Holo_Light,onDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m=m+1;
                String date= m + "/" + d + "/" + y;
                date_t.setText(date);
            }
        };

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int random = new Random().nextInt(100000) + 1;
                message msg=new message(random, user,to.getText().toString(),task.getText().toString(),deadline.getText().toString());
                databaseReference.child(Integer.toString(random)).setValue(msg);
            }
        });

    }
}
