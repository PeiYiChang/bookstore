package fcu.midterm.bookstore;

public class Book {
    private int bookImgId;
    private String bookName;
    private String bookState;

    public Book(int bookImgId, String bookName, String bookState) {
        this.bookImgId = bookImgId;
        this.bookName = bookName;
        this.bookState = bookState;
    }

    public int getBookImgId() {
        return bookImgId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookState() {
        return bookState;
    }
}