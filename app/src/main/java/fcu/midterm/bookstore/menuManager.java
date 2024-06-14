package fcu.midterm.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class menuManager extends AppCompatActivity {
    private Button btn_manage;
    private Button btn_status;
    private ImageButton account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_manager);
        btn_manage=findViewById(R.id.btn_bookman);
        btn_status = findViewById(R.id.book_status);
        account = findViewById(R.id.person_man);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.person_man) {
                    Intent intent = new Intent();
                    intent.setClass(menuManager.this, LoginActivity.class);
                    startActivity(intent);
                } else if (v.getId()==R.id.btn_bookman) {
                    Intent intent = new Intent();
                    intent.setClass(menuManager.this, book_manager.class);
                    startActivity(intent);
                } else if (v.getId()==R.id.book_status) {
                    Intent intent = new Intent();
                    intent.setClass(menuManager.this, book_state.class);
                    startActivity(intent);
                }
            }
        };
        account.setOnClickListener(listener);
        btn_status.setOnClickListener(listener);
        btn_manage.setOnClickListener(listener);
    }
}