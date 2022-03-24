package com.example.digitalmarketcard.BLL;

import com.example.digitalmarketcard.DAL.DAL_Items;

public class BLL_Items
{
    DAL_Items dali = new DAL_Items();

    public String getItemNameFromID(String ID_Items)
    {
        return dali.getItemNameFromID(ID_Items);
    }

    public int getPriceFromID(String ID_Items)
    {
        return dali.getPriceFromID(ID_Items);
    }

    public boolean Create_Detailed_Receipts(int ID_Receipts, String ID_Items, int SoLuong, int DonGia)
    {
        return dali.Create_Detailed_Receipts(ID_Receipts, ID_Items, SoLuong, DonGia);
    }

    public boolean Create_Receipts(int TotalPrice, int ID_Account)
    {
        return dali.Create_Receipts(TotalPrice, ID_Account);
    }

    public int get_ID_Account_fromPhoneNumber(String PhoneNumber)
    {
        return dali.get_ID_Account_fromPhoneNumber(PhoneNumber);
    }

    public int get_ID_Receipts_fromIDAccount(int ID_Account)
    {
        return dali.get_ID_Receipts_fromIDAccount(ID_Account);
    }

    public String get_ID_Item_fromItemsName(String Items_Name)
    {
        return dali.get_ID_Item_fromItemsName(Items_Name);
    }

    public int get_Total_Items_from_Items_Name(String Items_Name)
    {
        return dali.get_Total_Items_from_Items_Name(Items_Name);
    }

    public boolean Update_Total_Items(int slton, int slmua, String ID_Items)
    {
        return dali.Update_Total_Items(slton, slmua, ID_Items);
    }
}
