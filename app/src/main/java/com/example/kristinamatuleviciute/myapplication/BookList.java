package com.example.kristinamatuleviciute.myapplication;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BookList extends ArrayAdapter<Book> {

    private Activity context;
    private List<Book> books;

    public BookList(Activity context, List<Book> books){
        super(context, R.layout.book_list_layout, books);
        this.context = context;
        this.books = books;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.book_list_layout, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewRating = (TextView) listViewItem.findViewById(R.id.textViewRating);

        Book book = books.get(position);

        textViewName.setText(book.getBookName());
        textViewRating.setText(String.valueOf(book.getBookRating()));

        return listViewItem;

    }
}
