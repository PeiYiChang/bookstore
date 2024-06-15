package fcu.midterm.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
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
    private EditText etSearch;

    private ListView listBooks;
    private FirebaseAuth mAuth;
    private FloatingActionMenu famMenu, famUser;
    private FloatingActionButton fabLogin;
    private FloatingActionButton fabLogout;
    private FloatingActionButton fab_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etSearch = findViewById(R.id.et_search);
        showUser = findViewById(R.id.show_user);
        listBooks= findViewById(R.id.listBooks);
        mAuth = FirebaseAuth.getInstance();
        fabLogin = findViewById(R.id.fab_login);
        fabLogout = findViewById(R.id.fab_logout);
        fab_history = findViewById(R.id.fabHistory);
        famMenu = findViewById(R.id.fam_menu);
        famUser =  findViewById(R.id.fam_user);

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

        View.OnClickListener listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setEnabled(false);
            }
        };
        famMenu.setOnClickListener(listener1);
        famUser.setOnClickListener(listener1);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(v.getId() == R.id.fab_login && !isSignIn()){
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                if(v.getId() == R.id.fab_logout && isSignIn()){
                    mAuth.signOut();
                    Toast.makeText(MainActivity.this, "用戶已登出", Toast.LENGTH_SHORT).show();
                }

                if(v.getId() == R.id.fabHistory && isSignIn()){
                    intent.setClass(MainActivity.this, HistoryActivity.class);
                    startActivity(intent);
                }
                if(v.getId() == R.id.fabHistory && !isSignIn()){
                    Toast.makeText(MainActivity.this, "登入帳號以進行操作", Toast.LENGTH_SHORT).show();
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        fabLogin.setOnClickListener(listener);
        fab_history.setOnClickListener(listener);
        fabLogout.setOnClickListener(listener);

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