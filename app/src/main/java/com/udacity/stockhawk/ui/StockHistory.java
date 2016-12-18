package com.udacity.stockhawk.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.NumberFormat;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.DropBoxManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.data.DbHelper;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.R.attr.format;
import static android.R.attr.listPreferredItemPaddingEnd;

public class StockHistory extends AppCompatActivity {

    ArrayList<String> historyDate = new ArrayList<>();
    ArrayList<Float> historyPrice = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_history);

        Bundle extras = getIntent().getExtras();

        if (extras!=null){

            DbHelper dbHelper = new DbHelper(this);
            SQLiteDatabase dbHistory = dbHelper.getReadableDatabase();

            String symbolReceived = extras.getString("symbolString");

            Log.d("SYMBOL_RECIEVED", symbolReceived);


            Cursor c = dbHistory.rawQuery("SELECT * FROM  quotes   where symbol='"+symbolReceived+"'" , null);


            c.moveToFirst();

            String[] history = c.getString(5).replaceAll("\\n",",").split(",");

            for(int i =0; i<history.length; i=i+2){

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                String str = sdf.format(Long.parseLong(history[i]));
                Log.d("STOCK_DATE",str);
                historyDate.add(str);

            }

            for (int i =1;i<history.length;i=i+2){

                Log.d("STOCK_PRICE",history[i]);

                historyPrice.add(Float.parseFloat(history[i]));
            }


            c.close();
            dbHistory.close();

        }

        LineChart lineChart = (LineChart) findViewById(R.id.line_chart);

        ArrayList<Entry> priceEntries = new ArrayList<>();
        for (int i =0; i<historyPrice.size();i++){

            priceEntries.add(new Entry(historyPrice.get(i),i));

        }

        LineDataSet lineDataSet = new LineDataSet(priceEntries,"STOCK_PRICE_ENTRIES");

        ArrayList<String> chartLabels = new ArrayList<>();

        for (int i =0; i<historyDate.size();i++){
            chartLabels.add(historyDate.get(i));
        }

        LineData lineData = new LineData(chartLabels,lineDataSet);
        lineChart.setData(lineData);

    }
}
