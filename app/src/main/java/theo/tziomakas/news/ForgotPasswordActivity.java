package theo.tziomakas.news;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button forgotPassBtn;
    EditText emailEt;
    private Toolbar mToolbar;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mToolbar = (Toolbar)findViewById(R.id.forgot_password_toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.forgot_password_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        forgotPassBtn = (Button)findViewById(R.id.send_email_btn);
        emailEt = (EditText)findViewById(R.id.email_forgot_et);


        mProgress = new ProgressDialog(this);


        forgotPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEt.getText().toString();

                if(TextUtils.isEmpty(email)){
                    emailEt.setError("Required");
                }

                if(!TextUtils.isEmpty(email)){
                    mProgress.setTitle(getResources().getString(R.string.verify));
                    mProgress.setMessage(getResources().getString(R.string.wait_for_instructions));
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();

                    resetUserPassword(email);

                }
            }
        });

    }

    public void resetUserPassword(String email){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mProgress.dismiss();
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.success_email),
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            mProgress.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.email_do_not_exist), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgress.dismiss();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });



    }


}
