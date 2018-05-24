package com.example.hppc.bus;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.nearby.messages.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

/**
 * Created by hp pc on 04/01/2017.
 */

public class TimeTableShow extends Activity {
    int count=0;

    String k="";
    String Url1 = "http://innovtracks.pe.hu/vdc.php?i=";
    String serverresponse = "Finding.....";
    HttpURLConnection urlConnection = null;
    String address;


    int flag=-1;
    int MY_PER;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PER);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        textView=(TextView)findViewById(R.id.ttvl);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetableshow);
        k = getIntent().getStringExtra("Busno");

        Url1 = Url1 + k.substring(k.length()-4);

        TextView textViewk = (TextView) findViewById(R.id.tv1);
        textViewk.setText("Time Table - BUS:" + k);
        try {

            final String urls = Url1 + "&u=admin" + "&p=admin";
            Timer t = new Timer();
//Set the schedule function and rate
            t.scheduleAtFixedRate(new TimerTask()
            {

                @Override
                public void run()
                {

                    try {
                        if((flag==-1)||(flag==1)) {
                            flag=0;
                            new GetMethodDemo().execute(urls);

                        }

                    }
                    catch (Exception e)
                    {
                        Log.e("Errora",e.toString());
                    }


                }


                                  },
                    0,
                    3000);









        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        } finally
        {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }




            DataBaseHelper db;
            db = new DataBaseHelper(this);
            try
            {
                db.create();
            }
            catch (IOException ioe)
            {
                throw new Error("Unable to create database");
            }


            if (db.open())
            {

                String DB_Name = k;


                String tt[][] = db.check2(DB_Name);
                LinearLayout ll1 = (LinearLayout) findViewById(R.id.ll1);
                LinearLayout ll2 = (LinearLayout) findViewById(R.id.ll2);


                for (int i = 0; i < 11; i++)
                {


                    if (tt[i][0] != null) {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);


                        Button button = new Button(this);
                        button.setId(i);
                        button.setLayoutParams(layoutParams1);
                        button.setPadding(5, 5, 5, 5);
                        button.setBackgroundColor(Color.blue(0));

                        button.setText(tt[i][0]);
                        ll1.addView(button);
                        try {

                            LinearLayout linearLayout = new LinearLayout(this);
                            linearLayout.setId(i);
                            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                            ll2.addView(linearLayout);

                            for (int j = 1; j < 10; j++) {

                                Button button1 = new Button(this);
                                button1.setText(tt[i][j]);
                                button1.setLayoutParams(layoutParams);
                                button1.setPadding(5, 5, 5, 5);
                                button1.setBackgroundColor(Color.blue(2));


                                linearLayout.addView(button1);

                            }


                        } catch (Exception e)
                        {
                            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }


            }

        }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {




            // other 'case' lines to check for other
            // permissions this app might request

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    return;
    }

    public void trackbus(View view)
    {
        Intent intent = new Intent(this, Route.class);
        intent.putExtra("busno", k);
        startActivity(intent);


    }

    public void showMap(View view)
    {
        Intent intent = new Intent(this, MapsActivityNew.class);
        intent.putExtra("busno",k);
        startActivity(intent);
    }
    public class GetMethodDemo extends AsyncTask<String, Void, String>
    {
        String server_response;

        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK)
                {

                    server_response = readStream(urlConnection.getInputStream());
                    serverresponse=server_response;
                    try {
                        double lat = Double.parseDouble(serverresponse.substring(0, serverresponse.indexOf(',')));
                        double lon = Double.parseDouble(serverresponse.substring(serverresponse.indexOf(',') + 1));

                        address = getAddress(lat, lon);
                        if(address==null)
                        {
                            address="No Internet Connection";
                            Toast.makeText(getApplicationContext(),address,Toast.LENGTH_SHORT).show();
                        }
                        if(address=="")
                        {
                            address="No Connection Available";
                            Toast.makeText(getApplicationContext(),address,Toast.LENGTH_SHORT).show();

                        }
                        Log.e("Address:",address);
                        flag=1;

                    }
                    catch (Exception e)
                    {
                        address="No Connection Available";
                        Toast.makeText(getApplicationContext(),address,Toast.LENGTH_SHORT).show();

                        Log.e("Errort",e.toString());
                        flag=1;
                    }


                    Log.v("CatalogClient", serverresponse);
                }

            } catch (MalformedURLException e)
            {
                flag=1;
                Log.e("Error",e.toString());
            } catch (IOException e) {
                Log.e("Error",e.toString());
                flag=1;
            }
            catch (Exception e)
            {
                Log.e("Error",e.toString());
                flag=1;

            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                serverresponse = server_response;
                Log.e("Flag", flag + "");
                flag = 1;
                if (flag == 1) {
                    TextView ttvl1 = (TextView) findViewById(R.id.ttvl);
                    if (address == null) {
                        address = "No Internet Connection";
                        Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
                        ttvl1.setText("" + address);
                    } else if (address == "") {
                        address = "No Connection Available";
                        Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
                        ttvl1.setText("" + address);

                    } else

                        ttvl1.setText("Your Bus is at:\n" + address);

                }


                Log.e("Response", "" + serverresponse);


            }
            catch (Exception e)
            {
                Log.e("Error",e.toString());
            }


        }
    }

// Converting InputStream to String

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("Error",e.toString());
                }
            }
        }
        return response.toString();
    }
    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getFeatureName());
                result.append("\n");
                result.append(address.getSubLocality());
                result.append("\n");
                result.append(address.getLocality());
                result.append("\n");


            }
        } catch (IOException e)
        {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
    }
}
