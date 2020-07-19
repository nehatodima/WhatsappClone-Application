package com.example.whatsapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolderClass> {
    ArrayList<UserObject> userList;
    public AdapterClass(ArrayList<UserObject> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemuser,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ViewHolderClass vhc=new ViewHolderClass(layoutView);
        return vhc;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, final int position) {
        holder.name.setText(userList.get(position).getName());
        holder.phone.setText(userList.get(position).getPhone());
        holder.eachContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();
              //  FirebaseDatabase.getInstance().getReference().child("chat").push().setValue(key);
               FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).child("chat").child(key).setValue(true);
                FirebaseDatabase.getInstance().getReference().child("user").child(userList.get(position).getUid()).child("chat").child(key).setValue(true);

            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder{
        public TextView name,phone;
        public LinearLayout eachContact;
        public ViewHolderClass( View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            eachContact = itemView.findViewById(R.id.eachcontact);

        }

    }
}
