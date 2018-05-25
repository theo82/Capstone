package theo.tziomakas.news.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Comment {
    public String userId;
    public String commentAuthor;
    public String newsTitle;
    public String commentTitle;
    public String commentBody;

    public Comment(){

    }

    public Comment(String userId, String commentAuthor, String newsTitle, String commentTitle, String commentBody) {
        this.userId = userId;
        this.commentAuthor = commentAuthor;
        this.newsTitle = newsTitle;
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("commentAuthor", commentAuthor);
        result.put("newsTitle", newsTitle);
        result.put("commentTitle", commentTitle);
        result.put("commentBody", commentBody);

        return result;
    }
}
