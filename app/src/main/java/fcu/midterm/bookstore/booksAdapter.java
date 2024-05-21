package fcu.midterm.bookstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

import java.util.List;

public class booksAdapter extends BaseAdapter {
    private Context context;
    private List<Book> lsBooks;
    public booksAdapter(Context context, List<Book> lsBooks) {
        this.context = context;
        this.lsBooks = lsBooks;
    }
    public int getCount() {
        return lsBooks.size();
    }

    @Override
    public Object getItem(int position) {
        return lsBooks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.books_layout,viewGroup,false);
        }
        ImageView img = view.findViewById(R.id.imageView);
        TextView name = view.findViewById(R.id.book_name);
        TextView state = view.findViewById(R.id.state);

        Book book = lsBooks.get(index);
        img.setImageResource(book.getBookImgId());
        name.setText(book.getBookName());
        state.setText(String.valueOf(book.getBookState()));

        return view;
    }
}