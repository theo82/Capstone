package theo.tziomakas.news.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Comment implements Parcelable{
    public String userId;
    public String date;
    public String commentAuthor;
    public String newsTitle;
    public String commentTitle;
    public String commentBody;

    public Comment(){

    }

    public Comment(String userId, String date,String commentAuthor, String newsTitle, String commentTitle, String commentBody) {
        this.userId = userId;
        this.date = date;
        this.commentAuthor = commentAuthor;
        this.newsTitle = newsTitle;
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
    }

    protected Comment(Parcel in) {
        userId = in.readString();
        date = in.readString();
        commentAuthor = in.readString();
        newsTitle = in.readString();
        commentTitle = in.readString();
        commentBody = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("date",date);
        result.put("commentAuthor", commentAuthor);
        result.put("newsTitle", newsTitle);
        result.put("commentTitle", commentTitle);
        result.put("commentBody", commentBody);

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userId);
        parcel.writeString(date);
        parcel.writeString(commentAuthor);
        parcel.writeString(newsTitle);
        parcel.writeString(commentTitle);
        parcel.writeString(commentBody);
    }
}
