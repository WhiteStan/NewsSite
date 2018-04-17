package by.makouski.news.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Created by Stanislau_Makouski on 10/12/2016.
 */
public class News implements Serializable{
    private int id;
    private String mainTitle;
    private String shortTitle;
    private String content;
    private String photo;
    private Date publishDate;
    private List<Author> authors;
    private List<Tag> tags;
    private List<Comment> comments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;

        if (id != news.id) return false;
        if (mainTitle != null ? !mainTitle.equals(news.mainTitle) : news.mainTitle != null) return false;
        if (shortTitle != null ? !shortTitle.equals(news.shortTitle) : news.shortTitle != null) return false;
        if (content != null ? !content.equals(news.content) : news.content != null) return false;
        if (photo != null ? !photo.equals(news.photo) : news.photo != null) return false;
        if (publishDate != null ? !publishDate.equals(news.publishDate) : news.publishDate != null) return false;
        if (authors != null ? !authors.equals(news.authors) : news.authors != null) return false;
        if (tags != null ? !tags.equals(news.tags) : news.tags != null) return false;
        return !(comments != null ? !comments.equals(news.comments) : news.comments != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (mainTitle != null ? mainTitle.hashCode() : 0);
        result = 31 * result + (shortTitle != null ? shortTitle.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        return result;
    }
}
