package com.example.hppc.bus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by hp pc on 28/12/2016.
 */
public class Fare extends Activity  {
    Spinner fromL,toL,no; double fare=0.0;int fromn =0;
    int ton=0;
    int farec1[][]={{6,6,6,10,10,12},{0,6,6,6,10,12},{0,0,6,6,10,10},{0,0,0,6,6,10},{0,0,0,0,6,6},{0,0,0,0,0,6}};
    String loc1[]={"CANTT.","VIDYAPEETH","SIGRA","RATHYATRA","KAMACHHA","RAVINDRAPURI","LANKA"};
    String loc2[]={"LANKA","SUNDARPUR","DLW","MANDUADIH","LAHARTARA","HARI NAGAR","CANTT."};
    int farec2[][]={{6,6,10,12,15,15},{0,6,6,10,12,15},{0,0,6,10,10,12},{0,0,0,6,10,12},{0,0,0,0,6,10},{0,0,0,0,0,6}};

    String Loc[]={"Choose Destination","CANTT.","DLW","HARI NAGAR","KAMACHHA","LAHARTARA","LANKA","MANDUADIH","RATHYATRA","RAVINDRAPURI","SIGRA","SUNDARPUR","VIDYAPEETH"};

    int tran=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fare);
        fromL=(Spinner)findViewById(R.id.spf);
        toL=(Spinner)findViewById(R.id.spt);
        no=(Spinner)findViewById(R.id.spn);






        String noA[]={"No. Of Travellers","1","2","3","4","5","6","7","8"};

        ArrayAdapter<CharSequence> loc=new ArrayAdapter<CharSequence>(this,android.R.layout.simple_list_item_1,Loc);
        fromL.setAdapter(loc);
        toL.setAdapter(loc);
        ArrayAdapter<CharSequence> noa=new ArrayAdapter<CharSequence>(this,android.R.layout.simple_list_item_1,noA);
        no.setAdapter(noa);
        fromL.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






    }

    public void onfare(View view) {
        fromn= fromL.getSelectedItemPosition();
        ton=toL.getSelectedItemPosition();
        tran=0;

        tran=no.getSelectedItemPosition();

        if((fromn!=0)&&(ton!=0)) {
            int route = 0;
            fare = 0.0;
            try {
                if (Loc[fromn].equals("LANKA") && Loc[ton].equals("CANTT.")) {
                    Log.e("Route:", "" + 2);
                    fare = 15.0 * tran;
                } else if (Loc[fromn].equals("CANTT.") && Loc[ton].equals("LANKA")) {
                    Log.e("Route:", "" + 1);
                    fare = 12.0 * tran;
                } else if (((IsPresent1(Loc[fromn]) >= 0)) && ((IsPresent1(Loc[ton]) > 0))) {

                    //Log.e("Route:",""+1+" between"+IsPresent1(Loc[fromn])+" "+IsPresent1(Loc[ton]));
                    fare = farec1[IsPresent1(Loc[fromn])][IsPresent1(Loc[ton]) - 1] * tran;

                } else if (((IsPresent2(Loc[fromn]) >= 0)) && ((IsPresent2(Loc[ton]) > 0))) {
                    // Log.e("Route:",""+2+" between"+IsPresent2(Loc[fromn])+" "+IsPresent2(Loc[ton]));
                    fare = farec2[IsPresent2(Loc[fromn])][IsPresent2(Loc[ton]) - 1] * tran;

                } else {
                    // Log.e("Route:",""+0+" "+IsPresent2(Loc[fromn])+" "+IsPresent2(Loc[ton])+" "+IsPresent1(Loc[fromn])+" "+IsPresent1(Loc[ton]));
                    Toast.makeText(this, "No Bus On This Route", Toast.LENGTH_SHORT).show();
                }

            }
            catch (Exception e)
            {
                Log.e("e",e.toString());
                Toast.makeText(this,"No Bus On this Route",Toast.LENGTH_SHORT).show();
            }
        }

        TextView tv1=(TextView)findViewById(R.id.tvf);
        tv1.setText("Your Total Fare is :\n₹ "+fare+" only");
    }
    public int IsPresent1(String s)
    {
        int r=-1;
        for (int i=0;i<loc1.length;i++)
        {
            if(loc1[i].equals(s))
            {
                r=i;
                break;
            }
        }
        return  r;
    } public int IsPresent2(String s)
    {
       int r=-1;
        for (int i=0;i<loc2.length;i++)
        {
            if(loc2[i].equals(s))
            {
                r=i;
                break;
            }
        }
        return  r;
    }

}
