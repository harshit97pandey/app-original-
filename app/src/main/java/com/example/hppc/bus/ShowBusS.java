package com.example.hppc.bus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by hp pc on 19/01/2017.
 */

public class ShowBusS extends Activity {
    String stop;
    int time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showbuss);
        Bundle b1 = getIntent().getExtras();
        stop=b1.getString("stop");
        time=b1.getInt("time");
        int x,y;
        Log.e("Intent data :",stop+" "+time);
        String data[][]=new String[40][6];
        DataBaseHelper db ;

        Calendar c=Calendar.getInstance();
        db = new DataBaseHelper(this);
        SimpleDateFormat df=new SimpleDateFormat("HH:mm");
        String timeA=df.format(c.getTime());
        Log.e("Time:",timeA);
        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        if (db.open())
        {
            LinearLayout ll=(LinearLayout)findViewById(R.id.ll1);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.topMargin=15;
            layoutParams1.bottomMargin=20;

            layoutParams.rightMargin=5;
            layoutParams.leftMargin=5;
            data=db.check7(stop,timeA,time);
            for(int z=0;z<40;z++)
            {
                Log.wtf("data",z+" "+data[z][0]+" "+data[z][1]);
            }

            String log="";

            Log.e("Data Recieved: ",log);
            LinearLayout  linearLayout=(LinearLayout)findViewById(R.id.ll1);
            if(data[0][0]==null)
            {
                Toast.makeText(this,"No Bus In this Interval !",Toast.LENGTH_SHORT).show();
                finish();
            }
x=0;
            y=0;
            int fs=0;
            try {
                for (x = 0; x < 39; x++) {
                    for (y = x; y < 40; y++) {
                        if (data[x][0] == null)
                        {
                            fs=1;
                            break;
                        }
                        if (data[y][0] == null)
                        {

                            break;
                        }

                        if (Integer.parseInt(data[x][5]) > Integer.parseInt(data[y][5])) {
                            String t1 = data[x][5];
                            data[x][5] = data[y][5];
                            data[y][5] = t1;
                            String t2 = data[x][1];
                            data[x][1] = data[y][1];
                            data[y][1] = t2;
                            String t3 = data[x][2];
                            data[x][2] = data[y][2];
                            data[y][2] = t3;
                            String t4 = data[x][3];
                            data[x][3] = data[y][3];
                            data[y][3] = t4;
                            String t5 = data[x][4];
                            data[x][4] = data[y][4];
                            data[y][4] = t5;
                            String t6 = data[x][0];
                            data[x][0] = data[y][0];
                            data[y][0] = t6;


                        }

                    }
                    if(fs==1)
                    {
                        break;
                    }
                }
            }
            catch (Exception e)
            {
                Log.e("Error in Sorting",e.toString()+" in"+x+" "+y+" "+data[x][5]+" "+data[y][5]);
            }
            for(int i=0;i<40;i++)
            {
                log=log+data[i][0]+" "+data[2]+"\n";


            }
            for (int i=0;i<40;i++)
            {
                if(!(data[i][0]==null))
                {
                    Button v=new Button(this);
                    v.setGravity(Gravity.TOP|Gravity.CENTER);
                    v.setBackgroundColor(Color.WHITE);
                    v.setLayoutParams(layoutParams);
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
                    {
                        v.setBackgroundDrawable( getResources().getDrawable(R.drawable.busicon) );
                    }
                    else
                    {
                        v.setBackground( getResources().getDrawable(R.drawable.busicon));
                    }

                    v.setText(data[i][0]+"\n\t"+data[i][1]+" To "+data[i][3]+"\n On "+data[i][2]);
                    int t=Integer.parseInt(data[i][5]);
                    int h=0;
                    if(t>=60)
                    {
                        h=t/60;
                        t=t%60;
                    }
                    String time="";
                    if(h==0)
                    {
                        time=""+t+" mins";
                    }
                    else
                    {
                        time=""+h+" hours "+t+" mins";
                    }
                    TextView textView=new TextView(this);
                    textView.setBackgroundColor(Color.WHITE);
                    textView.setLayoutParams(layoutParams1);
                    textView.setTextColor(Color.rgb(3,162,37));
                    textView.setText("Arriving in "+time);



                    final  String k=data[i][0];
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(getApplicationContext(),TimeTableShow.class);

                            intent.putExtra("Busno",k);
                            startActivity(intent);

                        }
                    });
                    linearLayout.addView(v);
                    linearLayout.addView(textView);
                }

            }


        }


    }
}
