package com.example.wajahat.to_do;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wajahat.to_do.Data.message;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.SAXParser;

public class PostTask extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView date_t;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Button submit;
    private Spinner to;
    private EditText task;
    private TextView deadline;
    private TextView hd;


    private String user;
    private int id = 1;
    List<String> all_users = new ArrayList<String>();

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference userdata;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_task);
        date_t=findViewById(R.id.textView7);
        submit=findViewById(R.id.button);
        hd=findViewById(R.id.hide);
        hd.setVisibility(View.INVISIBLE);
        to=findViewById(R.id.to);
        task=findViewById(R.id.editText2);
        deadline=findViewById(R.id.textView7);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        user= bundle.getString("Username");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Tasks");
        userdata=firebaseDatabase.getReference().child("Users");

        to.setOnItemSelectedListener(this);
        final List<String> categories = new ArrayList<String>();
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        to.setAdapter(dataAdapter);


        userdata.addChildEventListener(new ChildEventListener() {
            String temp;
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                temp = (String) dataSnapshot.getKey();
                if (!temp.equals(user)) {
                    dataAdapter.add(temp);
                    dataAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        // Drop down layout style - list view with radio button

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
                message msg=new message(random, user,hd.getText().toString(),task.getText().toString(),deadline.getText().toString());
                databaseReference.child(Integer.toString(random)).setValue(msg);
            }
        });

    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        hd.setText(item);

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
