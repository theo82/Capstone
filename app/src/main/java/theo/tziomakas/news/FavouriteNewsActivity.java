package theo.tziomakas.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import theo.tziomakas.news.fragments.FavouriteNewsFragment;

public class FavouriteNewsActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_news);

        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction().add(R.id.container,new FavouriteNewsFragment())
                    .commit();
        }
    }
}
