package fcu.midterm.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private TextView showUser;

    private ListView listBooks;

    //private ImageButton account;
    private FirebaseAuth mAuth;

    private FloatingActionButton fab_login;
    private FloatingActionButton fab_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //account = findViewById(R.id.person);
        showUser = findViewById(R.id.show_user);
        listBooks= findViewById(R.id.listBooks);
        mAuth = FirebaseAuth.getInstance();
        fab_login = findViewById(R.id.fab_login);
        fab_signup = findViewById(R.id.fab_signup);

        Book book1 = new Book(R.drawable.book1,"原子習慣","館藏中");
        Book book2 = new Book(R.drawable.book2,"被討厭的勇氣","借出");
        Book book3 = new Book(R.drawable.book3,"小王子","館藏中");
        Book book4 = new Book(R.drawable.book4,"這世界很煩，但你要很可愛","館藏中");
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        booksAdapter adapter = new booksAdapter(this,books);
        listBooks.setAdapter(adapter);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        };
        fab_login.setOnClickListener(listener);

    }
    private boolean isSignIn() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null;
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (isSignIn()){
            String email = mAuth.getCurrentUser().getEmail();
            showUser.setText("歡迎 "+email);
        }
    }
}