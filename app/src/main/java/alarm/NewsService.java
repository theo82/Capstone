package alarm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import theo.tziomakas.news.model.News;
import theo.tziomakas.news.utils.NetworkUtils;

public class NewsService extends IntentService {

    private String newsUrl;
    private String date;
    private SharedPreferences prefs;
    List<News> newsList;


    public NewsService() {
        super("NewsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        newsList = new ArrayList<>();
        //newsUrl = bundle.getString(newsUrl);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        newsUrl = prefs.getString("url", "");
        date = prefs.getString("request_date","");

        Log.v("NewsService","Request date: " +date);

        newsList = NetworkUtils.fetchNewsData(newsUrl);

    }
}