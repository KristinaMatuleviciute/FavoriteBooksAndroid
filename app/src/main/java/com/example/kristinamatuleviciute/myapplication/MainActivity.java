package com.example.kristinamatuleviciute.myapplication;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String AUTHOR_NAME = "authorname";
    public static final String AUTHOR_ID = "authorid";

    EditText editTextName;
    Button buttonAdd;
    Spinner spinnerGenres;

    DatabaseReference databaseAuthors;

    ListView listViewAuthors;
    List<Author> authorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseAuthors = FirebaseDatabase.getInstance().getReference("authors");

        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonAdd = (Button) findViewById(R.id.buttonAddAuthor);
        spinnerGenres = (Spinner) findViewById(R.id.spinnerGenres);

        listViewAuthors = (ListView) findViewById(R.id.listViewAuthors);

        authorList = new ArrayList<>();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAuthor();
            }
        });

        listViewAuthors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Author author = authorList.get(position);

                Intent intent = new Intent(getApplicationContext(), AddBookActivity.class);

                intent.putExtra(AUTHOR_ID, author.getAuthorId());
                intent.putExtra(AUTHOR_NAME, author.getAuthorName());

                startActivity(intent);

            }
        });

        listViewAuthors.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Author author = authorList.get(position);
                showUpdateDialog(author.getAuthorId(), author.getAuthorName());
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseAuthors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                authorList.clear();

                for (DataSnapshot authorSnapshot : dataSnapshot.getChildren()) {
                    Author author = authorSnapshot.getValue(Author.class);

                    authorList.add(author);
                }

                AuthorList adapter = new AuthorList(MainActivity.this, authorList);
                listViewAuthors.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDialog(final String authorId, String authorName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update, null);
        dialogBuilder.setView(dialogView);

        final EditText  editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final Spinner spinnerGenres = (Spinner) dialogView.findViewById(R.id.spinnerGenres);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);

        dialogBuilder.setTitle("Updating Author " + authorName);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String genre = spinnerGenres.getSelectedItem().toString();

                if(TextUtils.isEmpty(name)){
                    editTextName.setError("name required");
                    return;
                }
                updateAuthors(authorId, name, genre);

                alertDialog.dismiss();


            }
        });




    }

    private boolean updateAuthors(String id, String name, String genre){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("authors").child(id);
        Author author = new Author(id, name, genre);
        databaseReference.setValue(author);

        Toast.makeText(this, "Author updated successfully", Toast.LENGTH_LONG).show();
        return true;

    }

    private void addAuthor() {
        String name = editTextName.getText().toString().trim();
        String genre = spinnerGenres.getSelectedItem().toString();
        if (!TextUtils.isEmpty(name)) {

            String id = databaseAuthors.push().getKey();

            Author author = new Author(id, name, genre);
            databaseAuthors.child(id).setValue(author);

            Toast.makeText(this, "Author added", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Enter a name", Toast.LENGTH_LONG).show();
        }

    }


}

