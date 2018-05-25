package theo.tziomakas.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import theo.tziomakas.news.model.Comment;
import theo.tziomakas.news.model.User;

public class CommentActivity extends AppCompatActivity {

    private static final String REQUIRED = "Required";
    private static final String TAG = CommentActivity.class.getSimpleName();

    Toolbar toolbar;

    DatabaseReference mDatabase;
    EditText titleEt;
    EditText bodyEt;
    Button commentBtn;
    String newsTitle;
    Intent i;
    String commentAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        toolbar = (Toolbar) findViewById(R.id.comment_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add comment");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        titleEt = (EditText) findViewById(R.id.comment_title);
        bodyEt = (EditText) findViewById(R.id.comment_body);
        commentBtn = (Button) findViewById(R.id.comment_btn);

        i = getIntent();

        newsTitle = i.getStringExtra("newsTitle");




        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitPost();
            }
        });

    }

    private void submitPost() {
        final String title = titleEt.getText().toString();
        final String body = bodyEt.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(title)) {
            titleEt.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(body)) {
            bodyEt.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        mDatabase.child("Users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(CommentActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            commentAuthor = dataSnapshot.child("name").getValue().toString();
                            writeNewPost(userId,commentAuthor,newsTitle, title, body);
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

   private void writeNewPost(String userId, String commentAuthor,String newsTitle, String commentTitle, String commentBody){
       String key = mDatabase.child("comments").push().getKey();
       Comment comment = new Comment(userId, commentAuthor,newsTitle,commentTitle,commentBody);
       Map<String, Object> commentValues = comment.toMap();

       Map<String, Object> childUpdates = new HashMap<>();
       childUpdates.put("/comments/" + key, commentValues);

       mDatabase.updateChildren(childUpdates);
   }

    private void setEditingEnabled(boolean enabled) {
        titleEt.setEnabled(enabled);
        bodyEt.setEnabled(enabled);
        if (enabled) {
            commentBtn.setVisibility(View.VISIBLE);
        } else {
            commentBtn.setVisibility(View.GONE);
        }
    }
}