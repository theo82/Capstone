package theo.tziomakas.news.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Comment {
    public String userId;
    public String newsTitle;
    public String commentTitle;
    public String commentBody;

    public Comment(){

    }

    public Comment(String userId, String newsTitle, String commentTitle, String commentBody) {
        this.userId = userId;
        this.newsTitle = newsTitle;
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("newsTitle", newsTitle);
        result.put("commentTitle", commentTitle);
        result.put("commentBody", commentBody);

        return result;
    }
}
