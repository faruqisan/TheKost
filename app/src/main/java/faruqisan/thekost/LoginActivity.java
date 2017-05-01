package faruqisan.thekost;

import android.app.ActionBar;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button btnSignin;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvAppName;
    private ConstraintLayout layout;
    private ProgressBar pbLogin;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignin = (Button) findViewById(R.id.btnSignIn);
        etEmail = (EditText) findViewById(R.id.etEmailAddress);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvAppName = (TextView) findViewById(R.id.tvAppName);
        pbLogin = (ProgressBar) findViewById(R.id.pbLogin);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "IndieFlower.ttf");
        tvAppName.setTypeface(typeface);

        layout = (ConstraintLayout) findViewById(R.id.loginlayout);

        animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(1000);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
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

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if(!email.isEmpty() || !password.isEmpty()){
                    Log.d("Inp email",email);
                    Log.d("Inp pass",password);
                    signIn(email,password);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        animationDrawable.start();
    }

    public void signIn(String email, String password) {
        pbLogin.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pbLogin.setVisibility(View.GONE);
                        boolean result = task.isSuccessful();
                        if(result){

                            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                String email = user.getEmail();
                                Toast.makeText(LoginActivity.this,"Welcome "+email, Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(LoginActivity.this,"Signin Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
