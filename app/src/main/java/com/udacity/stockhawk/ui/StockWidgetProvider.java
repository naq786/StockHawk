package com.udacity.stockhawk.ui;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import com.udacity.stockhawk.R;

/**
 * Created by nafeezq on 12/9/2016.
 */

public class StockWidgetProvider extends AppWidgetProvider{


        @Override
        public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

            for (int i = 0; i < appWidgetIds.length; i++) {

                Intent serviceIntent = new Intent(context, WidgetService.class);
                serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
                serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
                remoteViews.setRemoteAdapter(appWidgetIds[i],R.id.widget_list, serviceIntent);

                appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
            }

            super.onUpdate(context, appWidgetManager, appWidgetIds);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);

        }
}


