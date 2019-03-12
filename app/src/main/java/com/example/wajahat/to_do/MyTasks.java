package com.example.wajahat.to_do;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.wajahat.to_do.Adapters.myTasksAdapter;
import com.example.wajahat.to_do.Data.message;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyTasks extends AppCompatActivity {

    private List<message> mg=new ArrayList<>();
    private RecyclerView recyclerView;
    private myTasksAdapter adapter;
    private String user;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_tasks);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        user= bundle.getString("Username");

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("Tasks");
        recyclerView=findViewById(R.id.all_tasks);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                message task=dataSnapshot.getValue(message.class);
                if(task.getTo().equals(user)) {
                    mg.add(task);
                    adapter.setItems(mg);
                }
                }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        adapter= new myTasksAdapter(this, new myTasksAdapter.MyAdapterListener() {
            @Override
            public void onClick(View view, int position, List<message> items) {
                databaseReference.child(Integer.toString(mg.get(position).getId())).removeValue();
                mg.remove(position);
                adapter.setItems(mg);
                Toast.makeText(MyTasks.this, "Task Completed", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }
}
