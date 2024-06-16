package fcu.midterm.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order_list extends AppCompatActivity {
    private ListView borrowList;
    private List<Map<String, String>> borrow_list;
    private SimpleAdapter adapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        borrowList = findViewById(R.id.borrow_list);
        mAuth = FirebaseAuth.getInstance();
        String email = mAuth.getCurrentUser().getEmail();

        borrow_list = new ArrayList<>();
        adapter = new SimpleAdapter(this, borrow_list, R.layout.borrow_list_layout,
                new String[]{"bookName", "bookState"},
                new int[]{R.id.name_order, R.id.order_state});

        borrowList.setAdapter(adapter);

        getAllBooks();
    }

    private void getAllBooks() {
        mAuth = FirebaseAuth.getInstance();
        String email = mAuth.getCurrentUser().getEmail();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("borrow_list");

        ref.orderByChild("personName").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                borrow_list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Book book = ds.getValue(Book.class);
                    if (book != null) {

                            Map<String, String> map = new HashMap<>();
                            map.put("bookName", book.getBookName());
                            map.put("bookState", book.getBookState());
                            borrow_list.add(map);

                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Order_list.this, "讀取數據失敗", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBorrowButtonClick(View view) {
        mAuth = FirebaseAuth.getInstance();
        String email = mAuth.getCurrentUser().getEmail();
        // 獲取書籍點擊位置
        int position = borrowList.getPositionForView(view);
        if (position == ListView.INVALID_POSITION) {
            return;
        }

        Map<String, String> bookMap = borrow_list.get(position);
        String bookNameToBorrow = bookMap.get("bookName");

        // 從database(borrow_list)中刪除此筆資料
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("borrow_list");
        ref.orderByChild("bookName").equalTo(bookNameToBorrow).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ds.getRef().removeValue();
                }
                Toast.makeText(Order_list.this, "書籍借閱成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Order_list.this, "書籍借閱失敗", Toast.LENGTH_SHORT).show();
            }
        });

        // 获取用户的名称或ID（假设您使用Firebase Authentication）
        String bookState = manager_addBook.State.借閱中.toString(); // 更新書籍狀態
        String bookAuthor = bookMap.get("bookAuthor");

        Book book = new Book(bookNameToBorrow, bookState, bookAuthor, email);
        DatabaseReference newRef = database.getReference("borrow_history");
        newRef.push().setValue(book);

        // 更新database(books)書籍狀態
        DatabaseReference changeRef = database.getReference("books");
        changeRef.orderByChild("bookName").equalTo(bookNameToBorrow).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ds.getRef().child("bookState").setValue(manager_addBook.State.借閱中.toString());
                }
                Toast.makeText(Order_list.this, "書籍狀態更新成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Order_list.this, "書籍狀態更新失敗", Toast.LENGTH_SHORT).show();
            }
        });

        // 更新列表中的書籍狀態
        bookMap.put("bookState", "借閱中");
        adapter.notifyDataSetChanged();
    }
}
