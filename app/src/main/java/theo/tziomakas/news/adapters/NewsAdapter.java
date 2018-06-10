package theo.tziomakas.news.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;
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
        Picasso.with(context)
                .load(newsImage)
                .placeholder(R.drawable.ic_broken_image)
                .fit()
                .into(holder.mImageView);


        holder.mTitle.setText(newsTitle);
        holder.mAuthor.setText(newsAuthor);




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

        @BindView(R.id.news_image)
        ImageView mImageView;

        @BindView(R.id.news_title)
        TextView mTitle;

        @BindView(R.id.news_author)
        TextView mAuthor;

        public NewsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            // binding view
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            lOnClickListener.onListItemClick(clickedPosition);
        }
    }


}
