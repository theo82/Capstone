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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
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
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1;
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

        mToolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.create_new_account));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mDisplayName = findViewById(R.id.reg_display_name);
        mEmail = findViewById(R.id.reg_email);
        mPassword = findViewById(R.id.reg_password);
        mCreateBtn = findViewById(R.id.reg_create_btn);


        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateForm();

                String displayName = mDisplayName.getText().toString();
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();


                if(!TextUtils.isEmpty(displayName) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

                    mProgress.setTitle(getResources().getString(R.string.registering_user));
                    mProgress.setMessage(getResources().getString(R.string.waiting_for_registration));
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();

                    if(checkPlayServices()) {
                        registerUser(displayName, email, password);
                    }
                }else{
                    Toast.makeText(RegisterActivity.this,getResources().getString(R.string.fill_all_fields),Toast.LENGTH_LONG).show();
                }
            }

         });

    }

    /**
     *
     * @param displayName
     * @param email
     * @param password
     */
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

    /**
     * A method that takes you to the main intent.
     */
    private void sendToMain() {
        Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(mainIntent);

        finish();
    }

    /**
     * Validating the form. We don't want the field to be empty.
     */
    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mDisplayName.getText().toString())) {
            mDisplayName.setError(getResources().getString(R.string.required));
            result = false;
        } else {
            mDisplayName.setError(null);
        }

        if (TextUtils.isEmpty(mEmail.getText().toString())) {
            mEmail.setError(getResources().getString(R.string.required));
            result = false;
        } else {
            mEmail.setError(null);
        }

        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            mPassword.setError(getResources().getString(R.string.required));
            result = false;
        } else {
            mPassword.setError(null);
        }

        return result;
    }

    /**
     * Checking if the phone has google play services installed.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, getResources().getString(R.string.device_not_supported));
                finish();
            }
            return false;
        }
        return true;
    }

}

