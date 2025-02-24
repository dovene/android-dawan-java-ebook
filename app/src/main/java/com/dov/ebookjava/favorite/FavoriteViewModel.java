package com.dov.ebookjava.favorite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dov.ebookjava.database.AppRoomDatabase;
import com.dov.ebookjava.database.BookEntity;
import com.dov.ebookjava.database.BookMapper;
import com.dov.ebookjava.model.BooksResponse;

import java.util.ArrayList;
import java.util.List;


public class FavoriteViewModel extends AndroidViewModel {
    private final MutableLiveData<List<BooksResponse.Book>> _books = new MutableLiveData<>();
    public LiveData<List<BooksResponse.Book>> books = _books;

    private final MutableLiveData<Boolean> _isAddedToFavorite = new MutableLiveData<>();
    public LiveData<Boolean> isAddedToFavorite = _isAddedToFavorite;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
    }

    public void getAllBooks() {
        AppRoomDatabase db = AppRoomDatabase.getInstance(getApplication());
        new Thread(() -> {
            List<BookEntity> books = db.userDao().getAll();
            List<BooksResponse.Book> booksResponse = new ArrayList<>();
            for (BookEntity book : books) {
                booksResponse.add(BookMapper.toModel(book));
            }
            _books.postValue(booksResponse);
        }).start();
    }

    public void removeFromFavorite(BooksResponse.Book book) {
        new Thread(() -> {
            AppRoomDatabase db = AppRoomDatabase.getInstance(getApplication());
            db.userDao().deleteByIdString(book.getId());
            _isAddedToFavorite.postValue(false);
            getAllBooks();
        }).start();
    }


}