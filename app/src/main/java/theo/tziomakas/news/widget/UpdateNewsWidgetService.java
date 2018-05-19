package theo.tziomakas.news.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import theo.tziomakas.news.model.News;

public class UpdateNewsWidgetService extends IntentService {

    ArrayList<News> newsArrayList = new ArrayList<>();


    public UpdateNewsWidgetService() {
        super("UpdateNewsWidgetService");
    }

    public static void startBakingService(Context context, ArrayList<News> news) {
        Intent intent = new Intent(context, UpdateNewsWidgetService.class);
        intent.putExtra("news_list",news);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        if (intent != null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Gson gson = new Gson();

            String json = prefs.getString("news", "");
            Type type = new TypeToken<ArrayList<News>>(){}.getType();
            newsArrayList = gson.fromJson(json, type);
            handleActionUpdateNewsWidget(newsArrayList);

        }
    }

    private void handleActionUpdateNewsWidget(ArrayList<News> newsArrayList){
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.putExtra("news_list", newsArrayList);
        sendBroadcast(intent);
    }
}
