package com.udacity.stockhawk.ui;

import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.data.DbHelper;

import java.util.ArrayList;
/**
 * Created by nafeezq on 12/9/2016.
 */

public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private int mAppWidgetId;
    ArrayList<String> symbolArray = new ArrayList();
    ArrayList<String> priceArray = new ArrayList();
    ArrayList<String> priceChangeArray = new ArrayList();

    Cursor cursor;

    public WidgetRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;

        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    @Override
    public void onCreate() {

                DbHelper helper = new DbHelper(mContext);
                SQLiteDatabase db = helper.getReadableDatabase();

                cursor = db.query(Contract.Quote.TABLE_NAME,null,null,null,null,null,null);
                cursor.moveToFirst();

                do{

                    Log.d("WIDGET",cursor.getString(1));
                    Log.d("WIDGET",cursor.getString(2));
                    Log.d("WIDGET",cursor.getString(3));

                    symbolArray.add(cursor.getString(1));
                    priceArray.add(cursor.getString(2));
                    priceChangeArray.add(cursor.getString(3));

                }while (cursor.moveToNext());



                cursor.close();
                db.close();
    }


    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Log.d("WIDGET_ROW", symbolArray.get(position));

            row.setTextViewText(R.id.symbol,symbolArray.get(position));
            row.setTextViewText(R.id.price,priceArray.get(position));
            row.setTextViewText(R.id.change,priceChangeArray.get(position));

            return row;

    }


    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return (priceArray.size());

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {

    }

}





