package com.example.wajahat.to_do;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wajahat.to_do.Adapters.SearchAdapter;
import com.example.wajahat.to_do.Adapters.myTasksAdapter;
import com.example.wajahat.to_do.Data.message;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

public class SearchTask extends AppCompatActivity {

   TextView from_date;
   TextView to_date;
    Button ss;
    String user;
    private String temp_date;
    private List<message> mg=new ArrayList<>();
    private DatePickerDialog.OnDateSetListener onDateSetListener, onDateSetListener2;
    private RecyclerView recyclerView;
    private SearchAdapter adapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private  int fd, fm,fy , td, tm, ty;

    private List<Integer> dd=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_task);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        user= bundle.getString("Username");

        to_date=findViewById(R.id.to_date);
        from_date=findViewById(R.id.from_date);
        ss=findViewById(R.id.search);
        recyclerView=findViewById(R.id.search_rv);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("Tasks");

        to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(SearchTask.this, android.R.style.Theme_Holo_Light,onDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m=m+1;
                ty=y;
                tm=m;
                td=d;
                String date= m + "/" + d + "/" + y;
                to_date.setText(date);
            }
        };

        from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(SearchTask.this, android.R.style.Theme_Holo_Light,onDateSetListener2,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        onDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m=m+1;
                fy=y;
                fm=m;
                fd=d;
                String date= m + "/" + d + "/" + y;
                from_date.setText(date);
            }
        };
        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        message task=dataSnapshot.getValue(message.class);
                        if(task.getFrom().equals(user) || task.getTo().equals(user)){
                            temp_date=task.getDeadline();
                            StringTokenizer st1= new StringTokenizer(temp_date, "/");
                            int m= Integer.parseInt(st1.nextToken().toString());
                            int d= Integer.parseInt(st1.nextToken().toString());
                            int y= Integer.parseInt(st1.nextToken().toString());

                            if(y<=ty && y>=fy){
                                if(m>=fm && m <=tm){
                                    if(d>=fd && d<=td){
                                        Toast.makeText(SearchTask.this, "yess", Toast.LENGTH_SHORT).show();

                                        mg.add(task);
                                        adapter.setItems(mg);
                                    }
                                }
                            }

                        }
                    }
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });

            }
        });

        adapter=new SearchAdapter(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVerticalScrollBarEnabled(true);

    }

}
