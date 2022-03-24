package com.example.digitalmarketcard.DAL;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.*;

public class DB_Connection
{
    private Connection connection;

    @SuppressLint("NewApi")
    public Connection getConnection()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        connection = null;

        String ip = "192.168.1.5";
        String port = "1433";
        String db_Name = "DigitalMarketCard";
        String db_user = "pepega";
        String db_pass = "123";

        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://"+ip+":"+port+"/"+db_Name, db_user, db_pass);
        }
        catch (Exception e)
        {
            Log.e("ERROR: ", e.getMessage());
        }

        return connection;
    }
}
