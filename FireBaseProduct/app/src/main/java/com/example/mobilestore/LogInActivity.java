package com.example.mobilestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
    private EditText edt_password, mail;
    Button txt_login,txt_register;
    FirebaseAuth auth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mail = findViewById(R.id.mail);
        edt_password = findViewById(R.id.edt_password);
        txt_login = findViewById(R.id.txt_login);
        txt_register= findViewById(R.id.txt_register1);
        auth = FirebaseAuth.getInstance();
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogIn();
            }
        });
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LogInActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void LogIn() {
        if (mail.getText().toString().equals("") && edt_password.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Data", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog=new ProgressDialog(LogInActivity.this);
            progressDialog.show();
            auth.signInWithEmailAndPassword(mail.getText().toString(), edt_password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()){
                             progressDialog.dismiss();
                             Intent intent=new Intent(LogInActivity.this,MainActivity.class);
                             startActivity(intent);
                         }
                         else {
                             progressDialog.dismiss();
                             Toast.makeText(LogInActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                         }
                        }
                    });

        }
    }
}