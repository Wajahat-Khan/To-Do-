package com.example.wajahat.to_do;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wajahat.to_do.Adapters.myTasksAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

public class SearchTask extends AppCompatActivity {

    TextView search_text;
    Button ss;
    String user;
    String curr_date;
    private RecyclerView recyclerView;
    private myTasksAdapter adapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private  int d, m,y;

    private List<Integer> dd=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_task);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        user= bundle.getString("Username");
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("Tasks");

        search_text=findViewById(R.id.search_text);
        ss=findViewById(R.id.search);

        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curr_date=search_text.getText().toString();
                StringTokenizer st1 =
                        new StringTokenizer(curr_date, "/ ");
                while (st1.hasMoreTokens()){
                   dd.add(Integer.parseInt(st1.nextToken()));

                }

            }


        });

    }
}
