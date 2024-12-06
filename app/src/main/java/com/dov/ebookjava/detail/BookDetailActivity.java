package com.dov.ebookjava.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.dov.ebookjava.R;
import com.dov.ebookjava.model.BooksResponse;
import com.dov.ebookjava.model.Response;
import com.google.android.material.imageview.ShapeableImageView;

public class BookDetailActivity extends AppCompatActivity {
    AppCompatTextView title, author, description;
    ShapeableImageView bookImage;

    public static final String INTENT_TITLE = "title";
    public static final String INTENT_AUTHOR = "author";
    public static final String INTENT_DESCRIPTION = "description";
    public static final String INTENT_IMAGE = "image";


    public static Intent newIntent(Context context, Response.Item book) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(INTENT_TITLE, book.volumeInfo.title);
        intent.putExtra(INTENT_AUTHOR, book.volumeInfo.authors.get(0));
        intent.putExtra(INTENT_DESCRIPTION, book.volumeInfo.description);
        if (book.volumeInfo.imageLinks != null) {
            intent.putExtra(INTENT_IMAGE, book.volumeInfo.imageLinks.smallThumbnail);
        }
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setViewItems();
    }

    public void setViewItems() {
        title = findViewById(R.id.title_tv);
        author = findViewById(R.id.author_tv);
        description = findViewById(R.id.description_tv);
        bookImage = findViewById(R.id.image_iv);
        title.setText(getIntent().getStringExtra(INTENT_TITLE));
        setTitle(title.getText().toString());
        author.setText(getIntent().getStringExtra(INTENT_AUTHOR));
        description.setText(getIntent().getStringExtra(INTENT_DESCRIPTION));
        if (getIntent().getStringExtra(INTENT_IMAGE) != null) {
            Glide.with(this).load(getIntent().getStringExtra(INTENT_IMAGE)).into(bookImage);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}