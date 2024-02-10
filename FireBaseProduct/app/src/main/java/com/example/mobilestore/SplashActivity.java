package com.example.mobilestore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

    }
    @Override
    protected void onResume() {
        super.onResume();

        if (DetectConnection.checkInternetConnection(getApplicationContext())) {
            Thread mythread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(2000);
                        Intent intent = new Intent(SplashActivity.this, LogInActivity.class);
                        startActivity(intent);
                        finish();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            };
            mythread.start();
        } else {
            AlertDialog.Builder alert=new AlertDialog.Builder(SplashActivity.this);
            alert.setTitle("Error");
            alert.setMessage("Internet Connection Not Available");
            alert.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AgainCheck();
                }
            });
            alert.show();
        }


    }
    private void AgainCheck() {
        if (DetectConnection.checkInternetConnection(getApplicationContext())) {

            Thread mythread=new Thread(){
                @Override
                public void run() {
                    try {
                        sleep(2000);
                        Intent intent=new Intent(getApplicationContext(),LogInActivity.class);
                        startActivity(intent);
                        finish();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            };
            mythread.start();
        } else {
            AlertDialog.Builder alert=new AlertDialog.Builder(SplashActivity.this);
            alert.setTitle("Error");
            alert.setMessage("Internet Connection Not Available");
            alert.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AgainCheck();
                }
            });
            alert.show();

        }
    }
}