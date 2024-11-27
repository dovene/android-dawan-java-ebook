package com.dov.ebookjava.books;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dov.ebookjava.R;
import com.dov.ebookjava.detail.BookDetailActivity;
import com.dov.ebookjava.favorite.FavoriteActivity;
import com.dov.ebookjava.model.BooksResponse;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BooksViewModel viewModel;
    EditText searchEditText;
    BooksRecyclerViewAdapter booksRecyclerViewAdapter = new BooksRecyclerViewAdapter(new ArrayList<>(), new BooksRecyclerViewAdapter.OnItemAction() {
        @Override
        public void onFavoriteClick(BooksResponse.Book book) {
            viewModel.toggleFavorite(book);
        }
        @Override
        public void onShowDetails(BooksResponse.Book book) {
            startActivity(BookDetailActivity.newIntent(MainActivity.this, book));
        }
    });
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(BooksViewModel.class);
        setViewItems();
        setObservers();
    }


    private void setViewItems() {
        searchEditText = findViewById(R.id.search_et);
        progressBar = findViewById(R.id.progress_bar);
        findViewById(R.id.search_bt).setOnClickListener(v -> {
            viewModel.getBooks(searchEditText.getText().toString());
            closeKeyboard();
        });
        //booksRecyclerViewAdapter =
        recyclerView = findViewById(R.id.books_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(booksRecyclerViewAdapter);
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void setObservers() {
        viewModel.books.observe(this, books -> {
            booksRecyclerViewAdapter.setBooks(books);
        });

        viewModel.error.observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });

        viewModel.loading.observe(this, loading -> {
            if (loading) {
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_favorites) {
            startActivity(new Intent(this, FavoriteActivity.class));
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}