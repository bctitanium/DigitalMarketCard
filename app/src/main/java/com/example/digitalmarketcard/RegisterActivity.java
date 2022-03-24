package com.example.digitalmarketcard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.digitalmarketcard.BLL.BLL_Account;

public class RegisterActivity extends AppCompatActivity
{
    BLL_Account blla;

    EditText txtPhoneNumber;
    EditText txtPasscode;
    EditText txtName;
    Button btnRegister;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        blla = new BLL_Account();

        txtPhoneNumber = (EditText) findViewById(R.id.txtPhoneNumber);
        txtPasscode = (EditText) findViewById(R.id.txtPasscode);
        txtName = (EditText) findViewById(R.id.txtName);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnBack = (Button) findViewById(R.id.btnBack);

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String PhoneNumber = txtPhoneNumber.getText().toString();
                String Passcode = txtPasscode.getText().toString();
                String Name = txtName.getText().toString();

                if(PhoneNumber.isEmpty() || Passcode.isEmpty() || Name.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Bạn chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(blla.Check_PhoneNumber(PhoneNumber))
                {
                    Toast.makeText(RegisterActivity.this, "Số điện thoại đã được sử dụng!\nVui lòng nhập số điện thoại khác!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(blla.Create_Account(PhoneNumber, Passcode, Name))
                {
                    Toast.makeText(RegisterActivity.this, "Tạo tài khoản thành công!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }
}