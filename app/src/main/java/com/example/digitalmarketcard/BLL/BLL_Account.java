package com.example.digitalmarketcard.BLL;

import com.example.digitalmarketcard.DAL.DAL_Account;

public class BLL_Account
{
    DAL_Account dala = new DAL_Account();

    public boolean Create_Account(String PhoneNumber, String Passcode, String Name)
    {
        return dala.Create_Account(PhoneNumber, Passcode, Name);
    }

    public boolean Check_PhoneNumber(String PhoneNumber)
    {
        return dala.Check_PhoneNumber(PhoneNumber);
    }

    public String get_QR_Code(String PhoneNumber)
    {
        return dala.get_QR_Code(PhoneNumber);
    }

    public boolean Check_Account(String PhoneNumber, String Passcode)
    {
        return dala.Check_Account(PhoneNumber, Passcode);
    }

    public boolean Update_User_Info(String PhoneNumber, String Passcode, String Name, String ID_Account)
    {
        return dala.Update_User_Info(PhoneNumber, Passcode, Name, ID_Account);
    }
}
