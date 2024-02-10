package com.example.mobilestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowProduct extends AppCompatActivity {
    RecyclerView listView;
    private List<Product> listData;
    private MyAdapter adapter;
    FirebaseUser user;
    String UserID;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        user= FirebaseAuth.getInstance().getCurrentUser();
        UserID=user.getUid();
        listView=findViewById(R.id.lists);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listData=new ArrayList<>();
        progressDialog=new ProgressDialog(ShowProduct.this);
        progressDialog.show();
        final DatabaseReference nm= FirebaseDatabase.getInstance().getReference("Product").child(UserID);
        nm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                        Product l=npsnapshot.getValue(Product.class);
                        listData.add(l);
                    }
                    adapter=new MyAdapter(ShowProduct.this,listData);
                    listView.setAdapter(adapter);
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}