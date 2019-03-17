package com.example.wajahat.to_do;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wajahat.to_do.Data.Depts;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="qwe" ;
    private String username=null;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private static final int RC_SIGN_IN = 123;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference child;
    private DatabaseReference departments;
    private DatabaseReference main;


    private String m_Text = "";
    private TextView user;
    private Button post_task;
    private Button my_task;
    private Button my_tasks_assigned;
    private Button post_group;
    private String dept;
    private Button ss;
    private int flag=0;
    private int count=0;

    List<String> all_keys = new ArrayList<String>();

    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        user=findViewById(R.id.user);
        post_task=findViewById(R.id.post_task);
        my_task=findViewById(R.id.my_tasks);
        post_group=findViewById(R.id.post_group);
        ss=findViewById(R.id.ss);
        my_tasks_assigned=findViewById(R.id.MyAssigned);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users");
        child=FirebaseDatabase.getInstance().getReference().child("Users");
        departments=firebaseDatabase.getReference().child("Depts");
        main=firebaseDatabase.getReference();

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    // User signed in
                    username=user.getDisplayName();
                    databaseReference.child(user.getDisplayName()).setValue(user.getEmail());
                    onSignedInInitialize(user.getDisplayName());

                    main.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            count=(int) dataSnapshot.getChildrenCount();
                                 main.removeEventListener(this);
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

                    departments.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                all_keys.add(dataSnapshot.getKey());
                                if(all_keys.size()==count){
                                     if(!all_keys.contains(username)){

                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setTitle("Department");

// Set up the input
                                        final EditText input = new EditText(MainActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                                        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                                        builder.setView(input);

// Set up the buttons
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                m_Text = input.getText().toString();
                                                Depts depts = new Depts(username, m_Text.toUpperCase());
                                                departments.child(username).setValue(depts);
                                                flag = 1;
                                            }
                                        });
                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });

                                        builder.show();
                                    }
                                    else {
                                            }
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

                        }
                else {
                    // User not signed in
                    onSignedOutInitialize();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()
                                           ))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };


        post_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(),PostTask.class);
                intent.putExtra("Username",username);
                startActivity(intent);
            }
        });

        post_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(),PostGroup.class);
                intent.putExtra("Username",username);
                startActivity(intent);

            }
        });
        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(),SearchTask.class);
                intent.putExtra("Username",username);
                startActivity(intent);
            }
        });
        my_tasks_assigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(),MyTasksAssigned.class);
                intent.putExtra("Username",username);
                startActivity(intent);

            }
        });
        my_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(),MyTasks.class);
                intent.putExtra("Username",username);
                startActivity(intent);
            }
        });

 }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Signed in cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    private void onSignedInInitialize(String name){
        username=name;
        user.setText(username);
        // other working
    }
    private void onSignedOutInitialize(){
        username=null;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action:
                AuthUI.getInstance().signOut(this);
                return true;
        default:
            return super.onOptionsItemSelected(item);
        }

    }
}

