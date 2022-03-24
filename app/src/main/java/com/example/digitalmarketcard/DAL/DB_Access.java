package com.example.digitalmarketcard.DAL;

import java.sql.*;

public class DB_Access
{
    private Connection connection;
    private Statement statement;

    public DB_Access()
    {
        try
        {
            DB_Connection dbc = new DB_Connection();
            connection = dbc.getConnection();
            statement = connection.createStatement();
        }
        catch (SQLException e)
        {

        }
    }

    public int Update(String str)
    {
        try
        {
            int i = statement.executeUpdate(str);
            return i;
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    public ResultSet Query(String str)
    {
        try
        {
            ResultSet rs = statement.executeQuery(str);
            return rs;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
