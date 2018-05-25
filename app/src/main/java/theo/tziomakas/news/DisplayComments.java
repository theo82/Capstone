package theo.tziomakas.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import theo.tziomakas.news.adapters.DisplayCommentsAdapter;
import theo.tziomakas.news.adapters.SimpleDividerItemDecoration;
import theo.tziomakas.news.model.Comment;


public class DisplayComments extends AppCompatActivity {

    private static final String LOG_TAG = DisplayComments.class.getSimpleName();
    ArrayList<Comment> commentArrayList;
    Toolbar mToolbar;
    private DatabaseReference mDatabase;
    private Intent i;
    private String newsTitle;
    private TextView noCommentsTextView;
    private TextView commentsTextView;

    private RecyclerView mRecyclerView;
    private DisplayCommentsAdapter displayCommentsAdapter;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_comments);

        mToolbar = (Toolbar) findViewById(R.id.display_comments_app_bar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Display Comments");

        noCommentsTextView = (TextView)findViewById(R.id.noCommentsTextView);

        commentsTextView = (TextView)findViewById(R.id.commentsTextView);


        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_comments);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));


        mDatabase = FirebaseDatabase.getInstance().getReference();

        commentArrayList = new ArrayList<>();

        i = getIntent();

        newsTitle = i.getStringExtra("newsTitle");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Query query = mDatabase.child("comments").orderByChild("newsTitle").equalTo(newsTitle);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshots : dataSnapshot.getChildren()){
                        Comment comment = dataSnapshots.getValue(Comment.class);

                        //mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                        commentArrayList.add(comment);

                        displayCommentsAdapter = new DisplayCommentsAdapter(this,commentArrayList);

                        mRecyclerView.setAdapter(displayCommentsAdapter);

                        displayCommentsAdapter.setCommentsData(commentArrayList);

                        Log.d(LOG_TAG, String.valueOf(commentArrayList.size()));

                    }
                    //commentsTextView.setVisibility(View.VISIBLE);

                }else{
                    //Toast.makeText(DisplayComments.this,"There are no comments posted",Toast.LENGTH_LONG).show();
                    noCommentsTextView.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}