package faruqisan.thekost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mListener;
    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnSignup;
    private ProgressBar pbSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etSignupEmail);
        etPassword = (EditText) findViewById(R.id.etSignupPassword);
        btnSignup = (Button) findViewById(R.id.btnProcessSignup);
        pbSignup = (ProgressBar) findViewById(R.id.pbSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!name.isEmpty() || !email.isEmpty() || !password.isEmpty()) {

                }
            }
        });


    }

    public void signup(String name, String email, String password) {
        
    }
}
