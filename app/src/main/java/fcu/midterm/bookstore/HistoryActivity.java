package fcu.midterm.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    private ListView lvHistory;
    private List<Map<String, String>> list_history = new ArrayList<>();
    private SimpleAdapter adapter;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lvHistory = findViewById(R.id.lv_history);

        adapter = new SimpleAdapter(this, list_history, R.layout.borrow_list_layout,
                new String[]{"bookName", "bookState"},
                new int[]{R.id.name_order, R.id.order_state});
        lvHistory.setAdapter(adapter);
        listHistoryRecord();
    }
    private void listHistoryRecord(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("borrow_history");
        mAuth = FirebaseAuth.getInstance();
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
    }
}