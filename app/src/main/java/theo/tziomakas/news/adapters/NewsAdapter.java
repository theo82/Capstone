package theo.tziomakas.news.adapters;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import theo.tziomakas.news.R;
import theo.tziomakas.news.model.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    private static final String LOG_TAG = NewsAdapter.class.getName();
    private Context context;
    private ArrayList<News> newsList;



    final private ListItemClickListener lOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
    public NewsAdapter(Context context, ArrayList<News> news,  ListItemClickListener mClickHandler ){
        this.context = context;
        this.newsList = news;
        this.lOnClickListener = mClickHandler;
    }

    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.news_single_row;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttactToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttactToParentImmediately);


        NewsViewHolder newsViewHolder = new NewsViewHolder(view);

        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(NewsAdapter.NewsViewHolder holder, int position) {

        News news = newsList.get(position);

        String newsImage = news.getUrlToImage();
        String newsTitle = news.getTitle();
        String newsAuthor = news.getAuthor();
        //String newsDate = news.getPublishedDate();

        //String date1 = newsDate.substring(0,10);
       // String date2 = newsDate.substring(11,19);
        Picasso.with(context)
                .load(newsImage)
                .placeholder(R.drawable.ic_broken_image)
                .fit()
                .into(holder.mImageView);


        holder.mTitle.setText(newsTitle);
        holder.mAuthor.setText(newsAuthor);
        //holder.mDate.setText(date2);

        //Log.v(LOG_TAG,newsTitle);



    }

    @Override
    public int getItemCount() {
        if( newsList == null){
            return  0;
        }else {
            return newsList.size();
        }
    }

    public void setNewsData(ArrayList<News> newsList){
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    public void clear() {
        newsList.clear();
        notifyDataSetChanged();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageView;
        TextView mTitle;
        TextView mAuthor;
        //TextView mDate;

        public NewsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mImageView = itemView.findViewById(R.id.news_image);
            mTitle = itemView.findViewById(R.id.news_title);
            mAuthor = itemView.findViewById(R.id.news_author);
            //mDate = itemView.findViewById(R.id.news_date);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            lOnClickListener.onListItemClick(clickedPosition);
        }
    }


}
