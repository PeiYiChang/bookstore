package fcu.midterm.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmail;
    private EditText loginPassword;
    private Button btnCus;
    private Button btnMan;
    private Button btnSignup;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        btnCus = findViewById(R.id.btnCustomer);
        btnMan = findViewById(R.id.btnManager);
        btnSignup = findViewById(R.id.btnSignup);
        mAuth = FirebaseAuth.getInstance();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btnCustomer){
                    String email = loginEmail.getText().toString();
                    String password = loginPassword.getText().toString();
                    signIn(email,password);
                } else if (view.getId() == R.id.btnSignup) {
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,SignupActivity.class);
                    LoginActivity.this.startActivity(intent);
                }
            }
        };
        btnCus.setOnClickListener(listener);
        btnMan.setOnClickListener(listener);
        btnSignup.setOnClickListener(listener);
    }
    private void signIn(String email,String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            LoginActivity.this.finish();
                        }else {
                            Toast.makeText(LoginActivity.this,"登入失敗，請檢察帳號與密碼",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}