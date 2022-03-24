package com.example.digitalmarketcard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digitalmarketcard.BLL.BLL_Account;

import java.util.Scanner;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Account_UI extends AppCompatActivity
{
    BLL_Account blla;

    ImageView imgQRC;
    TextView lbName;
    Button btnLogout;
    Button btnBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_ui);

        blla = new BLL_Account();

        imgQRC = (ImageView) findViewById(R.id.imgQRC);
        lbName = (TextView) findViewById(R.id.lbName);
        btnBuy = (Button) findViewById(R.id.btnBuy);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        Intent intent = getIntent();
        String qr_code_value = intent.getStringExtra("qr_code");
        String PhoneNumber = intent.getStringExtra("PhoneNumber");
        Scanner scan = new Scanner(qr_code_value);
        scan.useDelimiter("@");
        scan.next(); scan.next(); scan.next();

        lbName.append(scan.next());
        imgQRC.setImageBitmap(qr_code_generator(qr_code_value));

        btnBuy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Account_UI.this, Receipts.class);
                intent.putExtra("PhoneNumber", PhoneNumber);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                imgQRC.setImageBitmap(null);
                finish();
            }
        });

        imgQRC.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Account_UI.this, User_Info.class);
                intent.putExtra("qr_code", blla.get_QR_Code(PhoneNumber));
                //startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                String result = data.getStringExtra("ChangedInfo");

                imgQRC.setImageBitmap(qr_code_generator(result));
            }
        }
    }

    private int get_Dimension()
    {
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int dimension = width < height ? width : height;

        return dimension * 3 / 4;
    }

    private Bitmap qr_code_generator(String data)
    {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            try
            {
                QRGEncoder qr_gen = new QRGEncoder(data, null, QRGContents.Type.TEXT, get_Dimension());
                Bitmap bitmap = qr_gen.getBitmap();

                return bitmap;

                /*boolean save = new QRGSaver().save(Environment.getExternalStorageDirectory().getPath() + "/QRCode/", data, bitmap, QRGContents.ImageType.IMAGE_JPEG);

                if(save)
                {
                    Toast.makeText(RegisterActivity.this, "Bạn có 1 mã QR Code!!!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Lỗi khi tạo mã QR Code ở IF", Toast.LENGTH_LONG).show();
                    return;
                }*/
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(Account_UI.this, "Lỗi khi tạo mã QR Code ở CATCH", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            ActivityCompat.requestPermissions(Account_UI.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        return null;
    }
}