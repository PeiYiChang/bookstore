package fcu.midterm.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class manager_manageBook extends AppCompatActivity {

    private ListView listBooksState;
    private List<Map<String, String>> state_list;
    private SimpleAdapter adapter;
    private enum State{
        借閱中, 館藏中
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_state);

        listBooksState = findViewById(R.id.listView);
        state_list = new ArrayList<>();
        adapter = new SimpleAdapter(this, state_list, R.layout.book_state_layout_manager,
                new String[]{"bookName", "bookState"},
                new int[]{R.id.name_state, R.id.book_state});

        listBooksState.setAdapter(adapter);

        getAllBooks();
    }

    private void getAllBooks() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("books");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                state_list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Book book = ds.getValue(Book.class);
                    if (book != null) {
                        Map<String, String> map = new HashMap<>();
                        map.put("bookName", book.getBookName());
                        map.put("bookState", book.getBookState());
                        state_list.add(map);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 處理數據庫錯誤
                Toast.makeText(manager_manageBook.this, "讀取數據失敗", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onDeleteButtonClick(View view) {
        // 获取点击的书籍名称
        int position = listBooksState.getPositionForView(view);
        Map<String, String> bookMap = state_list.get(position);
        String bookNameToDelete = bookMap.get("bookName");

        // 从数据库中删除书籍
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("books");
        ref.orderByChild("bookName").equalTo(bookNameToDelete).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ds.getRef().removeValue();
                }
                Toast.makeText(manager_manageBook.this, "書籍已刪除", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(manager_manageBook.this, "刪除失敗", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
