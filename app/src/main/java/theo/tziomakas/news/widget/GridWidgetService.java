package theo.tziomakas.news.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import theo.tziomakas.news.R;
import theo.tziomakas.news.model.News;

public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new NewsListRemoteVieFactory(this.getApplicationContext());
    }
}

class NewsListRemoteVieFactory implements RemoteViewsService.RemoteViewsFactory{

        public  static ArrayList<News> newsArrayList = new ArrayList<>();
        Context mContext;

        public NewsListRemoteVieFactory(Context applicationContext) {

            this.mContext = applicationContext;

        }


        private void readNews(){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
            Gson gson = new Gson();

            String json = prefs.getString("news", "");
            Type type = new TypeToken<ArrayList<News>>(){}.getType();
            newsArrayList = gson.fromJson(json, type);
        }

        @Override
        public void onCreate() {

            readNews();
        }

        @Override
        public void onDataSetChanged() {
            readNews();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if(newsArrayList == null){
                return 0;
            }
            return newsArrayList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_grid_view_item);

            views.setTextViewText(R.id.widget_grid_view_item, "\u2022 " + newsArrayList.get(position).getTitle()
                    + "\n" + String.valueOf(newsArrayList.get(position).getDescription()));



            Bundle selectedNewsBundle = new Bundle();
            selectedNewsBundle.putParcelableArrayList("news",newsArrayList);

            Intent fillInIntent = new Intent();
            views.setOnClickFillInIntent(R.id.widget_grid_view_item, fillInIntent);

            return views;
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
}

