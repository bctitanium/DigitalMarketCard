package com.example.digitalmarketcard.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DAL_Items
{
    private DB_Connection dbc = new DB_Connection();

    /**
     * lấy tên của items từ id của nó
     * @param ID_Items
     * @return
     */
    public String getItemNameFromID(String ID_Items)
    {
        String kq = "";

        PreparedStatement ps = null; //statement có truyền tham số, dùng để thực thi sql
        ResultSet rs = null; //dùng để đọc kết quả từ việc thực thì sql

        String sql = "Select Items_Name from Items where ID_Items = ?";

        try
        {
            //kết nối tới database
            Connection connection = dbc.getConnection();
            //truyền sql vào máy thực thi
            ps = connection.prepareStatement(sql);
            ps.setString(1, ID_Items);
            rs = ps.executeQuery(); //thực thi sql

            if (rs.next()) //đọc được kết quả
            {
                kq = rs.getString(1); //lấy ra dữ liệu của cột đầu tiên
            }
            else //đọc không được kết quả
            {
                kq = ""; //không có type đó
            }

            ps.close();
            rs.close();
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return kq;
    }

    /**
     * lấy ra giá của item đó
     * @param ID_Items
     * @return
     */
    public int getPriceFromID(String ID_Items)
    {
        int kq = -1;

        PreparedStatement ps = null; //statement có truyền tham số, dùng để thực thi sql
        ResultSet rs = null; //dùng để đọc kết quả từ việc thực thì sql

        String sql = "Select Price from Items where ID_Items = ?";

        try
        {
            //kết nối tới database
            Connection connection = dbc.getConnection();
            //truyền sql vào máy thực thi
            ps = connection.prepareStatement(sql);
            ps.setString(1, ID_Items);
            rs = ps.executeQuery(); //thực thi sql

            if (rs.next()) //đọc được kết quả
            {
                kq = rs.getInt(1); //lấy ra dữ liệu của cột đầu tiên
            }
            else //đọc không được kết quả
            {
                kq = -1; //không có type đó
            }

            ps.close();
            rs.close();
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return kq;
    }

    /**
     * tạo chi tiết hóa đơn
     * @param ID_Receipts
     * @param ID_Items
     * @param SoLuong
     * @return
     */
    public boolean Create_Detailed_Receipts(int ID_Receipts, String ID_Items, int SoLuong, int DonGia)
    {
        //true thì thêm thành công, false thì ngược lại, mặc định là false vì nếu có sự cố nhập liệu thì vẫn trả về false cho an toàn
        boolean kq = false;
        PreparedStatement ps = null; //statement có truyền tham số, dùng để thực thi sql

        String sql = "Insert into Detailed_Receipts(ID_Receipts, ID_Items, SoLuong, DonGia) values(?, ?, ?, ?)";

        try
        {
            //kết nối tới database
            Connection connection = dbc.getConnection();
            //truyền sql vào máy thực thi
            ps = connection.prepareStatement(sql);
            ps.setInt(1, ID_Receipts); //truyền tham số ID_Receipts, dựa theo số thứ tự của dấu '?' ở trên sql
            ps.setString(2, ID_Items); //truyền tham số ID_Items, dựa theo số thứ tự của dấu '?' ở trên sql
            ps.setInt(3, SoLuong); //truyền tham số SoLuong, dựa theo số thứ tự của dấu '?' ở trên sql
            ps.setInt(4, DonGia);

            if (ps.executeUpdate() != 0) //ghi được kết quả
            {
                kq = true; //thì thêm thành công
            }
            else //ghi không được kết quả
            {
                kq = false; //thì thêm không thành công
            }

            ps.close();
            connection.close();
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return kq; //trả về kết quả của kq
    }

    /**
     * tạo hóa đơn
     * @param TotalPrice
     * @param ID_Account
     * @return
     */
    public boolean Create_Receipts(int TotalPrice, int ID_Account)
    {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

        //true thì thêm thành công, false thì ngược lại, mặc định là false vì nếu có sự cố nhập liệu thì vẫn trả về false cho an toàn
        boolean kq = false;
        PreparedStatement ps = null; //statement có truyền tham số, dùng để thực thi sql

        String sql = "Insert into Receipts(TotalPrice, ID_Account, Date) values(?, ?, ?)";

        try
        {
            //kết nối tới database
            Connection connection = dbc.getConnection();
            //truyền sql vào máy thực thi
            ps = connection.prepareStatement(sql);
            ps.setInt(1, TotalPrice); //truyền tham số TotalPrice, dựa theo số thứ tự của dấu '?' ở trên sql
            ps.setInt(2, ID_Account); //truyền tham số ID_Account, dựa theo số thứ tự của dấu '?' ở trên sql
            ps.setString(3, df.format(Calendar.getInstance().getTime()));

            if (ps.executeUpdate() != 0) //ghi được kết quả
            {
                kq = true; //thì thêm thành công
            }
            else //ghi không được kết quả
            {
                kq = false; //thì thêm không thành công
            }

            ps.close();
            connection.close();
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return kq; //trả về kết quả của kq
    }

    /**
     * get id account từ phone number
     * @param PhoneNumber
     * @return
     */
    public int get_ID_Account_fromPhoneNumber(String PhoneNumber)
    {
        int kq = -1;

        PreparedStatement ps = null; //statement có truyền tham số, dùng để thực thi sql
        ResultSet rs = null; //dùng để đọc kết quả từ việc thực thì sql

        String sql = "Select ID_Account from User_Account where PhoneNumber = ?";

        try
        {
            //kết nối tới database
            Connection connection = dbc.getConnection();
            //truyền sql vào máy thực thi
            ps = connection.prepareStatement(sql);
            ps.setString(1, PhoneNumber);
            rs = ps.executeQuery(); //thực thi sql

            if (rs.next()) //đọc được kết quả
            {
                kq = rs.getInt(1); //lấy ra dữ liệu của cột đầu tiên
            }
            else //đọc không được kết quả
            {
                kq = -1; //không có type đó
            }

            ps.close();
            rs.close();
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return kq;
    }

    public int get_ID_Receipts_fromIDAccount(int ID_Account)
    {
        int kq = -1;

        PreparedStatement ps = null; //statement có truyền tham số, dùng để thực thi sql
        ResultSet rs = null; //dùng để đọc kết quả từ việc thực thì sql

        String sql = "Select top 1 ID_Receipts from Receipts where ID_Account = ? order by ID_Receipts desc";

        try
        {
            //kết nối tới database
            Connection connection = dbc.getConnection();
            //truyền sql vào máy thực thi
            ps = connection.prepareStatement(sql);
            ps.setInt(1, ID_Account);
            rs = ps.executeQuery(); //thực thi sql

            if (rs.next()) //đọc được kết quả
            {
                kq = rs.getInt(1); //lấy ra dữ liệu của cột đầu tiên
            }
            else //đọc không được kết quả
            {
                kq = -1; //không có type đó
            }

            ps.close();
            rs.close();
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return kq;
    }

    /**
     * hàm lấy mã của items từ tên của nó
     * @param Items_Name
     * @return
     */
    public String get_ID_Item_fromItemsName(String Items_Name)
    {
        String kq = "";

        PreparedStatement ps = null; //statement có truyền tham số, dùng để thực thi sql
        ResultSet rs = null; //dùng để đọc kết quả từ việc thực thì sql

        String sql = "Select ID_Items from Items where Items_Name = ?";

        try
        {
            //kết nối tới database
            Connection connection = dbc.getConnection();
            //truyền sql vào máy thực thi
            ps = connection.prepareStatement(sql);
            ps.setString(1, Items_Name);
            rs = ps.executeQuery(); //thực thi sql

            if (rs.next()) //đọc được kết quả
            {
                kq = rs.getString(1); //lấy ra dữ liệu của cột đầu tiên
            }
            else //đọc không được kết quả
            {
                kq = ""; //không có type đó
            }

            ps.close();
            rs.close();
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return kq;
    }

    public int get_Total_Items_from_Items_Name(String Items_Name)
    {
        int kq = 0;

        PreparedStatement ps = null; //statement có truyền tham số, dùng để thực thi sql
        ResultSet rs = null; //dùng để đọc kết quả từ việc thực thì sql

        String sql = "Select Total from Items where Items_Name = ?";

        try
        {
            //kết nối tới database
            Connection connection = dbc.getConnection();
            //truyền sql vào máy thực thi
            ps = connection.prepareStatement(sql);
            ps.setString(1, Items_Name);
            rs = ps.executeQuery(); //thực thi sql

            if (rs.next()) //đọc được kết quả
            {
                kq = rs.getInt(1); //lấy ra dữ liệu của cột đầu tiên
            }
            else //đọc không được kết quả
            {
                kq = 0; //không có type đó
            }

            ps.close();
            rs.close();
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return kq;
    }

    public boolean Update_Total_Items(int slton, int slmua, String ID_Items)
    {
        boolean kq = false;
        PreparedStatement ps = null; //statement có truyền tham số, dùng để thực thi sql
        //ResultSet rs = null; //dùng để đọc kết quả từ việc thực thì sql

        String sql = "Update Items set Total=? where ID_Items=?";

        try
        {
            //kết nối tới database
            Connection connection = dbc.getConnection();
            //truyền sql vào máy thực thi
            ps = connection.prepareStatement(sql);
            ps.setInt(1, slton-slmua);
            ps.setString(2, ID_Items);

            if (ps.executeUpdate() != 0) //ghi được kết quả
            {
                kq = true; //thì sửa thành công
            }
            else //ghi không được kết quả
            {
                kq = false; //thì sửa không thành công
            }

            ps.close();
            //rs.close();
            connection.close();
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return kq; //trả về kết quả của kq
    }
}
