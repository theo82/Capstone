package theo.tziomakas.news.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import theo.tziomakas.news.DetailActivity;
import theo.tziomakas.news.MainActivity;
import theo.tziomakas.news.R;
import theo.tziomakas.news.model.News;

public class NewsAppWidgetProvider extends AppWidgetProvider {

    static ArrayList<News> newsArrayList = new ArrayList<>();
    private static String json;
    private static Type type;
    private static SharedPreferences prefs;
    private static Gson gson;



    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, NewsAppWidgetProvider.class));

        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
        json = prefs.getString("news", "");
        type = new TypeToken<ArrayList<News>>(){}.getType();
        newsArrayList = gson.fromJson(json, type);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        // Construct the RemoteViews object
        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);

        Intent templateIntent = new Intent(context, DetailActivity.class);
        templateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent templatePendingIntent = PendingIntent.getActivity(
                context, 0, templateIntent, 0);

        views.setPendingIntentTemplate(R.id.widget_grid_view,
                templatePendingIntent);
        // Instruct the widget manager to update the widget

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);


        }
    }

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, NewsAppWidgetProvider.class));

        final String action = intent.getAction();

        if (action.equals("android.appwidget.action.APPWIDGET_UPDATE2")) {
            NewsAppWidgetProvider.updateRecipeWidgets(context,appWidgetManager,appWidgetIds);
            super.onReceive(context,intent);
        }
    }
}
