package com.example.hppc.bus;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hppc.bus.DataBaseHelper;
import java.io.IOException;

/**
 * Created by hp pc on 03/01/2017.
 */

public class ShowBus extends Activity
{
    String loc1;
    String loc2;
    int time;

    String BusSelected;
    String bus1[];
    String bus[][];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bus1=new String[8];
        bus=new String[40][4];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showbuses);
       Bundle b1 = getIntent().getExtras();
        loc1 = b1.getString("loc1");
       loc2 = b1.getString("loc2");
        time=b1.getInt("time");
    loc1=getIntent().getStringExtra("loc1");
        loc2=getIntent().getStringExtra("loc2");
        TextView textView=(TextView)findViewById(R.id.head);
        textView.setText("Buses on Route\n"+loc1+"  To  "+loc2);


        DataBaseHelper db ;
        db = new DataBaseHelper(this);
        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
        if (db.open())
        {
            //String k = db.Route(loc1,loc2);
            bus1= db.check(loc1,loc2);
            String s="";
            for(int i=0;i<8;i++)
            {
                if(bus1[i]!=null)
                Log.e("bus3",bus1[i]);
            }



           if(bus1[0]==null)
           {Toast.makeText(this,"Sorry, No Bus On This Route.",Toast.LENGTH_SHORT).show();
               finish();
           }
            Log.e("time",time+"");
            bus=db.check10(bus1,loc1,loc2,time);
            if(bus[0][0]==null)
            {Toast.makeText(this,"Sorry, No Bus On This Route.",Toast.LENGTH_SHORT).show();
                finish();
            }


            //String k="lll";


        }

            LinearLayout ll=(LinearLayout)findViewById(R.id.ll1);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin=15;
        layoutParams1.bottomMargin=20;

        layoutParams.rightMargin=5;
        layoutParams.leftMargin=5;
        String k[][]=new String[8][2];
        //change 1 to c down
        int i=0;

            while ((i <40))
            {
                if(bus[i][0]== null)

                    break;


                Button bb = new Button(this);
                bb.setId(i);
                bb.setText(bus[i][0]+"\n"+"From "+loc1+"  "+bus[i][1]+"\n"+"To   "+loc2+"  "+bus[i][2]);
                bb.setBackgroundColor(Color.WHITE);
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
                {
                    bb.setBackgroundDrawable( getResources().getDrawable(R.drawable.busicon) );
                }
                else
                {
                    bb.setBackground( getResources().getDrawable(R.drawable.busicon));
                }

                TextView tv=new TextView(this);
                tv.setLayoutParams(layoutParams1);
                tv.setGravity(9);
                tv.setBackgroundColor(Color.WHITE);
                k=db.check3(loc1,loc2);
                String s="";

                /*
                Log.e("l",s);
                String startl=k[i][0];
                String endl=k[i][1];
                if(loc1.equals("LANKA"))
                {
                    startl="LANKA";
                    endl="CANTT.";
                }
                if(loc1.equals("CANTT."))
                {
                    startl="CANTT.";
                    endl="LANKA";
                }
                */

                //tv.setText(startl+"   To   "+endl);
                int tm=Integer.parseInt(bus[i][3]);
               int th=tm/60;
                tm=tm%60;

                if(th>0)
                tv.setText("Arriving in "+th+" hours "+tm+" mins");
                else
                    tv.setText("Arriving in "+tm+" mins");
                tv.setTextColor(Color.rgb(3,162,37));








                bb.setLayoutParams(layoutParams);
                ll.addView(bb);
                ll.addView(tv);
                final String kj=bus[i][0];


                bb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button v = (Button) view;
                        String k = v.getText().toString();
                        Intent intent = new Intent(getApplicationContext(), TimeTableShow.class);
                        intent.putExtra("Busno", kj);
                        startActivity(intent);
                    }
                });
                i++;
            }
        }
        catch (Exception e)
        {
            Log.e("error",e.toString());
        }
        }

    }

