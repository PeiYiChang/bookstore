package fcu.midterm.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        bookNameShow = findViewById(R.id.name_show);
        bookAuthorShow = findViewById(R.id.author_show);
        bookPublisherShow = findViewById(R.id.publisher_show);
        bookStateShow = findViewById(R.id.state_show);
        bookIntroduceShow = findViewById(R.id.introduce_show);
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
                            pic_storage = storageReference.child(bookImgId);
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
                    }
                } else {
                    Toast.makeText(Search_result.this, "没有找到书名为 " + message + " 的书籍", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
                databaseError.toException().printStackTrace();
            }
        });
    }
}
