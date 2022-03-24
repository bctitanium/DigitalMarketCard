package com.example.digitalmarketcard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyCustomListAdapter extends ArrayAdapter<dsItems>
{
    Context mCtx;
    int resource;
    List<dsItems> dsItemsList;

    public MyCustomListAdapter(Context mCtx, int resource, List<dsItems> dsItemsList)
    {
        super(mCtx, resource, dsItemsList);
        this.mCtx = mCtx;
        this.resource = resource;
        this.dsItemsList = dsItemsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.activity_detailed_item, null);

        ImageView imgView = view.findViewById(R.id.imgItem);
        TextView lbName = view.findViewById(R.id.lbName);
        TextView lbPrice = view.findViewById(R.id.lbPrice);

        EditText txtSoLuong = view.findViewById(R.id.txtSoLuong);

        dsItems dsItems = dsItemsList.get(position);

        imgView.setImageDrawable(mCtx.getResources().getDrawable(dsItems.getImage()));
        lbName.setText(dsItems.getName());
        lbPrice.setText("0");

        view.findViewById(R.id.btnPlus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sl = Integer.parseInt(txtSoLuong.getText().toString());
                sl++;
                txtSoLuong.setText(sl+"");
                int price = dsItems.getPrice();
                lbPrice.setText(price*sl+"");
                dsItems.setQuantity(sl);
            }
        });

        view.findViewById(R.id.btnMinus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sl = Integer.parseInt(txtSoLuong.getText().toString());
                if(sl>0)
                {
                    sl--;
                    txtSoLuong.setText(sl+"");
                    int price = dsItems.getPrice();
                    lbPrice.setText(price*sl+"");
                    dsItems.setQuantity(sl);
                }
            }
        });

        view.findViewById(R.id.btnX).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItems(position);
            }
        });

        return view;
    }

    private void removeItems(final int i)
    {
        new AlertDialog.Builder(mCtx)
                .setTitle("Xóa hả?")
                .setMessage("Bạn muốn xóa món này?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dsItemsList.remove(i);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
