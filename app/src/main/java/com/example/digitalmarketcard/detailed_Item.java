package com.example.digitalmarketcard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class detailed_Item extends AppCompatActivity
{
    EditText txtSoLuong;
    Button btnPlus;
    Button btnMinus;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_item);

        txtSoLuong = (EditText) findViewById(R.id.txtSoLuong);
        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnMinus = (Button) findViewById(R.id.btnMinus);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sl = Integer.parseInt(txtSoLuong.getText().toString());
                txtSoLuong.setText(sl++);
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sl = Integer.parseInt(txtSoLuong.getText().toString());
                txtSoLuong.setText(sl--);
            }
        });
    }
}