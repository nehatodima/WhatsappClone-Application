package com.example.whatsapp;
import android.content.Intent;
import android.os.Bundle;
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
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolderClass> {
    ArrayList<ChatObject> chatList;
    public ChatAdapter(ArrayList<ChatObject> charList) {
        this.chatList = charList;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemchat,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ViewHolderClass vhc=new ViewHolderClass(layoutView);
        return vhc;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderClass holder, final int position) {
        holder.chatID.setText(chatList.get(position).getChatID());
       holder.eachContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("chatID",chatList.get(holder.getAdapterPosition()).getChatID());
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
                //String key = FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();
                //FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("chat").child(key).setValue(true);
                //FirebaseDatabase.getInstance().getReference().child(userList.get(position).getUid()).child("chat").child(key).setValue(true);
            }
        });
    }

    @Override
    public int getItemCount() { return chatList.size();
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder{
        public TextView chatID;
        public LinearLayout eachContact;
        public ViewHolderClass( View itemView) {
            super(itemView);
            chatID = itemView.findViewById(R.id.chatID);
            eachContact = itemView.findViewById(R.id.eachcontact);
        }

    }
}
