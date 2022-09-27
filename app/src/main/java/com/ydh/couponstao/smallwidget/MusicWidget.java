package com.ydh.couponstao.smallwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.ydh.couponstao.R;

/**
 * Implementation of App Widget functionality.
 */
public class MusicWidget extends AppWidgetProvider {
    public static String NEXT_ACTION = "click_next";
    public static String PREVIOUS_ACTION = "previous_next";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence noData = context.getString(R.string.no_data);
        CharSequence no = context.getString(R.string.no);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.music_widget);
        // views.setTextViewText(R.id.tv_title, noData);
        //  views.setTextViewText(R.id.tv_artist_name, no);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        final String action = intent.getAction();
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.music_widget);
        if (PREVIOUS_ACTION.equals(action)) {

        } else if (NEXT_ACTION.equals(action)) {

        } else {
            String trackName = intent.getStringExtra("trackName");
            String artistName = intent.getStringExtra("artistName");
            String albumArtistName = intent.getStringExtra("albumArtistName");
            String albumName = intent.getStringExtra("albumName");
            views.setTextViewText(R.id.tv_title, trackName);
            views.setTextViewText(R.id.tv_artist_name, artistName);

            Intent intentNext = new Intent();
            intentNext.setClass(context, MusicWidget.class);
            intentNext.setAction(NEXT_ACTION);
            PendingIntent pendingIntentNext = PendingIntent.getBroadcast(context, R.id.tv_next, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.tv_next, pendingIntentNext);
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(new ComponentName(context, MusicWidget.class), views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

