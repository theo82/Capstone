package theo.tziomakas.news;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button forgotPassBtn;
    EditText emailEt;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mToolbar = (Toolbar)findViewById(R.id.forgot_password_toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Forgot Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        forgotPassBtn = (Button)findViewById(R.id.send_email_btn);
        emailEt = (EditText)findViewById(R.id.email_forgot_et);

        forgotPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEt.getText().toString();

                resetUserPassword(email);
            }
        });

    }

    public void resetUserPassword(String email){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
        progressDialog.setMessage("verifying..");
        progressDialog.show();

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Reset password instructions has sent to your email",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    "Email don't exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
