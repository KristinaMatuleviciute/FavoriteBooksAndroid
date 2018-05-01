package com.example.kristinamatuleviciute.myapplication;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class AuthorList extends ArrayAdapter<Author>{

    private Activity context;
    private List<Author> authorList;

    public AuthorList(Activity context, List<Author> authorList){
        super(context, R.layout.list_layout, authorList);
        this.context = context;
        this.authorList = authorList;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenri);

        Author author = authorList.get(position);

        textViewName.setText(author.getAuthorName());
        textViewGenre.setText(author.getAristGendre());

        return listViewItem;

    }


}
