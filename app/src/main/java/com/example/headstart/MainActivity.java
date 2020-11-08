package com.example.headstart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button supplierBnt;
    Button customerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        supplierBnt = findViewById(R.id.supplierbtn);
        supplierBnt.setOnClickListener(this);

        customerBtn = findViewById(R.id.customerbtn);
        customerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //if user is a Truck owner and clicks supplier btn redirect to Login Page
        if (v.getId() == R.id.supplierbtn){
            startActivity(new Intent(this, TruckOwnerLoginForm.class));
        }
        // Or if user is customer then redirect to customer page....
        /*if (v.getId() == R.id.customerbtn){
            startActivity(new Intent());
        }*/
    }


    //On back press Dialog
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Are you sure you want to Exit ?")
                .setNegativeButton(android.R.string.no,null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}