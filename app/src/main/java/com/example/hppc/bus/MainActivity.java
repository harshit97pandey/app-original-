package com.example.hppc.bus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import static com.example.hppc.bus.R.id.sp1;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedpreferences = getSharedPreferences("Register", Context.MODE_PRIVATE);
        String regstat=sharedpreferences.getString("regno","0");

        try {
            if (regstat.equals("0")) {

                Intent in = new Intent(this, registration.class);


                startActivity(in);
                finish();


            }
        }
        catch (Exception e)
        {

        }




    }


    public void track(View view)
    {
        Intent in =new Intent(this,Tracker.class);
        startActivity(in);
    }

    public void nearbubusstop(View view) {
        Intent in =new Intent(this,NearbyBusStop.class);
        startActivity(in);
    }

    public void fare(View view)
    {
Intent in=new Intent(this,Fare.class);
        startActivity(in);


    }

    public void table(View view) {
        Intent in=new Intent(this,TimeTable.class);
        startActivity(in);
    }

    public void helpline(View view) {
        Intent intent=new Intent(this,HelpLine.class);
        startActivity(intent);
    }
}
