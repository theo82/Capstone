package theo.tziomakas.news.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import theo.tziomakas.news.R;
import theo.tziomakas.news.adapters.NewsAdapter;
import theo.tziomakas.news.adapters.SimpleDividerItemDecoration;
import theo.tziomakas.news.loaders.USATodayLoader;
import theo.tziomakas.news.model.News;
import theo.tziomakas.news.widget.UpdateNewsWidgetService;


/**
 * A simple {@link Fragment} subclass.
 */
public class USATodayFragment extends Fragment implements LoaderManager.LoaderCallbacks<Object> {

    private static final String LOG_TAG = USATodayFragment.class.getName();
    private static final int NEWS_LOADER_ID = 0;
    private String newsUrl;

    private RecyclerView mRecyclerView;
    private NewsAdapter adapter;
    private TextView errorTextView;

    private static final String ARRAY_LIST = "list_view";
    private ArrayList<News> newsArrayList;
    private static final String LAYOUT_STATE = "USATodayFragment.recycler.layout";

    String newsTitlesToJson;
    public USATodayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.common_fragment_layout, container, false);


        if(savedInstanceState == null) {
            newsArrayList = new ArrayList<>();

            mRecyclerView = v.findViewById(R.id.news_recycler_view);
            errorTextView = v.findViewById(R.id.errorTextView);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
            adapter = new NewsAdapter(getActivity(), newsArrayList);
            mRecyclerView.setAdapter(adapter);

            getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);

            UpdateNewsWidgetService.startBakingService(getContext(), (ArrayList<News>) newsArrayList);



        }else{
            newsArrayList = savedInstanceState.getParcelableArrayList(ARRAY_LIST);
            errorTextView = v.findViewById(R.id.errorTextView);
            mRecyclerView = v.findViewById(R.id.news_recycler_view);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
            adapter = new NewsAdapter(getActivity(), newsArrayList);
            mRecyclerView.setAdapter(adapter);

            UpdateNewsWidgetService.startBakingService(getContext(), (ArrayList<News>) newsArrayList);


        }


        return v;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        if(id == NEWS_LOADER_ID){
            newsUrl = "https://newsapi.org/v2/top-headlines?sources=usa-today&apiKey=98b995b151264acdb35e751ff6d22a3c";

        }

        return new USATodayLoader(getActivity(),newsUrl);
    }



    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        int id = loader.getId();

        if(id == NEWS_LOADER_ID){
            newsArrayList = (ArrayList<News>)data;

            if(newsArrayList !=null && !newsArrayList.isEmpty()){
                adapter.clear();
                adapter.setNewsData(newsArrayList);

                newsTitlesToJson = new Gson().toJson(newsArrayList);

                PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .edit().putString("news",newsTitlesToJson)
                        .apply();

                Log.v(LOG_TAG, newsTitlesToJson);


            }else{
                showError();
            }
        }
    }
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(ARRAY_LIST,newsArrayList);
        //outState.putParcelable(LAYOUT_STATE, mRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null)
        {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(LAYOUT_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);

        }
    }

    public void showError(){
        mRecyclerView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
    }
}
