package theo.tziomakas.news.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import theo.tziomakas.news.utils.NetworkUtils;
import theo.tziomakas.news.model.News;

public class USATodayLoader extends AsyncTaskLoader<List<News>> {

    private static final String LOG_TAG = USATodayLoader.class.getName();

    String url;

    public USATodayLoader(Context context, String url) {
        super(context);
        this.url = url;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {

        if(url == null){
            return null;
        }

        List<News> result = NetworkUtils.fetchNewsData(url);
        return result;
    }
}
