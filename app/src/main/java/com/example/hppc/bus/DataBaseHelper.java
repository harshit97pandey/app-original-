package com.example.hppc.bus;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by hp pc on 03/01/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {


    //The Android's default system path of your application database.
    private static String DB_PATH = "/Android/data/com.example.hppc.bus/databases/";

    private static String DB_NAME = "time table.db";

    private SQLiteDatabase myDataBase;



    private final Context context;
    private SQLiteDatabase db;


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);

        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.context = context;
    }

    // Creates a empty database on the system and rewrites it with your own database.
    public void create() throws IOException
    {
        boolean dbExist = checkDataBase();

        if (dbExist)
        {
            //do nothing - database already exist
        }
        else {
            // By calling this method and empty database will be created into the default system path
            // of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    // Check if the database exist to avoid re-copy the data
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String path = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // database don't exist yet.
            e.printStackTrace();
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    // copy your assets db
    private void copyDataBase() throws IOException {
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    //Open the database
    public boolean open() {
        try {
            String myPath = DB_PATH + DB_NAME;
            db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            return true;
        } catch (SQLException sqle) {
            db = null;
            return false;
        }
    }

    @Override
    public synchronized void close() {
        if (db != null)
            db.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
    public String[] check(String loc1,String loc2)
    {

        final String TABLE_NAME = "Route";
        String h[]=new String[8];

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
        String data      = null;
        int count=0;
        int c=0;
        int d=0;
        int e=0;
        int flag=1;
String s="";

        if (cursor.moveToFirst())
        {
            do
            {
                for (int i=1;i<=cursor.getColumnCount()-1;i++)
                {
                    if(cursor.getString(i)!= null)
                    {


                        if((cursor.getString(i).equals(loc1))||(cursor.getString(i).equals(loc2)))
                        {

                            if((cursor.getString(i).equals(loc1)))
                            {
                                if(e==0)
                                d=1;

                            }
                            if((cursor.getString(i).equals(loc2)))
                            {
                                e=1;

                            }
                            count++;
                        }
                    }
                }



                if ((count==2)&&(d==1))
                {
                    h[c++]=cursor.getString(0);
                    count=0;
                    flag=1;
                    d=0;
                }
                e=0;
                flag=1;
                d=0;
                count=0;

            } while (cursor.moveToNext());


        }
        cursor.close();


        return h;
    }


    public String[][] check3(String loc1,String loc2)
    {
        int d=0;
        int e=0;
        int flag=1;

        final String TABLE_NAME = "Route";
        String h[][]=new String[8][2];

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
        String data      = null;
        int count=0;
        int c=0;


        if (cursor.moveToFirst())
        {
            do
            {
                for (int i=1;i<=cursor.getColumnCount()-1;i++)
                {
                    if(cursor.getString(i)!= null)
                    {
                        if((cursor.getString(i).equals(loc1))||(cursor.getString(i).equals(loc2)))
                        {

                            count++;
                        }
                    }
                }
                if ((count==2))
                {
                    h[c][0]=cursor.getString(1);
                    h[c++][1]=cursor.getString(cursor.getColumnCount()-1);
                    count=0;

                }

            } while (cursor.moveToNext());
        }
        cursor.close();


        return h;
    }

    public  String[][] check2(String dbname)
    {


        String r[][]=new String[11][11];
        int c=0;
        int d=0;
        int start=0;
        int end=0;

        try {
            int x=check5(dbname);
           Log.e("x=",""+x);
            String selectQuery = "SELECT  * FROM " +dbname;
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery(selectQuery, null);

            start=0;
            end=cursor.getColumnCount()-1;
            if(x==-1)
            {cursor.moveToLast();
                do {
                    for(int i=start;i<=end;i=i+1)
                    {
                        if(i==1){
                            continue;
                        }


                        r[c][d++] =cursor.getString(i);
                    }
                    d=0;
                    c++;

                }while (cursor.moveToPrevious());


            }
            else {
                cursor.moveToFirst();
                do {
                    for(int i=start;i<=end;i=i+x)
                    {
                        if(i==1){
                            continue;
                        }


                        r[c][d++] =cursor.getString(i);
                    }
                    d=0;
                    c++;

                }while (cursor.moveToNext());


            }



        }
        catch (Exception e)
        {

        }


        return r;
    }

    private int check5(String dbname) {
        int x=0;
        String selectQuery = "SELECT  * FROM " +"Change";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        do{

            if(cursor.getString(0).equals(dbname))
            {
                x=Integer.parseInt(cursor.getString(1));
                break;
            }
        }
        while(cursor.moveToNext());
        cursor.close();
        db.close();



        return -x;
    }


    public String[][] Show(String dbname)
    {
        String result[][]=new String[15][15];
        final String TABLE_NAME = dbname;
        result[0][0]=dbname;

        String selectQuery = "SELECT  * FROM " +dbname;
        SQLiteDatabase db  = this.getReadableDatabase();

        Cursor cursor      = db.rawQuery(selectQuery, null);

        String data      = null;
        int count=0;
        int c=0;
        int d=0;


        if (cursor.moveToFirst())
        {
            do
            {
             for (int i=2;i<=cursor.getColumnCount()-1;i++)
             {
                 result[c][d++]=cursor.getString(i);
             }
                d=0;
                c++;


            } while (cursor.moveToNext());
        }

        cursor.close();



        return result;


    }

    public String Route(String loc1,String loc2)
    {



/*
            String r="";
            String query = "SELECT * FROM " + "Route";
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor = db.rawQuery(query, null);
            int i=0;

            while (cursor.moveToNext())
            {
                int c=0;
                for(int j=1;j<=7;j++)
                {
                    if((cursor.getString(j).equals(loc1))||(cursor.getString(j).equals(loc2)))
                    {
                        c++;
                    }

                }
                if(c==2)
                {
                    i++;
                    r=r+cursor.getString(0);

                }

            }
            r=r+i;
            cursor.close();
            */




            return "UP65AT 4044";

    }





    public  String[] check4(String dbname)
    {


        String r[]=new String[11];
        int c=0;
        int d=0;
        try {
            String selectQuery = "SELECT  * FROM " +dbname;
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();

            do {



                    r[c++] =cursor.getString(0);


            }while (cursor.moveToNext());
        }
        catch (Exception e)
        {

        }


        return r;
    }
    public String[][] check10(String bus[],String from,String to,int time)
    {
        for(int i=0;i<8;i++)
        {
            if(bus[i]!=null)
            Log.e("bus in db",bus[i]);
        }

        String data[][] = new String[40][4];
        int c=0;
        Calendar cd=Calendar.getInstance();
        SimpleDateFormat df=new SimpleDateFormat("HH:mm");
        String timeA=df.format(cd.getTime());
        int d=0;
        for (int i = 0; i < 8; i++) {
            try {
            String dbname = bus[i];

            if(dbname==null)
                break;
            String endTime = "";
            String selectQuery = "SELECT  * FROM " + dbname;
            SQLiteDatabase db = this.getReadableDatabase();


            Cursor cursor = db.rawQuery(selectQuery, null);
            Cursor cursor1 = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            cursor1.moveToFirst();



                do
                {
                    if(cursor.getString(0).equals(from))
                    {


                        for(int z=2;z<=cursor.getColumnCount()-1;z++)
                        {
                            if(timeDiff(cursor.getString(z),timeA,time)>0)
                            {
                                data[c][0] = bus[i];
                                data[c][1] = cursor.getString(z);
                                do
                                {
                                    if(cursor1.getString(0).equals(to))
                                    {
                                        data [c][2]=cursor1.getString(z);
                                        cursor1.moveToFirst();
                                        break;
                                    }
                                }while(cursor1.moveToNext());
                                data[c][3]=Integer.toString(timeDiff(cursor.getString(z),timeA,time));
                                c++;
                            }

                        }


                    }



                }while(cursor.moveToNext());
                cursor.close();
                cursor1.close();
                for(int j=0;j<39;j++)
                {
                    try {
                        if (data[j][1] != null)
                            Log.e("BUs out db", data[i][1]);
                    }
                    catch (Exception e)
                    {
                        Log.e("eeee",e.toString());
                    }
                }
            }
            catch (Exception e)
            {
                Log.e("Error at DB",e.toString());
            }

                }



        for(int i=0;i<39;i++)
        {for(int j=i+1; j<40;j++)
        {
            if(data[j][3]==null||data[i][3]==null)
                break;
            if(Integer.parseInt(data[j][3])<Integer.parseInt(data[i][3]))
            {
                String t=data[j][0];
                data[j][0]=data[i][0];
                data[i][0]=t;
               t=data[j][1];
                data[j][1]=data[i][1];
                data[i][1]=t;
                t=data[j][2];
                data[j][2]=data[i][2];
                data[i][2]=t;
                t=data[j][3];
                data[j][3]=data[i][3];
                data[i][3]=t;

            }


        }}


            return data;

        }



    public String[][] check7(String name,String ActualTime,int time)
    {
        String data[][]=new String[40][6];
        int c=0;
        int flag=0;
        String endLoc="";
        int j=0;
        String table[]={"UP65AT4044","UP65AT4091","UP65AT5142","UP65AT6035","UP65AT5692","UP65AT5877","UP65AT5878","UP65AT7344"};
        for(int i=0;i<8;i++)
        {
            String dbname=table[i];

            String endTime="";
            if(i<=3)
                endLoc="CANTT.";
            else
            endLoc="LANKA";
            Log.e("Checkin Bus:",table[i]+" End Loc "+endLoc);
            String selectQuery = "SELECT  * FROM " +dbname;
            SQLiteDatabase db = this.getReadableDatabase();


            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            try {


                do {




                   if(cursor.getString(0).equals(name))
                   {
                       Log.e("Bus Stop:"," "+cursor.getString(0));
                       for (j=2;j<=cursor.getColumnCount()-1;j++)
                       {
                           if(timeDiff(cursor.getString(j),ActualTime,time)>0)
                           {

                               if(!(name.equals(endLoc))) {

                                   Log.e("Time:", " " + cursor.getString(j));
                                   data[c][0] = dbname;
                                   data[c][1] = name;
                                   data[c][2] = cursor.getString(j);
                                   data[c][3] = endLoc;
                                   data[c][5] = Integer.toString(timeDiff(cursor.getString(j), ActualTime, time));
                                   flag = 1;
                                   Log.e("Flag", "" + flag);

                                   c++;
                               }

                           }
                           /*
                           if(flag==1)
                           {
                               cursor.moveToFirst();

                               do {
                                   if(cursor.getString(0).equals(endLoc))
                                   {
                                       data[c][4]=cursor.getString(j);
                                       flag=2;
                                       break;
                                   }


                               }while (cursor.moveToNext());

                           }
                           */


                       }

                   }





                }while (cursor.moveToNext());
            }

            catch (Exception e)
            {
                Log.e("Error At 1",e.toString()+" in "+table[i]+" at "+cursor.getString(0));


            }

        }

        for(int z=0;z<40;z++)
        {
            Log.e("data",z+" "+data[z][0]+" "+data[z][1]);
        }


        return  data;

    }
    public int timeDiff(String t,String ActualTime,int time)
    {
        int t1=Integer.parseInt(t.substring(0,t.indexOf(':')))-Integer.parseInt(ActualTime.substring(0,t.indexOf(':')));
        int t2=Integer.parseInt(t.substring(t.indexOf(':')+1))-Integer.parseInt(ActualTime.substring(t.indexOf(':')+1));
        t1=t1*60+t2;
        if(t1<0)
            return -1;
        else if(t1>time)
            return  -2;
        else
            return (t1);



    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

}