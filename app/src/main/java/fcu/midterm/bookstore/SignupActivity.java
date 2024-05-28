package fcu.midterm.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private EditText signupName;
    private EditText signupEmail;
    private EditText signupPassword;
    private Button btnSignNew;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.signup_email);
        signupName = findViewById(R.id.signup_name);
        signupPassword = findViewById(R.id.signup_password);
        btnSignNew = findViewById(R.id.btnSignupnew);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signupEmail.getText().toString();
                String password = signupPassword.getText().toString();
                signUp(email,password);
            }
        };
        btnSignNew.setOnClickListener(listener);
    }
    private void signUp(String email,String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignupActivity.this,"註冊成功",Toast.LENGTH_SHORT).show();
                            SignupActivity.this.finish();
                        }
                        else {
                            Toast.makeText(SignupActivity.this,"註冊失敗",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}