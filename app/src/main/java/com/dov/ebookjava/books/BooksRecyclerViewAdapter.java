package com.dov.ebookjava.books;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dov.ebookjava.R;
import com.dov.ebookjava.model.BooksResponse;
import com.dov.ebookjava.model.Response;

import java.util.ArrayList;
import java.util.List;

public class BooksRecyclerViewAdapter  extends RecyclerView.Adapter<BooksViewHolder> {
    private List<Response.Item> books;

    public interface OnItemAction {
        void onFavoriteClick(Response.Item book);
        void onShowDetails(Response.Item book);
    }
    private OnItemAction onItemAction;

    public BooksRecyclerViewAdapter(List<Response.Item> books, OnItemAction onItemAction) {
        this.books = books;
        this.onItemAction = onItemAction;
    }

    public void setBooks(List<Response.Item> bookInfos) {
        this.books = bookInfos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.book_item, parent, false);
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        holder.bind(books.get(position), onItemAction);
    }

    @Override
    public int getItemCount() {
      return books.size();
    }

}
