package com.example.digitalmarketcard.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAL_Account
{
    private DB_Connection dbc = new DB_Connection();

    /**
     * Tạo tài khoản
     * @param PhoneNumber
     * @param Passcode
     * @param Name
     * @return
     */
    public boolean Create_Account(String PhoneNumber, String Passcode, String Name)
    {
        //true thì tạo acc thành công, false thì ngược lại, mặc định là false vì nếu có sự cố nhập liệu thì vẫn trả về false cho an toàn
        boolean kq = false;
        PreparedStatement ps = null; //statement có truyền tham số, dùng để thực thi sql
        PreparedStatement ps2 = null;
        Statement st = null; //statement không truyền tham số, dùng để thực thi sql
        ResultSet rs = null; //dùng để đọc kết quả từ việc thực thì sql

        String sql = "Insert into User_Account(PhoneNumber, Passcode, Name) values(?, ?, ?)";
        String getUID = "Select ID_Account from User_Account where PhoneNumber = '"+PhoneNumber+"'";
        int ID_Account = -1;
        String add_qrcode = "Update User_Account set QR_Code = ? where ID_Account = ?";

        try
        {
            //kết nối tới database
            Connection connection = dbc.getConnection();
            //truyền sql vào máy thực thi
            ps = connection.prepareStatement(sql);
            ps.setString(1, PhoneNumber); //truyền tham số PhoneNumber, dựa theo số thứ tự của dấu '?' ở trên sql
            ps.setString(2, Passcode); //truyền tham số Passcode, dựa theo số thứ tự của dấu '?' ở trên sql
            ps.setString(3, Name); //truyền tham số Name, dựa theo số thứ tự của dấu '?' ở trên sql

            if (ps.executeUpdate() != 0) //ghi được kết quả
            {
                st = connection.createStatement();
                rs = st.executeQuery(getUID);
                rs.next();
                ID_Account = rs.getInt(1);

                String qr_code = ID_Account+"@"+PhoneNumber+"@"+Passcode+"@"+Name;
                ps2 = connection.prepareStatement(add_qrcode);
                ps2.setString(1, qr_code);
                ps2.setInt(2, ID_Account);

                if(ps2.executeUpdate() != 0)
                {
                    kq = true;
                }
                else
                {
                    kq = false;
                }
            }
            else //ghi không được kết quả
            {
                kq = false; //thì tạo acc không thành công
            }

            ps.close();
            ps2.close();
            st.close();
            rs.close();
            connection.close();
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return kq;
    }

    /**
     * Kiểm tra trùng số phone
     * @param PhoneNumber
     * @return
     */
    public boolean Check_PhoneNumber(String PhoneNumber)
    {
        //true thì tồn tại PhoneNumber, false thì không tồn tại PhoneNumber, mặc định là false vì nếu có sự cố nhập liệu thì vẫn trả về false cho an toàn
        boolean kq = false;
        PreparedStatement ps = null; //statement có truyền tham số, dùng để thực thi sql
        ResultSet rs = null; //dùng để đọc kết quả từ việc thực thì sql

        String sql = "Select PhoneNumber from User_Account where PhoneNumber = ?";

        try
        {
            Connection connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, PhoneNumber);
            rs = ps.executeQuery();
            if(rs.next())
            {
                kq = true;
            }
            else
            {
                kq = false;
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
     * Hàm này trả về QR_Code của 1 User với PhoneNumber nhập vào
     * @param PhoneNumber PhoneNumber của 1 User nào đó
     * @return QR_Code của User
     */
    public String get_QR_Code(String PhoneNumber)
    {
        String kq = "";

        PreparedStatement ps = null; //statement có truyền tham số, dùng để thực thi sql
        ResultSet rs = null; //dùng để đọc kết quả từ việc thực thì sql

        String sql = "Select QR_Code from User_Account where PhoneNumber = ?";

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
     * Kiểm tra để đăng nhập
     * @param PhoneNumber
     * @param Passcode
     * @return
     */
    public boolean Check_Account(String PhoneNumber, String Passcode)
    {
        //true thì tồn tại acc, false thì không tồn tại acc, mặc định là false vì nếu có sự cố nhập liệu thì vẫn trả về false cho an toàn
        boolean kq = false;
        PreparedStatement ps = null; //statement có truyền tham số, dùng để thực thi sql
        ResultSet rs = null; //dùng để đọc kết quả từ việc thực thì sql

        String sql = "Select PhoneNumber, Passcode from User_Account where PhoneNumber = ? and Passcode = ?";

        try
        {
            //kết nối tới database
            Connection connection = dbc.getConnection();
            //truyền sql vào máy thực thi
            ps = connection.prepareStatement(sql);
            ps.setString(1, PhoneNumber); //truyền tham số username, dựa theo số thứ tự của dấu '?' ở trên sql
            ps.setString(2, Passcode); //truyền tham số password, dựa theo số thứ tự của dấu '?' ở trên sql
            rs = ps.executeQuery(); //thực thi sql

            if (rs.next()) //đọc được kết quả
            {
                kq = true; //thì tài khoản có tồn tại
            }
            else //đọc không được kết quả
            {
                kq = false; //thì tài khoản không tồn tại
            }

            ps.close();
            rs.close();
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
     * hàm cập nhật thông tin cá nhân
     * @param PhoneNumber
     * @param Passcode
     * @param Name
     * @param ID_Account
     * @return
     */
    public boolean Update_User_Info(String PhoneNumber, String Passcode, String Name, String ID_Account)
    {
        boolean kq = false;
        PreparedStatement ps = null; //statement có truyền tham số, dùng để thực thi sql

        String sql = "Update User_Account set PhoneNumber=?, Passcode=?, Name=?, QR_Code=? where ID_Account=?";

        try
        {
            //kết nối tới database
            Connection connection = dbc.getConnection();
            //truyền sql vào máy thực thi
            ps = connection.prepareStatement(sql);
            ps.setString(1, PhoneNumber);
            ps.setString(2, Passcode);
            ps.setString(3, Name);
            ps.setString(4, ID_Account + "@" + PhoneNumber + "@" + Passcode + "@" + Name);
            ps.setString(5, ID_Account);

            if (ps.executeUpdate() != 0) //ghi được kết quả
            {
                kq = true; //thì đổi pass thành công
            }
            else //ghi không được kết quả
            {
                kq = false; //thì đổi pass không thành công
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
}
