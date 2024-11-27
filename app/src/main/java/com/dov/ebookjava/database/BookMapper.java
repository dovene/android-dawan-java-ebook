package com.dov.ebookjava.database;

import com.dov.ebookjava.model.BookInfo;
import com.dov.ebookjava.model.BooksResponse;

import java.util.Arrays;

public class BookMapper {

    public static BookEntity toEntity(BooksResponse.Book book) {
        BookEntity entity = new BookEntity();
        entity.setId(book.getId());
        BookInfo volumeInfo = book.getVolumeInfo();
        if (volumeInfo != null) {
            entity.setTitle(volumeInfo.getTitle());

            String authors = String.join("; ", volumeInfo.getAuthors());
            entity.setAuthors(authors);


            entity.setDescription(volumeInfo.getDescription());
            entity.setLanguage(volumeInfo.getLanguage());
            entity.setPreviewLink(volumeInfo.getPreviewLink());
            entity.setInfoLink(volumeInfo.getInfoLink());
            entity.setCanonicalVolumeLink(volumeInfo.getCanonicalVolumeLink());
            if (volumeInfo.getImageLinks() != null) {
                entity.setSmallThumbnail(volumeInfo.getImageLinks().getSmallThumbnail());
                entity.setThumbnail(volumeInfo.getImageLinks().getThumbnail());
            }
        }
        return entity;
    }

    public static BooksResponse.Book toModel(BookEntity entity) {
        BooksResponse.Book book = new BooksResponse.Book();
        book.setId(entity.getId());
        BookInfo volumeInfo = new BookInfo();
        volumeInfo.setTitle(entity.getTitle());

        String[] authors = entity.getAuthors().split("; ");
        volumeInfo.setAuthors(Arrays.asList(authors));
        volumeInfo.setDescription(entity.getDescription());
        volumeInfo.setLanguage(entity.getLanguage());
        volumeInfo.setPreviewLink(entity.getPreviewLink());
        volumeInfo.setInfoLink(entity.getInfoLink());
        volumeInfo.setCanonicalVolumeLink(entity.getCanonicalVolumeLink());

        BookInfo.ImageLink imageLinks = new BookInfo.ImageLink();


        imageLinks.setSmallThumbnail(entity.getSmallThumbnail());
        imageLinks.setThumbnail(entity.getThumbnail());
        volumeInfo.setImageLinks(imageLinks);
        book.setVolumeInfo(volumeInfo);
        return book;
    }
}
