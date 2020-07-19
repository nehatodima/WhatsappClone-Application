package com.example.whatsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolderClass> {
    ArrayList<MsgObject>  msglist;
    public MessageAdapter(ArrayList<MsgObject> msgList) {

      msglist = msgList;
      //  msglist= new ArrayList<MsgObject>();
        //msglist.add(new MsgObject("hjh","mnnnnnnnnn","hhhhhhhhhhh"));
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemmsg,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ViewHolderClass vhc=new ViewHolderClass(layoutView);
        return vhc;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, final int position) {
        holder.msg.setText(msglist.get(position).getMessage());
        holder.sender.setText(msglist.get(position).getSenderID());
       /* holder.eachContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String key = FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();
                //FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("chat").child(key).setValue(true);
                //FirebaseDatabase.getInstance().getReference().child(userList.get(position).getUid()).child("chat").child(key).setValue(true);
            }
        });*/
    }

    @Override
    public int getItemCount() {
    return msglist.size();
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder{
        public TextView msg;
        public TextView sender;
        public LinearLayout msglayout;
        public ViewHolderClass( View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.message);
            sender = itemView.findViewById(R.id.sender);
            msglayout = itemView.findViewById(R.id.msglayout);
        }

    }
}
