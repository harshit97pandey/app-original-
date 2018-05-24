package com.example.hppc.bus;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by hp pc on 10/01/2017.
 */

public class HelpLine extends Activity {

    LinearLayout b1l1;
    LinearLayout b2l1;
    DataBaseHelper1 db;
    int MY_PER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpline);
        LinearLayout b1l1 = (LinearLayout) findViewById(R.id.b1l1);
        LinearLayout b2l1 = (LinearLayout) findViewById(R.id.b2l1);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.CALL_PHONE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CALL_PHONE},
                        MY_PER);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public String getStringName(String s) {
        if (s.charAt(0) == '2')
            s = s.substring(3);
        s = s.replace(" ", "");
        s = s.toLowerCase();
        return s;

    }


    public void b1(View view) {
        remove();Button v = (Button)view;

        String tablename = getStringName(v.getText().toString());
        Log.e("k",tablename);
        db = new DataBaseHelper1(this);
        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        db.open();

        String list[] = new String[15];
        list = db.Show(tablename);
        try {
            LinearLayout b1l1 = (LinearLayout) findViewById(R.id.b1l1);

            for (int i = 0; i < 15; i++) {
                if (list[i] == null)
                    break;
                Log.e("", list[i]);
                Button t = new Button(this);t.setBackgroundColor(Color.WHITE);
                t.setBackground(getResources().getDrawable(R.drawable.phone));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10, 10, 10, 10);
                t.setLayoutParams(layoutParams);
                t.setText(list[i]);
                t.setId(i);

            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button tt = (Button) view;
                    String ttt = tt.getText().toString();
                   ttt= ttt.trim();
                   ttt= ttt.substring(ttt.indexOf('\n') + 1);
                   ttt= ttt.replace(" ", "");




                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + ttt));
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(callIntent);


                }
            });

                b1l1.addView(t);
            }
        }catch (Exception e)
        {
            Log.e("error",e.toString());
        }

        }

    public void b2(View view) {

        remove();Button v = (Button)view;

        String tablename = getStringName(v.getText().toString());
        Log.e("k",tablename);
        db = new DataBaseHelper1(this);
        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        db.open();

        String list[] = new String[15];
        list = db.Show(tablename);
        try {
            LinearLayout b2l1 = (LinearLayout) findViewById(R.id.b2l1);

            for (int i = 0; i < 15; i++) {
                if (list[i] == null)
                    break;
                Log.e("", list[i]);
                Button t = new Button(this);t.setBackgroundColor(Color.WHITE);
                t.setText(list[i]);
                t.setBackground(getResources().getDrawable(R.drawable.phone));
                t.setId(i);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10, 10, 10, 10);
                t.setLayoutParams(layoutParams);

                t.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button tt = (Button) view;
                        String ttt = tt.getText().toString();
                       ttt= ttt.trim();
                       ttt= ttt.substring(ttt.indexOf("\n") + 1);
                        ttt.replace(" ", "");


                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + ttt));
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(callIntent);


                    }
                });


                b2l1.addView(t);
            }
        }catch (Exception e)
        {
            Log.e("error",e.toString());
        }

    }

    public void b3(View view) {
        remove();Button v = (Button)view;

        String tablename = getStringName(v.getText().toString());
        Log.e("k",tablename);
        db = new DataBaseHelper1(this);
        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        db.open();

        String list[] = new String[15];
        list = db.Show(tablename);
        try {
            LinearLayout b3l1 = (LinearLayout) findViewById(R.id.b3l1);
            

            for (int i = 0; i < 15; i++) {
                if (list[i] == null)
                    break;
                Log.e("", list[i]);
                Button t = new Button(this);t.setBackgroundColor(Color.WHITE);
                t.setText(list[i]);
                t.setId(i);
                t.setBackground(getResources().getDrawable(R.drawable.phone));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10, 10, 10, 10);
                t.setLayoutParams(layoutParams);

                t.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button tt = (Button) view;
                        String ttt = tt.getText().toString();
                        ttt= ttt.trim();
                        ttt= ttt.substring(ttt.indexOf("\n") + 1);
                        ttt.replace(" ", "");


                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + ttt));
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(callIntent);


                    }
                });


                b3l1.addView(t);
            }
        }catch (Exception e)
        {
            Log.e("error",e.toString());
        }
    }

    public void b4(View view) {

        remove();Button v = (Button)view;

        String tablename = getStringName(v.getText().toString());
        Log.e("k",tablename);
        db = new DataBaseHelper1(this);
        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        db.open();

        String list[] = new String[15];
        list = db.Show(tablename);
        try {
            LinearLayout b4l1 = (LinearLayout) findViewById(R.id.b4l1);

            for (int i = 0; i < 15; i++) {
                if (list[i] == null)
                    break;
                Log.e("", list[i]);
                Button t = new Button(this);t.setBackgroundColor(Color.WHITE);
                t.setText(list[i]);
                t.setId(i);
                t.setBackground(getResources().getDrawable(R.drawable.phone));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10, 10, 10, 10);
                t.setLayoutParams(layoutParams);

                t.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button tt = (Button) view;
                        String ttt = tt.getText().toString();
                        ttt= ttt.trim();
                        ttt= ttt.substring(ttt.indexOf("\n") + 1);
                        ttt.replace(" ", "");


                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + ttt));
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(callIntent);


                    }
                });


                b4l1.addView(t);
            }
        }catch (Exception e)
        {
            Log.e("error",e.toString());
        }
    }


    public void b6(View view) {

        remove();Button v = (Button)view;

        String tablename = getStringName(v.getText().toString());
        Log.e("k",tablename);
        db = new DataBaseHelper1(this);
        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        db.open();

        String list[] = new String[15];
        list = db.Show(tablename);
        try {
            LinearLayout b6l1 = (LinearLayout) findViewById(R.id.b6l1);

            for (int i = 0; i < 15; i++) {
                if (list[i] == null)
                    break;
                Log.e("", list[i]);
                Button t = new Button(this);t.setBackgroundColor(Color.WHITE);
                t.setBackgroundColor(Color.WHITE);
                t.setText(list[i]);
                t.setId(i);
                t.setBackground(getResources().getDrawable(R.drawable.phone));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10, 10, 10, 10);
                t.setLayoutParams(layoutParams);

                t.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button tt = (Button) view;
                        String ttt = tt.getText().toString();
                        ttt= ttt.trim();
                        ttt= ttt.substring(ttt.indexOf("\n") + 1);
                        ttt.replace(" ", "");


                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + ttt));
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(callIntent);


                    }
                });


                b6l1.addView(t);
            }
        }catch (Exception e)
        {
            Log.e("error",e.toString());
        }
    }

    public void b7(View view) {
        remove();Button v = (Button)view;

        String tablename = getStringName(v.getText().toString());
        tablename="medicalstore";
        Log.e("k",tablename);

        db = new DataBaseHelper1(this);
        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        db.open();
        String list[] = new String[15];


        list = db.Show(tablename);


        try {
            LinearLayout b7l1 = (LinearLayout) findViewById(R.id.b7l1);

            for (int i = 0; i < 15; i++) {
                if (list[i] == null)
                    break;
                Log.e("", list[i]);
                Button t = new Button(this);t.setBackgroundColor(Color.WHITE);
                t.setText(list[i]);
                t.setId(i);
                t.setBackground(getResources().getDrawable(R.drawable.phone));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10, 10, 10, 10);
                t.setLayoutParams(layoutParams);

                t.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button tt = (Button) view;
                        String ttt = tt.getText().toString();
                        ttt= ttt.trim();
                        ttt= ttt.substring(ttt.indexOf("\n") + 1);
                        ttt.replace(" ", "");


                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + ttt));
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(callIntent);


                    }
                });


                b7l1.addView(t);
            }
        }catch (Exception e)
        {
            Log.e("error",e.toString());
        }
    }
    public void b8(View view) {
        remove();Button v = (Button)view;

        String tablename = getStringName(v.getText().toString());
        Log.e("k",tablename);
        db = new DataBaseHelper1(this);
        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        db.open();

        String list[] = new String[15];
        list = db.Show(tablename);
        try {
            LinearLayout b8l1 = (LinearLayout) findViewById(R.id.b8l1);

            for (int i = 0; i < 15; i++) {
                if (list[i] == null)
                    break;
                Log.e("", list[i]);
                Button t = new Button(this);
                t.setBackgroundColor(Color.WHITE);
                t.setBackground(getResources().getDrawable(R.drawable.phone));
                t.setText(list[i]);
                t.setId(i);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10, 10, 10, 10);
                t.setLayoutParams(layoutParams);
                t.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button tt = (Button) view;
                        String ttt = tt.getText().toString();
                        ttt= ttt.trim();
                        ttt= ttt.substring(ttt.indexOf("\n") + 1);
                        ttt.replace(" ", "");



                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + ttt));
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(callIntent);


                    }
                });


                b8l1.addView(t);
            }
        }catch (Exception e)
        {
            Log.e("error",e.toString());
        }
    }
    public void b9(View view) {
        remove();Button v = (Button)view;

        String tablename = getStringName(v.getText().toString());
        Log.e("k",tablename);
        db = new DataBaseHelper1(this);
        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        db.open();

        String list[] = new String[15];
        list = db.Show(tablename);
        try {
            LinearLayout b9l1 = (LinearLayout) findViewById(R.id.b9l1);

            for (int i = 0; i < 15; i++) {
                if (list[i] == null)
                    break;
                Log.e("", list[i]);
                Button t = new Button(this);t.setBackgroundColor(Color.WHITE);
                t.setText(list[i]);
                t.setId(i);
                t.setBackground(getResources().getDrawable(R.drawable.phone));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10, 10, 10, 10);
                t.setLayoutParams(layoutParams);

                t.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button tt = (Button) view;
                        String ttt = tt.getText().toString();
                        ttt= ttt.trim();
                        ttt= ttt.substring(ttt.indexOf("\n") + 1);
                        ttt.replace(" ", "");


                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + ttt));
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(callIntent);


                    }
                });


                b9l1.addView(t);
            }
        }catch (Exception e)
        {
            Log.e("error",e.toString());
        }
    }
    public void remove()
    {

        LinearLayout b9l1 = (LinearLayout) findViewById(R.id.b9l1);
        b9l1.removeAllViews();
        LinearLayout b8l1 = (LinearLayout) findViewById(R.id.b8l1);
        b8l1.removeAllViews();
        LinearLayout b7l1 = (LinearLayout) findViewById(R.id.b7l1);
        b7l1.removeAllViews();
        LinearLayout b6l1 = (LinearLayout) findViewById(R.id.b6l1);
        b6l1.removeAllViews();

        LinearLayout b4l1 = (LinearLayout) findViewById(R.id.b4l1);
        b4l1.removeAllViews();
        LinearLayout b3l1 = (LinearLayout) findViewById(R.id.b3l1);
        b3l1.removeAllViews();
        LinearLayout b2l1 = (LinearLayout) findViewById(R.id.b2l1);
        b2l1.removeAllViews();
        LinearLayout b1l1 = (LinearLayout) findViewById(R.id.b1l1);
        b1l1.removeAllViews();


    }
}

