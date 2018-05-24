package com.example.hppc.bus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by hp pc on 23/12/2016.
 */

public class registration extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);


        String bloodgrp[]={"Choose Blood Group","A+","A-","B+","B-","AB+","AB-","O+","O-"};
        Spinner spinner = (Spinner) findViewById(R.id.sp1);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,
                android.R.layout.simple_list_item_1,bloodgrp);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void Sign_Up(View view) {
        //Registration Part**************

        SharedPreferences sharedpreferences = getSharedPreferences("Register", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("regno","1");
        editor.commit();
        Intent in=new Intent(this,MainActivity.class);
        startActivity(in);
        finish();
    }
}
