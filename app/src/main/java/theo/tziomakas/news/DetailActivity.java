package theo.tziomakas.news;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import theo.tziomakas.news.data.FavouriteContract;

public class DetailActivity extends AppCompatActivity {

    ImageView mImageView;
    TextView mTitle;
    TextView mDate;
    TextView mDescription;
    TextView mAuthor;
    ToggleButton mFavBtn;
    private FloatingActionButton mShareBtn;
    private String newsTitle;
    private String newsImage;
    private String newsDate;
    private String newsDescription;
    private static String NEWS_SHARE_HASHTAG = "#PopularMoviesApp";
    private String date1;
    private String date2;
    private String newsUrl;
    private String newsAuthor;
    private Cursor favoriteCursor;

    private static Bundle bundle = new Bundle();

    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent i = getIntent();


        mAuthor = (TextView) findViewById(R.id.detail_author);

        mImageView = (ImageView) findViewById(R.id.detail_image_view);
        mTitle = (TextView) findViewById(R.id.detail_title);
        mDate = (TextView) findViewById(R.id.detail_publish_date);
        mDescription = (TextView) findViewById(R.id.detail_description);
        mShareBtn = (FloatingActionButton) findViewById(R.id.share_floating_btn);
        mFavBtn = (ToggleButton) findViewById(R.id.fav_news_btn);

        mFavBtn.setTextOn(null);
        mFavBtn.setText(null);
        mFavBtn.setTextOff(null);

        newsAuthor = i.getStringExtra("author");
        newsImage = i.getStringExtra("image");
        newsTitle = i.getStringExtra("title");
        newsDate = i.getStringExtra("date");
        newsDescription = i.getStringExtra("description");
        newsUrl = i.getStringExtra("url");
        date1 = newsDate.substring(0, 10);
        date2 = newsDate.substring(11, 19);

        Picasso.with(this).load(newsImage)
                .placeholder(R.drawable.ic_broken_image)
                .into(mImageView);

        mTitle.setText(newsTitle);
        mAuthor.setText("Author: " + newsAuthor);
        mDescription.setText(newsDescription);
        mDate.setText(date2 + ", " + date1);

        mShareBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent shareIntent = createShareNewsIntent();
                startActivity(shareIntent);
            }
        });
        favoriteCursor = getContentResolver().query(FavouriteContract.FavouriteEntry.CONTENT_URI,
                null,
                FavouriteContract.FavouriteEntry.COLUMN_NEWS_TITLE + "=?",
                new String[]{newsTitle},
                null);

        if (favoriteCursor.getCount() > 0) {
            try {
                mFavBtn.setChecked(true);
            } finally {
                favoriteCursor.close();
            }
        }
        mFavBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean isChecked) {

                if (isChecked) {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            ContentValues contentValues = new ContentValues();

                            contentValues.put(FavouriteContract.FavouriteEntry.COLUMN_NEWS_TITLE, newsTitle);
                            contentValues.put(FavouriteContract.FavouriteEntry.COLUMN_NEWS_AUTHOR, newsAuthor);
                            contentValues.put(FavouriteContract.FavouriteEntry.COLUMN_NEWS_DESCRIPTION, newsDescription);
                            contentValues.put(FavouriteContract.FavouriteEntry.COLUMN_NEWS_URL, newsUrl);
                            contentValues.put(FavouriteContract.FavouriteEntry.COLUMN_NEWS_URL_TO_IMAGE, newsImage);
                            contentValues.put(FavouriteContract.FavouriteEntry.COLUMN_NEWS_PUBLISHED_AT, newsDate);

                            //The actual insertion in the db.
                            uri = getContentResolver().insert(FavouriteContract.FavouriteEntry.CONTENT_URI, contentValues);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Toast.makeText(DetailActivity.this, "Article with title:  " + newsTitle + " was added", Toast.LENGTH_SHORT).show();

                        }
                    }.execute();
                } else {

                    Uri newsTitleOfFavNews = FavouriteContract.FavouriteEntry.buildNewsUriWithTitle(newsTitle);
                    //String title = uri.getPathSegments().get(1);// Get the task ID from the URI path

                    getContentResolver().delete(
                            newsTitleOfFavNews,
                            null,
                            null);
                    Toast.makeText(DetailActivity.this, "News article deleted from favourites ", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.detail_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.detail_comment_btn){
            Intent commentIntent = new Intent(DetailActivity.this, CommentActivity.class);
            commentIntent.putExtra("newsTitle",newsTitle);
            startActivity(commentIntent);
        }else if(item.getItemId() == R.id.detail_display_comment_btn){
            Intent displayCommentsIntent = new Intent(DetailActivity.this,DisplayComments.class);
            displayCommentsIntent.putExtra("newsTitle",newsTitle);
            startActivity(displayCommentsIntent);
        }
        else if(item.getItemId() == R.id.detail_browser_btn){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsUrl));
            startActivity(browserIntent);
        } if(item.getItemId() == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return true;
    }

    private Intent createShareNewsIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(NEWS_SHARE_HASHTAG + "\n\n\n" + newsTitle
                        + "\n\n\n" + newsDescription
                        + "\n\n\n" + newsDate)
                .getIntent();

        return shareIntent;
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mFavBtn.setChecked(savedInstanceState.getBoolean("ToggleButtonState",false));
    }

    @Override
     protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putBoolean("ToggleButtonState",mFavBtn.isChecked());
        }


    /*
    @Override
    protected void onPause() {
        super.onPause();
        bundle.putBoolean("ToggleButtonState", mFavBtn.isChecked());
    }

    @Override
    public void onResume() {
        super.onResume();
        mFavBtn.setChecked(bundle.getBoolean("ToggleButtonState",false));
    }
    */
}

