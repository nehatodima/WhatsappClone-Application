package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.Manifest;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    public Button logout;
    public Button finduser;
    private RecyclerView chatrecyclerView;
    private RecyclerView.Adapter chatadapter;
    private RecyclerView.LayoutManager chatlayoutManager;
    ArrayList<ChatObject> chatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logout = (Button)findViewById(R.id.logoutid);
        finduser = (Button)findViewById(R.id.finduserid);
        chatList = new ArrayList<>();
        finduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ointent = new Intent(HomeActivity.this, UsersActivity.class);
                startActivity(ointent);
            }
        }
        );
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        return;
                    }
                });
                getPermissions();
        initializeRecyclerView();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid()).child("chat");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot childsnapshot : dataSnapshot.getChildren()) {
                    ChatObject value = new ChatObject(childsnapshot.getKey());
                    Log.d("HomeActivity.this", "fdddddddddddddddddddValue is: " + value);
                    boolean exists = false;
                    for(ChatObject a : chatList)
                    {
                        if(a.getChatID().equals(value.getChatID())){
                            exists = true;
                        }
                    }
                    if(exists)
                        continue;

                        chatList.add(value);
                        chatadapter.notifyDataSetChanged();

                }
                Log.d("HomeActivity.this", "donrr");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



       // chatList.add(new ChatObject("neha"));
        //Log.d("HomeActivity.this", "doneeeeeeeeeer");
        //chatadapter.notifyDataSetChanged();
    }






    private void initializeRecyclerView() {
        chatrecyclerView =findViewById(R.id.chatrecview);
        chatrecyclerView.setNestedScrollingEnabled(false);
        chatrecyclerView.setHasFixedSize(false);
        chatlayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        chatrecyclerView.setLayoutManager(chatlayoutManager);
        chatadapter = new ChatAdapter(chatList);
        chatrecyclerView.setAdapter(chatadapter);
    }
    private void getPermissions() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS},1);
        }
    }
}