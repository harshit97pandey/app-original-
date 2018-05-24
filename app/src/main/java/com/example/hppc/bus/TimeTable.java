package com.example.hppc.bus;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by hp pc on 29/12/2016.
 */

public class TimeTable extends Activity {
    String Loc[]={"Bus Stop","CANTT..","VIDYAPEETH","SIGRA","RATHYATRA","KAMACHA","RAVINDRAPURI","LANKA"};
    Spinner sp1=(Spinner)findViewById(R.id.sptime);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);
        ArrayAdapter<String> ar=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Loc);
        sp1.setAdapter(ar);

    }

    public void showTable(View view)
    {
        int i=sp1.getSelectedItemPosition();
    }
}
