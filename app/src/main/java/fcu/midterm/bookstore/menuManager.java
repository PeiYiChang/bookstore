package fcu.midterm.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class menuManager extends AppCompatActivity {
    private Button btn_addBook;
    private Button btn_bookManage;
    private ImageButton account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_manager);

        btn_addBook=findViewById(R.id.btn_add_book);
        btn_bookManage = findViewById(R.id.btn_book_manage);
        account = findViewById(R.id.person_man);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.person_man) {
                    Intent intent = new Intent();
                    intent.setClass(menuManager.this, MainActivity.class);
                    startActivity(intent);
                } else if (v.getId()==R.id.btn_add_book) {
                    Intent intent = new Intent();
                    intent.setClass(menuManager.this, manager_addBook.class);
                    startActivity(intent);
                } else if (v.getId()==R.id.btn_book_manage) {
                    Intent intent = new Intent();
                    intent.setClass(menuManager.this, manager_manageBook.class);
                    startActivity(intent);
                }
            }
        };
        account.setOnClickListener(listener);
        btn_bookManage.setOnClickListener(listener);
        btn_addBook.setOnClickListener(listener);
    }
}