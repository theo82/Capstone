package theo.tziomakas.news.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import theo.tziomakas.news.R;
import theo.tziomakas.news.model.Comment;

public class DisplayCommentsAdapter extends RecyclerView.Adapter<DisplayCommentsAdapter.CommentsViewHolder>{


    private static final String LOG_TAG = NewsAdapter.class.getName();
    private ValueEventListener context;
    private ArrayList<Comment> commentArrayList;

    public DisplayCommentsAdapter(ValueEventListener context, ArrayList<Comment> commentArrayList){
        this.context = context;
        this.commentArrayList = commentArrayList;
    }

    @Override
    public DisplayCommentsAdapter.CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.single_comment_row;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttactToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttactToParentImmediately);

        CommentsViewHolder commentsViewHolder = new CommentsViewHolder(view);

        return commentsViewHolder;
    }

    @Override
    public void onBindViewHolder(DisplayCommentsAdapter.CommentsViewHolder holder, int position) {

         Comment comment = commentArrayList.get(position);

         holder.mTitle.setText(comment.commentTitle);
         holder.mBody.setText(comment.commentBody);
         holder.mAuthor.setText(comment.commentAuthor);
         holder.mDate.setText(comment.date);


    }

    @Override
    public int getItemCount() {
        if(commentArrayList == null){
            return  0;
        }else{
            return commentArrayList.size();
        }
    }

    public void setCommentsData(ArrayList<Comment> newsList){
        this.commentArrayList = commentArrayList;
        notifyDataSetChanged();
    }

    public void clear() {
        commentArrayList.clear();
        notifyDataSetChanged();
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mBody;
        TextView mAuthor;
        TextView mDate;

        public CommentsViewHolder(View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.display_comment_title);
            mBody = itemView.findViewById(R.id.display_comment_body);
            mAuthor = itemView.findViewById(R.id.display_comment_author);
            mDate = itemView.findViewById(R.id.display_comment_date);


        }
    }
}
