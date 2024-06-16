package fcu.midterm.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private TextView showUser;
    private EditText etSearch;
    private Button btn_find;
    private ListView listBooks;
    private FirebaseAuth mAuth;
    private FloatingActionMenu famMenu, famUser;
    private FloatingActionButton fabLogin;
    private FloatingActionButton fabLogout;
    private FloatingActionButton fab_history,fab_orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etSearch = findViewById(R.id.et_search);
        showUser = findViewById(R.id.show_user);
        listBooks= findViewById(R.id.listBooks);
        btn_find = findViewById(R.id.btn_find);
        mAuth = FirebaseAuth.getInstance();
        fabLogin = findViewById(R.id.fab_login);
        fabLogout = findViewById(R.id.fab_logout);
        fab_history = findViewById(R.id.fabHistory);
        famMenu = findViewById(R.id.fam_menu);
        famUser =  findViewById(R.id.fam_user);
        fab_orderList = findViewById(R.id.fab_orderList);

        Book book1 = new Book(R.drawable.book1,"原子習慣","");
        Book book2 = new Book(R.drawable.book2,"被討厭的勇氣","");
        Book book3 = new Book(R.drawable.book3,"小王子","");
        Book book4 = new Book(R.drawable.book4,"這世界很煩，但你要很可愛","");
        Book book5 = new Book(R.drawable.book5, "我可能錯了: 森林智者的最後一堂人生課", "");
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        booksAdapter adapter = new booksAdapter(this,books);
        listBooks.setAdapter(adapter);

//        View.OnClickListener listener1 = new View.OnClickListener() {
//            boolean enable= true;
//            @Override
//            public void onClick(View v) {
//                enable = !enable;
//                etSearch.setEnabled(enable);
//            }
//        };
//        famMenu.setOnClickListener(listener1);
//        famUser.setOnClickListener(listener1);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                if(v.getId() == R.id.fab_login && !isSignIn()){
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else if(v.getId() == R.id.fab_logout){
                    if(isSignIn()){
                        mAuth.signOut();
                        Toast.makeText(MainActivity.this, "用戶已登出", Toast.LENGTH_SHORT).show();
                        showUser.setText(" ");
                    }
                }else if(v.getId() == R.id.fabHistory){
                    if(isSignIn()){
                        intent.setClass(MainActivity.this, HistoryActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this, "登入帳號以進行操作", Toast.LENGTH_SHORT).show();
                        intent.setClass(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }else if(v.getId() == R.id.btn_find){
                    intent.setClass(MainActivity.this, Search_result.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Book_Name", etSearch.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else if(v.getId() == R.id.fab_orderList){
                    if(isSignIn()){
                        intent.setClass(MainActivity.this, Order_list.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "登入帳號以進行操作", Toast.LENGTH_SHORT).show();
                        intent.setClass(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }
        };
        fabLogin.setOnClickListener(listener);
        fab_history.setOnClickListener(listener);
        fabLogout.setOnClickListener(listener);
        btn_find.setOnClickListener(listener);
        fab_orderList.setOnClickListener(listener);
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