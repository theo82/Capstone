package theo.tziomakas.news;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private TextInputEditText mLoginEmail;
    private TextInputEditText mLoginPassword;

    private Button mLoginBtn;
    private Button mForgotPass;

    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mToolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");


        mAuth = FirebaseAuth.getInstance();

        mProgress = new ProgressDialog(this);


        mLoginEmail = (TextInputEditText)findViewById(R.id.login_email);
        mLoginPassword = (TextInputEditText)findViewById(R.id.login_password);
        mLoginBtn = (Button)findViewById(R.id.login_create_btn);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mLoginEmail.getText().toString();
                String pass = mLoginPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(pass)){

                    mProgress.setTitle("Logging in");
                    mProgress.setMessage("Please wait while we check your credentials");
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();

                    loginUser(email,pass);
                    
                }

            }
        });

        mForgotPass = (Button)findViewById(R.id.login_forgot_password);
        mForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(i);
            }
        });

    }

    private void loginUser(final String email, final String pass) {

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {
                        mProgress.dismiss();
                        sendToMain();
                    } else {
                        mProgress.hide();

                        Toast.makeText(LoginActivity.this, "Can not login", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, String.valueOf(task.getException()));
                    }
                }


        });
    }

    private void sendToMain() {
        Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
