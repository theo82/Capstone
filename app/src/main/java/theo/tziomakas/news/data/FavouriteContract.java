package theo.tziomakas.news.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavouriteContract {

    public static final String CONTENT_AUTHORITY = "theo.tziomakas.news";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAVOURITE = "favourite";

    public static final class FavouriteEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVOURITE)
                .build();

        public static final String TABLE_NAME = "favourite";

        public static final String COLUMN_NEWS_AUTHOR = "author";

        public static final String COLUMN_NEWS_TITLE = "title";

        public static final String COLUMN_MOVIE_DESCRIPTION = "description";

        public static final String COLUMN_MOVIE_URL = "url";

        public static final String COLUMN_MOVIE_URL_TO_IMAGE = "urlToImage";

        public static final String COLUMN_MOVIE_PUBLISHED_AT = "publishedAt";

        public static Uri buildNewsUriWithId(String id){
            return CONTENT_URI.buildUpon()
                    .appendPath(id)
                    .build();
        }

    }

}
