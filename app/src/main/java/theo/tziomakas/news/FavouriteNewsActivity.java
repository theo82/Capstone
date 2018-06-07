package theo.tziomakas.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import theo.tziomakas.news.fragments.FavouriteNewsFragment;

public class FavouriteNewsActivity extends AppCompatActivity {

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
