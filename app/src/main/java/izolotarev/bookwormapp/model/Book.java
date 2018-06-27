package izolotarev.bookwormapp.model;

import java.util.Date;

/**
 * Created by Игорь on 27.05.2018.
 */

public class Book {
    private int bookId;
    private String title;
    private String author;
    private String releaseDate;
    private int categoryId;

    public Book(int bookId, String title, String author, String releaseDate, int categoryId){
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.categoryId = categoryId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
