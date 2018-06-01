package theo.tziomakas.news.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import theo.tziomakas.news.utils.NetworkUtils;
import theo.tziomakas.news.model.News;

public class GenericLoader extends AsyncTaskLoader<List<News>> {

    private static final String LOG_TAG = GenericLoader.class.getName();

    String url;
    public List<News> result;

    public GenericLoader(Context context, String url) {
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

        result = NetworkUtils.fetchNewsData(url);
        return result;
    }
}
