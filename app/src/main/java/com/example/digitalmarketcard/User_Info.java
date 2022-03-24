package com.example.digitalmarketcard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.digitalmarketcard.BLL.BLL_Account;
import com.example.digitalmarketcard.BLL.BLL_Items;

import java.util.Scanner;

public class User_Info extends AppCompatActivity
{
    BLL_Account blla;
    BLL_Items blli;

    EditText txtPhoneNumber;
    EditText txtPasscode;
    EditText txtPasscodeConfirm;
    EditText txtName;
    Button btnSave;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        blla = new BLL_Account();
        blli = new BLL_Items();

        txtPhoneNumber = (EditText) findViewById(R.id.txtPhoneNumber);
        txtPasscode = (EditText) findViewById(R.id.txtPasscode);
        txtPasscodeConfirm = (EditText) findViewById(R.id.txtPasscodeConfirm);
        txtName = (EditText) findViewById(R.id.txtName);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnBack = (Button) findViewById(R.id.btnBack);

        Intent intent = getIntent();
        Scanner scan = new Scanner(intent.getStringExtra("qr_code"));
        scan.useDelimiter("@");
        String ID_Account = scan.next();
        String PhoneNumber = scan.next();
        String Passcode = scan.next();
        String Name = scan.next();

        txtPhoneNumber.setText(PhoneNumber);
        txtPasscode.setText(Passcode);
        txtName.setText(Name);

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!txtPasscode.getText().toString().equals(txtPasscodeConfirm.getText().toString()))
                {
                    Toast.makeText(User_Info.this, "2 Passcode không giống nhau", Toast.LENGTH_LONG).show();
                    return;
                }

                String qr_code = ID_Account + "@" + txtPhoneNumber.getText().toString() + "@" +txtPasscode.getText().toString() + "@" + txtName.getText().toString();

                if(!blla.Update_User_Info(txtPhoneNumber.getText().toString(), txtPasscode.getText().toString(), txtName.getText().toString(), ID_Account))
                {
                    Toast.makeText(User_Info.this, "Cập nhật thông tin không thành công", Toast.LENGTH_LONG).show();

                    return;
                }

                new AlertDialog.Builder(User_Info.this)
                .setTitle("Cập nhật thông tin thành công")
                .setMessage("Bạn vừa cập nhật lại thông tin của mình")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("ChangedInfo", qr_code);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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