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

/**
 * Created by hp pc on 03/01/2017.
 */

public class DataBaseHelper1 extends SQLiteOpenHelper {


    //The Android's default system path of your application database.
    private static String DB_PATH = "/Android/data/com.example.hppc.bus/databases/";

    private static String DB_NAME = "Helpline.db";

    private SQLiteDatabase myDataBase;



    private final Context context;
    private SQLiteDatabase db;


    public DataBaseHelper1(Context context) {
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
                                d=i;
                            }
                            if((cursor.getString(i).equals(loc2)))
                            {
                                e=i;
                            }
                            count++;
                        }
                    }
                }
                if ((count==2))
                {
                    h[c++]=cursor.getString(0);
                    count=0;
                }

            } while (cursor.moveToNext());
        }
        cursor.close();


        return h;
    }


    public String[][] check3(String loc1,String loc2)
    {
        int d=0;
        int e=0;

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
                            if((cursor.getString(i).equals(loc1)))
                            {
                                d=i;
                            }
                            if((cursor.getString(i).equals(loc2)))
                            {
                                e=i;
                            }
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


        String r[][]=new String[11][4];
        int c=0;
        int d=0;
        try {
            String selectQuery = "SELECT  * FROM " +dbname;
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();

            do {
                for(int i=0;i<=cursor.getColumnCount()-1;i++)
                {


                    r[c][d++] =cursor.getString(i);
                }
                d=0;
                c++;

            }while (cursor.moveToNext());
        }
        catch (Exception e)
        {

        }


        return r;
    }




    public String[] Show(String dbname)
    {
        String result[]=new String[15];
        final String TABLE_NAME = dbname;


        String selectQuery = "SELECT  * FROM " +dbname;
        SQLiteDatabase db  = this.getReadableDatabase();

        Cursor cursor      = db.rawQuery(selectQuery, null);

        String data      = null;
        int count=0;
        int c=0;
        int d=0;


        if (cursor.moveToFirst())
        {
           do {
               result[d++] = cursor.getString(0) + "\n" + cursor.getString(1);
               Log.e("",cursor.getString(0) + "\n" + cursor.getString(1));


           }while (cursor.moveToNext());



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

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

}