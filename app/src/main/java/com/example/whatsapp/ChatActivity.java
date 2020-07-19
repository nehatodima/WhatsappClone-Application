package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView msgrecview;
    private RecyclerView.Adapter msgadapter;
    private RecyclerView.LayoutManager msglayoutManager;
    private EditText m;
    private Button b;
    ArrayList<MsgObject> msgList;
    String chatID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        msgList = new ArrayList<>();
        chatID = getIntent().getExtras().getString("chatID");
        Log.d("ChatActivity.this","chatidddddddddddddddddd is"+chatID);
        m=(EditText)findViewById(R.id.message);
        b=(Button)findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        initializeRecyclerView();
        ////////////////
DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("chat").child(chatID);
       myRef.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               if(dataSnapshot.exists()){
                   String text ="";
                   String creator = "";
                   if(dataSnapshot.child("text").getValue() != null)
                   {
                       text = dataSnapshot.child("text").getValue().toString();
                   }
                   if(dataSnapshot.child("creator").getValue() != null)
                   {
                       creator = dataSnapshot.child("creator").getValue().toString();
                   }
                   MsgObject mo = new MsgObject(dataSnapshot.getKey(),creator ,text );
                   msgList.add(mo);
                   msglayoutManager.scrollToPosition(msgList.size()-1);
                   Log.d("ChatActivity.this","mmmmmmmmmmmmmmmmmmm"+msgList.get(0).getMessage().toString());
                   msgadapter.notifyDataSetChanged();
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
        /////////////
        //msgList = getMessages();
       /*MsgObject mo = new MsgObject("message id ","senderid ","message" );;
        msgList.add(mo);
        Log.d("ChatActivity.this","mmmmmmmmmmmmmmmmmmm"+msgList.get(0).getMessage().toString());
        msgadapter.notifyDataSetChanged();*/
    }



    private void sendMessage(){
        Toast.makeText(ChatActivity.this,"send",Toast.LENGTH_LONG).show();
        if(!m.getText().toString().isEmpty()){
            DatabaseReference msgref = FirebaseDatabase.getInstance().getReference().child("chat").child(chatID).push();
            Log.d("ChatActivity.this","chatid is"+chatID);
            Map msgMap = new HashMap<>();
            msgMap.put("text",m.getText().toString());
            msgMap.put("creator", FirebaseAuth.getInstance().getUid());
            msgref.updateChildren(msgMap);
        }
        m.setText(null);
    }
    private void initializeRecyclerView() {
        msgrecview=findViewById(R.id.msgrecview);
        msgrecview.setNestedScrollingEnabled(false);
        msgrecview.setHasFixedSize(false);
        msglayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        msgrecview.setLayoutManager(msglayoutManager);
        msgadapter = new MessageAdapter(msgList);
        msgrecview.setAdapter(msgadapter);
    }
}