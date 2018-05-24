package com.example.hppc.bus;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;

/**
 * Created by hp pc on 29/12/2016.
 */

public class Route extends Activity {
    String route[]=new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route);

        String k=getIntent().getStringExtra("busno");
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout ll1=(LinearLayout)findViewById(R.id.ll1);

        DataBaseHelper db ;
        db = new DataBaseHelper(this);
        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        if (db.open())
        {

            route=db.check4(k);

            for(int i=0;i<10;i++)
            {
                if(route[i]== null)
                    break;
                LinearLayout linearLayout=new LinearLayout(this);
                linearLayout.setId(i);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                SharedPreferences sharedPreferences=getSharedPreferences("location", Context.MODE_PRIVATE);
                 String loc1=sharedPreferences.getString("loc1","LANKA");

                String loc2=sharedPreferences.getString("loc2","CANTT.");

                String z=route[i];
                if(route[i].equals(loc1))
                {
                    z=z+"\t(Starting Location)";
                }
                if(route[i].equals(loc2))
                {
                    z=z+"(Destination)";
                }

                Button button=new Button(this);

                button.setText(z);
                button.setBackgroundResource(R.drawable.blue);

               ll1.addView(button);




            }





        }

    }
}
