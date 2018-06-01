package service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import theo.tziomakas.news.model.News;
import theo.tziomakas.news.utils.NetworkUtils;

public class NewsService extends IntentService{

    private String newsUrl;
    private String date;
    private SharedPreferences prefs;
    List<News> newsList;
    String newDate;
    String newFormattedDate;

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

        Log.v("NewsService","News size: " + newsList.size());

        for(int i = 0; i<newsList.size(); i++){
            newDate = newsList.get(0).getPublishedDate();
        }

        newFormattedDate = newDate.substring(0,10);

        Log.v("NewsService","Formatted date: " + newFormattedDate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = sdf.parse(date);
            Date date2 = sdf.parse(newFormattedDate);

            if(date2.compareTo(date1)>0){
                // Receive notification.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
