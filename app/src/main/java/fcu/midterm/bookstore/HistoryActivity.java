package fcu.midterm.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    private ListView lvHistory;
    private List<Map<String, String>> list_history = new ArrayList<>();
    private SimpleAdapter adapter;
    private FirebaseAuth mAuth;
    private Button backHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lvHistory = findViewById(R.id.lv_history);

        adapter = new HistoryAdapter(this, list_history, R.layout.books_return_layout,
                new String[]{"bookName", "bookState"},
                new int[]{R.id.tv_name_return, R.id.tv_return_state});
        lvHistory.setAdapter(adapter);
        listHistoryRecord();
    }
    private void listHistoryRecord(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("borrow_history");
        mAuth = FirebaseAuth.getInstance();
        backHome = findViewById(R.id.backhome);
        String email = mAuth.getCurrentUser().getEmail();
        ref.orderByChild("personName").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_history.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Book book = ds.getValue(Book.class);
                    if (book != null) {
                        Map<String, String> map = new HashMap<>();
                        map.put("bookName", book.getBookName());
                        map.put("bookState", book.getBookState());
                        list_history.add(map);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 處理數據庫錯誤
                Toast.makeText(HistoryActivity.this, "讀取數據失敗", Toast.LENGTH_SHORT).show();
            }
        });
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(HistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void onBorrowButtonClick2(View view) {
        mAuth = FirebaseAuth.getInstance();
        String email = mAuth.getCurrentUser().getEmail();
        // 獲取書籍點擊位置
        int position = lvHistory.getPositionForView(view);
        if (position == ListView.INVALID_POSITION) {
            return;
        }

        Map<String, String> bookMap = list_history.get(position);
        String bookNameToBorrow = bookMap.get("bookName");

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // 更新database(books)書籍狀態
        DatabaseReference changeRef = database.getReference("books");
        changeRef.orderByChild("bookName").equalTo(bookNameToBorrow).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ds.getRef().child("bookState").setValue(manager_addBook.State.借閱中.toString());
                }
                Toast.makeText(HistoryActivity.this, "書籍狀態更新成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HistoryActivity.this, "書籍狀態更新失敗", Toast.LENGTH_SHORT).show();
            }
        });
        // 更新列表中的書籍狀態
        bookMap.put("bookState", "借閱中");
        adapter.notifyDataSetChanged();

        DatabaseReference ref = database.getReference("borrow_history");
        ref.orderByChild("bookName").equalTo(bookNameToBorrow).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ds.getRef().child("bookState").setValue(manager_addBook.State.借閱中.toString());
                }
                Toast.makeText(HistoryActivity.this, "書籍狀態更新成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HistoryActivity.this, "書籍狀態更新失敗", Toast.LENGTH_SHORT).show();
            }
        });
        // 更新列表中的書籍狀態
        bookMap.put("bookState", "借閱中");
        adapter.notifyDataSetChanged();

        DatabaseReference listRef = database.getReference("borrow_list");
        listRef.orderByChild("bookName").equalTo(bookNameToBorrow).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ds.getRef().child("bookState").setValue(manager_addBook.State.借閱中.toString());
                }
                Toast.makeText(HistoryActivity.this, "書籍狀態更新成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HistoryActivity.this, "書籍狀態更新失敗", Toast.LENGTH_SHORT).show();
            }
        });
        // 更新列表中的書籍狀態1
        bookMap.put("bookState", "借閱中");
        adapter.notifyDataSetChanged();
    }
    public void onReturnButtonClick(View view) {
        mAuth = FirebaseAuth.getInstance();
        String email = mAuth.getCurrentUser().getEmail();
        // 獲取書籍點擊位置
        int position = lvHistory.getPositionForView(view);
        if (position == ListView.INVALID_POSITION) {
            return;
        }

        Map<String, String> bookMap = list_history.get(position);
        String bookNameToReturn = bookMap.get("bookName");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("borrow_history");
        ref.orderByChild("bookName").equalTo(bookNameToReturn).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ds.getRef().child("bookState").setValue(manager_addBook.State.館藏中.toString());
                }
                Toast.makeText(HistoryActivity.this, "書籍狀態更新成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HistoryActivity.this, "書籍狀態更新失敗", Toast.LENGTH_SHORT).show();
            }
        });
        // 更新列表中的書籍狀態
        bookMap.put("bookState", "館藏中");
        adapter.notifyDataSetChanged();

        DatabaseReference listRef = database.getReference("borrow_list");
        listRef.orderByChild("bookName").equalTo(bookNameToReturn).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ds.getRef().child("bookState").setValue(manager_addBook.State.館藏中.toString());
                }
                Toast.makeText(HistoryActivity.this, "書籍狀態更新成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HistoryActivity.this, "書籍狀態更新失敗", Toast.LENGTH_SHORT).show();
            }
        });
        // 更新列表中的書籍狀態
        bookMap.put("bookState", "館藏中");
        adapter.notifyDataSetChanged();
        // 更新database(books)書籍狀態
        DatabaseReference changeRef = database.getReference("books");
        changeRef.orderByChild("bookName").equalTo(bookNameToReturn).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ds.getRef().child("bookState").setValue(manager_addBook.State.館藏中.toString());
                }
                Toast.makeText(HistoryActivity.this, "書籍狀態更新成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HistoryActivity.this, "書籍狀態更新失敗", Toast.LENGTH_SHORT).show();
            }
        });

        // 更新列表中的書籍狀態
        bookMap.put("bookState", "館藏中");
        adapter.notifyDataSetChanged();
    }
}