package fcu.midterm.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class HistoryActivity extends AppCompatActivity {

    private FloatingActionButton fabLogin;
    private FloatingActionButton fabLogout;
    private FloatingActionButton fab_history;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        fabLogin = findViewById(R.id.fab_login);
        fabLogout = findViewById(R.id.fab_logout);
        fab_history = findViewById(R.id.fabHistory);
        mAuth = FirebaseAuth.getInstance();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(v.getId() == R.id.fab_logout){
                    mAuth.signOut();
                    intent.setClass(HistoryActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        fabLogout.setOnClickListener(listener);
    }

}