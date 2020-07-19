package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<UserObject> userList;
    public String TAG = "UsersActivity.this";
    public Boolean yes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        userList = new ArrayList<>();
        initializeRecyclerView();
        getContactList();
    }

    private void getContactList() {
        String ISOprefix = getCountryIso();

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while(phones.moveToNext()){
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phone.replace(" ","");
            phone.replace(")","");
            phone.replace("(","");
            phone.replace("-","");
            phone =phone.substring(0);


            //if(String.valueOf(phone.charAt(4)).equals("8")) {
            UserObject mContact = new UserObject(name, phone,"");
            ContactThereInDatabase(mContact);




        }
    }


    public void ContactThereInDatabase(UserObject mContact)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user");
        Query query = ref.orderByChild("phone").equalTo(mContact.getPhone());
        final String namee = mContact.getName();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String phone = "";
                    String name ="";
                    for(DataSnapshot childSnapshot : dataSnapshot.getChildren())
                    {
                        if(childSnapshot.child("phone").getValue()!=null)
                        {
                            phone = childSnapshot.child("phone").getValue().toString();
                        }
                        if(childSnapshot.child("name").getValue()!=null)
                        {
                            name = namee;
                        }


                        UserObject finalobj= new UserObject(phone,name,childSnapshot.getKey());
                        userList.add(finalobj);
                        adapter.notifyDataSetChanged();
                        return;
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private String getCountryIso(){
        String iso = null;
        TelephonyManager tm  = (TelephonyManager)getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        if(tm.getNetworkCountryIso() != null)
        {
            if(!tm.getNetworkCountryIso().toString().equals("")){
                iso = tm.getNetworkCountryIso().toString()  ;          }
        }
        return CountryToPhonePrefix.getPhone(iso);
    }

    private void initializeRecyclerView() {
        recyclerView =findViewById(R.id.chatrecview);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterClass(userList);
        recyclerView.setAdapter(adapter);
    }
}