package com.example.mobilestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Welcome extends AppCompatActivity {

    FirebaseUser user;
    DatabaseReference reference;
    String UserID;
    FirebaseAuth auth;
    EditText id,name,des;
    Button add_p;
    ProgressDialog progressDialog;
    long max=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        user= FirebaseAuth.getInstance().getCurrentUser();
        id=findViewById(R.id.P_ide);
        name=findViewById(R.id.P_namee);
        des=findViewById(R.id.P_Dese);
        add_p=findViewById(R.id.add);
        reference= FirebaseDatabase.getInstance().getReference("User");
        UserID=user.getUid();
        auth=FirebaseAuth.getInstance();
      //  Toast.makeText(this, "User"+UserID, Toast.LENGTH_SHORT).show();
        add_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().toString().equals("")&&name.getText().toString().equals("")
                       &&des.getText().toString().equals("")){
                    Toast.makeText(Welcome.this, "Enter Detail", Toast.LENGTH_SHORT).show();

                }
                else {
                    progressDialog=new ProgressDialog(Welcome.this);
                    progressDialog.show();
                    reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            max = (snapshot.getChildrenCount());
                            User userProfile = snapshot.getValue(User.class);
                            if (userProfile != null) {
                                // Toast.makeText(Welcome.this, "User Name"+userProfile.name, Toast.LENGTH_SHORT).show();

                                DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("Product").child(UserID);
                                String pKey = dRef.push().getKey();
                                Product product = new Product(id.getText().toString(), name.getText().toString()
                                        , des.getText().toString(), pKey);


                                dRef.child(pKey).setValue(product).addOnCompleteListener(
                                        new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    progressDialog.dismiss();
                                                    finish();
                                                    /*Toast.makeText(Welcome.this, "Done", Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(Welcome.this, "" + pKey, Toast.LENGTH_SHORT).show();
                                              */
                                                } else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(Welcome.this, "Task" + task.getException(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                );
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }
}