package com.example.hppc.bus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by hp pc on 24/12/2016.
 */

public class Tracker extends Activity {
    Spinner fromL;
    Spinner toL;
    Spinner stopl;
    int time=0;
    int time2=0;
    String Loc[]={"Choose Stop---","CANTT.","DLW","HARI NAGAR","KAMACHHA","LAHARTARA","LANKA","MANDUADIH","RATHYATRA","RAVINDRAPURI","SIGRA","SUNDARPUR","VIDYAPEETH"};
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracker);

        String Bus[]={"1","2","3","4"};

        ArrayAdapter<CharSequence> loc=new ArrayAdapter<CharSequence>(this,android.R.layout.simple_list_item_1,Loc);
        ArrayAdapter<CharSequence> bus=new ArrayAdapter<CharSequence>(this,android.R.layout.simple_list_item_1,Bus);
        fromL=(Spinner)findViewById(R.id.sp2);
        toL=(Spinner)findViewById(R.id.sp3);


        stopl=(Spinner)findViewById(R.id.spfbs);
        stopl.setAdapter(loc);
        fromL.setAdapter(loc);
        toL.setAdapter(loc);

    }

    public void ShowOnMap(View view) {
        Intent in=new Intent(this,MapsActivity.class);
        startActivity(in);
    }

    public void route(View view) {
        Intent intent=new Intent(this,Route.class);
        startActivity(intent);
    }

    public void showbusy(View view) {
        try {

            String loc1=Loc[fromL.getSelectedItemPosition()];
            String loc2=Loc[toL.getSelectedItemPosition()];
            if((loc1.equalsIgnoreCase("Choose Stop---")||(loc2.equalsIgnoreCase("Choose Stop---"))))
            {
                Toast.makeText(this,"Please Choose Source and Destination Stops !",Toast.LENGTH_SHORT).show();

            }
            else {
                if(time!=0) {
                    Bundle bundle = new Bundle();
                    SharedPreferences sharedPreferences = getSharedPreferences("location", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("loc1", loc1);
                    editor.putString("loc2", loc2);
                    editor.commit();
                    bundle.putString("loc1", loc1);
                    bundle.putString("loc2", loc2);
                    bundle.putInt("time", time);


                    Intent intent = new Intent(this, ShowBus.class);


                    intent.putExtras(bundle);

                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(this,"Please select a time interval !",Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this,"error in 1.2",Toast.LENGTH_SHORT).show();


        }

    }

    public void RB(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.rb1:
                if (checked)
                    time=5;
                    break;
            case R.id.rb2:
                if (checked)
                 time=10;
                    break;
            case R.id.rb3:
                if (checked)
                    time=30;
                    break;
            case R.id.rb4:
                if (checked)
                    time=60;
                    break;
            case R.id.rb5:
                if (checked)
                    time=120;
                    break;
            case R.id.rb6:
                if (checked)
                    time=2400;
                    break;
        }
    }


    public void RBS(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.rbs1:
                if (checked)
                    time=5;
                break;
            case R.id.rbs2:
                if (checked)
                    time=10;
                break;
            case R.id.rbs3:
                if (checked)
                    time=20;
                break;
            case R.id.rbs4:
                if (checked)
                    time=30;
                break;
            case R.id.rbs5:
                if (checked)
                    time=60;
                break;
            case R.id.rbs6:
                if (checked)
                    time=2400;
                break;
        }
    }


    public void showbusy2(View view)
    {

        String stoploc=Loc[stopl.getSelectedItemPosition()];
        if(stoploc.equals("Choose Stop---"))
        {
            Toast.makeText(this,"First,Choose Your Stop !",Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(time!=0) {
                Bundle bundle = new Bundle();
                bundle.putString("stop", stoploc);
                bundle.putInt("time", time);

                Intent intent = new Intent(this, ShowBusS.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }
            else
            {
                Toast.makeText(this,"Please select a time interval !",Toast.LENGTH_SHORT).show();
            }


        }
    }
}
