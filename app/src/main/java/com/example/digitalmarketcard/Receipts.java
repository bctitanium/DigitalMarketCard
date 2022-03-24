package com.example.digitalmarketcard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digitalmarketcard.BLL.BLL_Items;

import java.util.ArrayList;
import java.util.List;

public class Receipts extends AppCompatActivity
{
    BLL_Items blli;

    List<dsItems> dsItemsList;
    private ListView list_items;
    int totalprice;
    String PhoneNumber;

    Button btnBackToAccount;
    Button btnScan;
    Button btnFinish;
    TextView lbTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);

        blli = new BLL_Items();

        dsItemsList = new ArrayList<>();

        list_items = (ListView) findViewById(R.id.list_items);

        btnBackToAccount = (Button) findViewById(R.id.btnBackToAccount);
        btnScan = (Button) findViewById(R.id.btnScan);
        btnFinish = (Button) findViewById(R.id.btnFinish);
        lbTotalPrice = (TextView) findViewById(R.id.lbTotalPrice);

        Intent intent = getIntent();
        PhoneNumber = intent.getStringExtra("PhoneNumber");

        btnBackToAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                {
                    Intent intent = new Intent(Receipts.this, QR_Code_Scan_Activity.class);
                    //startActivity(intent);
                    startActivityForResult(intent, 1);
                }
                else
                {
                    ActivityCompat.requestPermissions(Receipts.this, new String[]{Manifest.permission.CAMERA}, 0);
                }
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                totalprice = 0;

                for(int i=0; i<dsItemsList.size(); i++)
                {
                    totalprice += dsItemsList.get(i).getPrice() * dsItemsList.get(i).getQuantity();
                }

                lbTotalPrice.setText(totalprice+"");

                new AlertDialog.Builder(Receipts.this)
                        .setTitle("Thanh toán hả?")
                        .setMessage("Bạn đã sẵn sàng thanh toán chưa?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                ThanhToan();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                Toast.makeText(Receipts.this, "Chưa thanh toán", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                
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
                String result = data.getStringExtra("result");

                //btnFinish.setText(result);

                dsItemsList.add(new dsItems(
                        getImagePos(result),
                        blli.getItemNameFromID(result),
                        blli.getPriceFromID(result)));

                MyCustomListAdapter adapter = new MyCustomListAdapter(this, R.layout.activity_detailed_item, dsItemsList);

                list_items.setAdapter(adapter);
            }
        }
    }

    public int getImagePos(String qr_code)
    {
        int sttImage = -1;

        switch(qr_code)
        {
            case "IT010":
                sttImage = R.drawable.it010;
                break;
            case "IT011":
                sttImage = R.drawable.it011;
                break;
            case "IT012":
                sttImage = R.drawable.it012;
                break;
        }

        return sttImage;
    }
    
    public void ThanhToan()
    {
        Toast.makeText(Receipts.this, "Thanh toán nè\n" + PhoneNumber, Toast.LENGTH_SHORT).show();

        if(blli.Create_Receipts(totalprice, blli.get_ID_Account_fromPhoneNumber(PhoneNumber)))
        {
            int thisIdReceipts = blli.get_ID_Receipts_fromIDAccount(blli.get_ID_Account_fromPhoneNumber(PhoneNumber));

            for(int i=0; i<dsItemsList.size(); i++)
            {
                if(
                    blli.Create_Detailed_Receipts(
                        thisIdReceipts,
                        blli.get_ID_Item_fromItemsName(dsItemsList.get(i).getName()),
                        dsItemsList.get(i).getQuantity(),
                        blli.getPriceFromID(blli.get_ID_Item_fromItemsName(dsItemsList.get(i).getName())))
                  )
                {
                    //nó chạy đúng thì ko vào đây
                }
                else
                {
                    Toast.makeText(Receipts.this, "Lỗi receipts (if 1)", Toast.LENGTH_SHORT).show();
                }

                if(
                    blli.Update_Total_Items(blli.get_Total_Items_from_Items_Name(
                            dsItemsList.get(i).getName()),
                            dsItemsList.get(i).getQuantity(),
                            blli.get_ID_Item_fromItemsName(dsItemsList.get(i).getName()))
                  )
                {
                    //nó chạy đúng thì ko vào đây
                }
                else
                {
                    Toast.makeText(Receipts.this, "Lỗi receipts (if 2)", Toast.LENGTH_SHORT).show();
                }
            }

            Toast.makeText(Receipts.this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(Receipts.this, "Lỗi receipts", Toast.LENGTH_SHORT).show();
        }
    }
}