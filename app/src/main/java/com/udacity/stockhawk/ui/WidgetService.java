package com.udacity.stockhawk.ui;
import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by nafeezq on 12/9/2016.
 */

public class WidgetService extends RemoteViewsService {

     @Override

        public RemoteViewsFactory onGetViewFactory(Intent intent) {

            return (new WidgetRemoteViewsFactory(this.getApplicationContext(), intent));
        }

    }

