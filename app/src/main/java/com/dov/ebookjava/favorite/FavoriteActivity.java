package com.dov.ebookjava.favorite;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dov.ebookjava.books.BooksRecyclerViewAdapter;
import com.dov.ebookjava.R;
import com.dov.ebookjava.detail.BookDetailActivity;
import com.dov.ebookjava.model.BooksResponse;
import com.dov.ebookjava.model.Response;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    FavoriteViewModel viewModel;
    BooksRecyclerViewAdapter booksRecyclerViewAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Mes favoris");
        viewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        setViewItems();
        setObservers();
        viewModel.getAllBooks();
    }


    private void setViewItems() {
        booksRecyclerViewAdapter = new BooksRecyclerViewAdapter(new ArrayList<>(), new BooksRecyclerViewAdapter.OnItemAction() {
            @Override
            public void onFavoriteClick(Response.Item book) {
                viewModel.removeFromFavorite(book);
            }
            @Override
            public void onShowDetails(Response.Item book) {
                startActivity(BookDetailActivity.newIntent(FavoriteActivity.this, book) );
            }
        });
        recyclerView = findViewById(R.id.favorites_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(booksRecyclerViewAdapter);

    }

    private void setObservers() {
        viewModel.books.observe(this, books -> {
            booksRecyclerViewAdapter.setBooks(books);
        });

        viewModel.isAddedToFavorite.observe(this, isAddedToFavorite -> {
            if (isAddedToFavorite) {
                Toast.makeText(this, "Livre ajouté aux favoris", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Livre supprimé des favoris", Toast.LENGTH_SHORT).show();
            }
            booksRecyclerViewAdapter.notifyDataSetChanged();
        });
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