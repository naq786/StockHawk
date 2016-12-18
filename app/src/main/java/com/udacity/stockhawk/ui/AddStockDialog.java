package com.udacity.stockhawk.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.DbHelper;
import com.udacity.stockhawk.data.PrefUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;


public class AddStockDialog extends DialogFragment {

    String stockString;
    Stock validStock;
    String stockName;




    @BindView(R.id.dialog_stock)
    EditText stock;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View custom = inflater.inflate(R.layout.add_stock_dialog, null);

        ButterKnife.bind(this, custom);

        stock.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                addStock();
                return true;
            }
        });
        builder.setView(custom);

        builder.setMessage(getString(R.string.dialog_title));
        builder.setPositiveButton(getString(R.string.dialog_add),
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    public void onClick(DialogInterface dialog, int id) {
                        addStock();
                    }
                });
        builder.setNegativeButton(getString(R.string.dialog_cancel), null);

        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void addStock() {
        Activity parent = getActivity();
        if (parent instanceof MainActivity) {
             stockString = stock.getText().toString();

            try {
                validStock = YahooFinance.get(stockString);
                stockName = validStock.getName();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(stockName==null){

                Toast.makeText(getContext(), "INVALID SYMBOL", Toast.LENGTH_SHORT).show();


            }else {

                Log.d("STOCK_VALID",stockName);
                ((MainActivity) parent).addStock(stockString);

            }


        }
        dismissAllowingStateLoss();
    }

}
