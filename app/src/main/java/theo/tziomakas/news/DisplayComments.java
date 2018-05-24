package theo.tziomakas.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import theo.tziomakas.news.model.Comment;


public class DisplayComments extends AppCompatActivity {

    Toolbar mToolbar;
    private DatabaseReference mDatabase;
    private Intent i;
    private String newsTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_comments);

        mToolbar = (Toolbar)findViewById(R.id.display_comments_app_bar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Display Comments");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        i = getIntent();

        newsTitle = i.getStringExtra("newsTitle");

        Query commentQuery = mDatabase.child("comments")
                .equalTo(newsTitle);

        if(commentQuery == null){
            Toast.makeText(DisplayComments.this,"There are no comments posted",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(DisplayComments.this,"There are comments posted",Toast.LENGTH_LONG).show();
        }
     }
}
