package theo.tziomakas.news;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    ImageView mImageView;
    TextView mTitle;
    TextView mDate;
    TextView mDescription;

    private String newsTitle;
    private String newsImage;
    private String newsDate;
    private String newsDescription;
    private static String NEWS_SHARE_HASHTAG ="#PopularMoviesApp";
    private String date1;
    private String date2;
    private String newsUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar)findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent i = getIntent();

        mImageView = (ImageView)findViewById(R.id.detail_image_view);
        mTitle = (TextView)findViewById(R.id.detail_title);
        mDate = (TextView)findViewById(R.id.detail_publish_date);
        mDescription = (TextView)findViewById(R.id.detail_description);

        newsImage = i.getStringExtra("image");
        newsTitle = i.getStringExtra("title");
        newsDate = i.getStringExtra("date");
        newsDescription = i.getStringExtra("description");
        newsUrl = i.getStringExtra("url");

        date1 = newsDate.substring(0,10);
        date2 = newsDate.substring(11,19);

        Picasso.with(this).load(newsImage)
                .placeholder(R.drawable.ic_broken_image)
                .into(mImageView);

        mTitle.setText(newsTitle);
        mDescription.setText(newsDescription);
        mDate.setText(date2 + ", " + date1);


    }

    public void onShare(View view){
        Intent shareIntent = createShareNewsIntent();
        startActivity(shareIntent);
    }

    public void onShowBrowser(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsUrl));
        startActivity(browserIntent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.detail_menu,menu);

        return true;
    }

    private Intent createShareNewsIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(mTitle + NEWS_SHARE_HASHTAG + "\n\n\n" + newsTitle
                        + "\n\n\n" + newsDescription
                        + "\n\n\n" + newsDate)
                .getIntent();

        return shareIntent;
    }
}
