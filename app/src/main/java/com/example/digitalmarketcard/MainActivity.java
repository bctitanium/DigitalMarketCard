package com.example.digitalmarketcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.digitalmarketcard.BLL.BLL_Account;

public class MainActivity extends AppCompatActivity
{
    BLL_Account blla;

    EditText txtPhoneNumber;
    EditText txtPasscode;
    Button btnLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blla = new BLL_Account();

        txtPhoneNumber = (EditText) findViewById(R.id.txtPhoneNumber);
        txtPasscode = (EditText) findViewById(R.id.txtPasscode);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String PhoneNumber = txtPhoneNumber.getText().toString();
                String Passcode = txtPasscode.getText().toString();

                if(PhoneNumber.isEmpty() || Passcode.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Bạn chưa nhập thông tin tài khoản", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(blla.Check_Account(PhoneNumber, Passcode))
                {
                    txtPhoneNumber.setText("");
                    txtPasscode.setText("");

                    Intent intent = new Intent(MainActivity.this, Account_UI.class);
                    intent.putExtra("qr_code", blla.get_QR_Code(PhoneNumber));
                    intent.putExtra("PhoneNumber", PhoneNumber);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Bạn nhập sai thông tin tài khoản", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}