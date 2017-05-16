package faruqisan.thekost;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mListener;
    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnSignup;
    private ProgressBar pbSignup;
    private ConstraintLayout layout;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // move to main activity
                } else {
                    // no user signed in
                }
            }
        };

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etSignupEmail);
        etPassword = (EditText) findViewById(R.id.etSignupPassword);
        btnSignup = (Button) findViewById(R.id.btnProcessSignup);
        pbSignup = (ProgressBar) findViewById(R.id.pbSignup);

        layout = (ConstraintLayout) findViewById(R.id.layoutSignup);

        animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(1000);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!name.isEmpty() || !email.isEmpty() || !password.isEmpty()) {
                    signup(name,email,password);
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mListener != null) {
            mAuth.removeAuthStateListener(mListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        animationDrawable.start();
    }

    public void signup(String name, String email, String password) {
        pbSignup.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        pbSignup.setVisibility(View.GONE);
                        boolean isSuccess = task.isSuccessful();
                        if (isSuccess) {
                            Toast.makeText(RegisterActivity.this,"Signup Success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Intent intent = new Intent();
                            intent.putExtra("email",user.getEmail());
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this,"Signup Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pbSignup.setVisibility(View.GONE);
                Toast.makeText(RegisterActivity.this,"Signup Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
