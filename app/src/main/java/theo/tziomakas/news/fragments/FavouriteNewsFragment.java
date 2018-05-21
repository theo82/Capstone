package theo.tziomakas.news.fragments;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import theo.tziomakas.news.R;
import theo.tziomakas.news.adapters.NewsAdapter;
import theo.tziomakas.news.adapters.SimpleDividerItemDecoration;
import theo.tziomakas.news.data.FavouriteContract;
import theo.tziomakas.news.model.News;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteNewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Object>{

    private ArrayList<News> newsArrayList;
    private RecyclerView mRecyclerView;
    private NewsAdapter adapter;
    private static final int FAV_NEWS_LOADER = 1;
    private TextView errorTextView;


    private static final String[] FAVOURITE_NEWS_PROJECTION = {
            FavouriteContract.FavouriteEntry.COLUMN_NEWS_AUTHOR,
            FavouriteContract.FavouriteEntry.COLUMN_NEWS_TITLE,
            FavouriteContract.FavouriteEntry.COLUMN_NEWS_DESCRIPTION,
            FavouriteContract.FavouriteEntry.COLUMN_NEWS_URL,
            FavouriteContract.FavouriteEntry.COLUMN_NEWS_URL_TO_IMAGE,
            FavouriteContract.FavouriteEntry.COLUMN_NEWS_PUBLISHED_AT

    };

    public FavouriteNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.common_fragment_layout, container, false);

        newsArrayList = new ArrayList<>();

        errorTextView = v.findViewById(R.id.errorTextView);

        mRecyclerView = v.findViewById(R.id.news_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        //adapter = new NewsAdapter(getActivity(),newsArrayList,this);


        //adapter = new NewsAdapter(getActivity(),newsArrayList,this);
        //mRecyclerView.setAdapter(adapter);

        getLoaderManager().initLoader(FAV_NEWS_LOADER,null,this);



        return v;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        if (id == FAV_NEWS_LOADER) {

            Uri newsUri = FavouriteContract.FavouriteEntry.CONTENT_URI;

            return new CursorLoader(
                    getActivity(),
                    newsUri,
                    FAVOURITE_NEWS_PROJECTION,
                    null,
                    null,
                    null
            );
        }

        return null;

    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        int id = loader.getId();

        if(id == FAV_NEWS_LOADER) {

            Cursor dataCursor = (Cursor) data;
            // Cast Object to display a list of movies from the net.
            ArrayList<News> n = new ArrayList<>();

            if (dataCursor != null && dataCursor.getCount() > 0) {

                while (dataCursor.moveToNext()) {
                    String author = dataCursor.getString(0);
                    String title = dataCursor.getString(1);
                    String description = dataCursor.getString(2);
                    String url = dataCursor.getString(3);
                    String urlToImage = dataCursor.getString(4);
                    String publishedDate = dataCursor.getString(5);

                    News news = new News(author,title,description,url,urlToImage,publishedDate);
                    n.add(news);
                }
                //adapter.setNewsData(n);
            }else{
                //recyclerView.setVisibility(View.INVISIBLE);
                //getLoaderManager().destroyLoader(MOVIES_LOADER_ID);
                //adapter.clear();

                Toast.makeText(getActivity(),"You haven't added a news article as favourite",Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }

    public void showError(){
        mRecyclerView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
    }
}
