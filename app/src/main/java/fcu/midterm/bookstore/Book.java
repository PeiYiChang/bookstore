package fcu.midterm.bookstore;

import android.net.Uri;

public class Book {
    private String bookImgId;
    private int bookImg;
    private String bookName;
    private String bookState;
    private String bookAuthor;
    private String bookPublisher;
    private String bookIntroduce;
    public Book(){

    }

    public Book(int bookImg, String bookName, String bookState) {
        this.bookImg = bookImg;
        this.bookName = bookName;
        this.bookState = bookState;
    }
    public Book(String bookImgId, String bookName, String bookState) {
        this.bookImgId = bookImgId;
        this.bookName = bookName;
        this.bookState = bookState;
    }
    public Book(String bookImgId, String bookName, String bookState,String bookAuthor,String bookPublisher,String bookIntroduce) {
        this.bookImgId = bookImgId;
        this.bookName = bookName;
        this.bookState = bookState;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookIntroduce = bookIntroduce;
    }

    public String getBookIntroduce() {
        return bookIntroduce;
    }

    public void setBookIntroduce(String bookIntroduce) {
        this.bookIntroduce = bookIntroduce;
    }

    public int getBookImg() {
        return bookImg;
    }

    public void setBookImg(int bookImg) {
        this.bookImg = bookImg;
    }

    public String getBookImgId() {
        return bookImgId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookState() {
        return bookState;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public void setBookImgId(String bookImgId) {
        this.bookImgId = bookImgId;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setBookState(String bookState) {
        this.bookState = bookState;
    }
}