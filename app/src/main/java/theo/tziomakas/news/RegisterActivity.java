package theo.tziomakas.news;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private TextInputEditText mDisplayName;
    private TextInputEditText mEmail;
    private TextInputEditText mPassword;
    private Button mCreateBtn;

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;

    private ProgressDialog mProgress;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mToolbar = (Toolbar)findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mDisplayName = (TextInputEditText)findViewById(R.id.reg_display_name);
        mEmail = (TextInputEditText)findViewById(R.id.reg_email);
        mPassword = (TextInputEditText)findViewById(R.id.reg_password);
        mCreateBtn = (Button) findViewById(R.id.reg_create_btn);


        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String displayName = mDisplayName.getText().toString();
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if(!TextUtils.isEmpty(displayName) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

                    mProgress.setTitle("Registering User");
                    mProgress.setMessage("Please wait while we create your account");
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();

                    registerUser(displayName,email,password);
                }


            }

         });

    }

    private void registerUser(final String displayName, String email, String password){
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {

                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        String uId = currentUser.getUid();

                        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uId);
                        HashMap<String,String> userMap = new HashMap<>();
                        userMap.put("name",displayName);
                        userMap.put("status", "Hey.");
                        userMap.put("image","default");
                        userMap.put("thumb_page","default");

                        mDatabaseReference.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    mProgress.dismiss();
                                    sendToMain();
                                }
                            }
                        });
                    }else{
                        mProgress.hide();
                        Toast.makeText(RegisterActivity.this,"Can not register",Toast.LENGTH_SHORT).show();
                        Log.d(TAG, String.valueOf(task.getException()));
                    }
                }
            });

    }

    private void sendToMain() {
        Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(mainIntent);

        finish();
    }

}

