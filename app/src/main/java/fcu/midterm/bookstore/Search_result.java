package fcu.midterm.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class Search_result extends AppCompatActivity {
    private TextView bookNameShow;
    private TextView bookAuthorShow;
    private TextView bookPublisherShow;
    private TextView bookStateShow;
    private TextView bookIntroduceShow;
    private Button btnBorrowAdd;
    private ImageView bookImgShow;
    private StorageReference storageReference, pic_storage;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        bookNameShow = findViewById(R.id.name_show);
        bookAuthorShow = findViewById(R.id.author_show);
        bookPublisherShow = findViewById(R.id.publisher_show);
        bookStateShow = findViewById(R.id.state_show);
        bookIntroduceShow = findViewById(R.id.introduce_show);
        bookIntroduceShow.setMovementMethod(ScrollingMovementMethod.getInstance());
        btnBorrowAdd = findViewById(R.id.add_borrow);
        bookImgShow = findViewById(R.id.imageView2);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String message = bundle.getString("Book_Name");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("books");
        Query query = ref.orderByChild("bookName").equalTo(message);

        // Initialize Firebase Storage reference
        storageReference = FirebaseStorage.getInstance().getReference();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot bookSnapshot : dataSnapshot.getChildren()) {
                        String bookName = bookSnapshot.child("bookName").getValue(String.class);
                        String bookAuthor = bookSnapshot.child("bookAuthor").getValue(String.class);
                        String bookImgId = bookSnapshot.child("bookImgId").getValue(String.class);
                        String bookIntroduce = bookSnapshot.child("bookIntroduce").getValue(String.class);
                        String bookPublisher = bookSnapshot.child("bookPublisher").getValue(String.class);
                        String bookState = bookSnapshot.child("bookState").getValue(String.class);

                        bookNameShow.setText(bookName);
                        bookAuthorShow.setText(bookAuthor);
                        bookIntroduceShow.setText(bookIntroduce);
                        bookPublisherShow.setText(bookPublisher);
                        bookStateShow.setText(bookState);


                        if (bookImgId != null && !bookImgId.isEmpty()) {
                            pic_storage = storageReference.child(bookName+".jpg");
                            try {
                                final File file = File.createTempFile("images", "jpg");
                                pic_storage.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        bookImgShow.setImageURI(Uri.fromFile(file));
                                    }
                                }).addOnFailureListener(exception -> {
                                    // Handle any errors
                                    exception.printStackTrace();
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        btnBorrowAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isSignIn()){
                                    String email = mAuth.getCurrentUser().getEmail();
                                    Book book = new Book(bookName, bookState,email);
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference ref = database.getReference("borrow_list");
                                    ref.push().setValue(book);
                                    intent.setClass(Search_result.this, Order_list.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(Search_result.this, "登入帳號以進行操作", Toast.LENGTH_SHORT).show();
                                    intent.setClass(Search_result.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(Search_result.this, "沒有找到到書名為 " + message + " 的書籍", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
                databaseError.toException().printStackTrace();
            }
        });
    }
    private boolean isSignIn() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null;
    }
}
