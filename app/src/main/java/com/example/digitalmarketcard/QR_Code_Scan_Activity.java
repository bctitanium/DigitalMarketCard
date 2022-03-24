package com.example.digitalmarketcard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class QR_Code_Scan_Activity extends AppCompatActivity
{
    private CodeScanner codeScanner;

    CodeScannerView code_scanner_view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scan);

        code_scanner_view = (CodeScannerView) findViewById(R.id.code_scanner_view);

        codeScanner = new CodeScanner(this, code_scanner_view);

        codeScanner.startPreview();

        codeScanner.setDecodeCallback(new DecodeCallback()
        {
            @Override
            public void onDecoded(@NonNull Result result)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //Toast.makeText(QR_Code_Scan_Activity.this, result.getText(), Toast.LENGTH_LONG).show();

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("result", result.getText());
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    protected void onPause()
    {
        codeScanner.releaseResources();
        super.onPause();
    }
}