package com.example.kristinamatuleviciute.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddBookActivity extends AppCompatActivity {

    TextView textViewArtistName;
    EditText editTextBookName;
    RatingBar  seekBarRating;

    Button buttonAddBook;

    ListView listViewBooks;

    DatabaseReference databaseBooks;
    List<Book> books;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        textViewArtistName = (TextView) findViewById(R.id.textViewArtistName);
        editTextBookName = (EditText) findViewById(R.id.editTextBook);
        seekBarRating =(RatingBar) findViewById(R.id.seekBarRating);

        buttonAddBook= (Button) findViewById(R.id.buttonAddBook);

        listViewBooks = (ListView) findViewById(R.id.listViewBooks);

        Intent intent = getIntent();

        books = new ArrayList<>();

        String id =  intent.getStringExtra(MainActivity.AUTHOR_ID);
        String name = intent.getStringExtra(MainActivity.AUTHOR_NAME);

        textViewArtistName.setText(name);

        databaseBooks = FirebaseDatabase.getInstance().getReference("books").child(id);

        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBook();

            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        databaseBooks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                books.clear();

                for(DataSnapshot bookSnapshot : dataSnapshot.getChildren()){
                    Book book = bookSnapshot.getValue(Book.class);
                    books.add(book);
                }

                BookList bookListAdapter = new BookList(AddBookActivity.this, books);
                listViewBooks.setAdapter(bookListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveBook(){

        String bookName = editTextBookName.getText().toString().trim();
        int rating = seekBarRating.getProgress();
        if(!TextUtils.isEmpty(bookName)){
            String id = databaseBooks.push().getKey();

            Book book = new Book(id, bookName, rating);

            databaseBooks.child(id).setValue(book);
            Toast.makeText(this, "Book saved successfully", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Enter book name, please", Toast.LENGTH_LONG).show();
        }

    }

}
