package fr.enssat.gazouilli.model;

/**
 * Created by Adrien-ENSSAT on 19/01/2016.
 */
public class Comment {

    private String tweetId;
    private String author;
    private String text;

    public Comment(String tweetId, String author, String text) {
        this.tweetId = tweetId;
        this.author = author;
        this.text = text;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
