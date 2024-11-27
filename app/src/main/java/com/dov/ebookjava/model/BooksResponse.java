package com.dov.ebookjava.model;


import java.util.List;

public class BooksResponse {
    private List<Book> items;

    public List<Book> getBooks() {
        return items;
    }

    public void setBooks(List<Book> books) {
        this.items = books;
    }

    public static class Book {

        private String id;
        private BookInfo volumeInfo;

        public BookInfo getVolumeInfo() {
            return volumeInfo;
        }

        public void setVolumeInfo(BookInfo volumeInfo) {
            this.volumeInfo = volumeInfo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}