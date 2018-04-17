package by.makouski.news.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Stanislau_Makouski on 10/12/2016.
 */
public class Comment implements Serializable {
    private Integer id;
    private String content;
    private Integer news_id;
    private Timestamp publishDate;
    private Integer user_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getNews_id() {
        return news_id;
    }

    public void setNews_id(Integer news_id) {
        this.news_id = news_id;
    }

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (id != comment.id) return false;
        if (news_id != comment.news_id) return false;
        if (user_id != comment.user_id) return false;
        if (!content.equals(comment.content)) return false;
        return publishDate.equals(comment.publishDate);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + content.hashCode();
        result = 31 * result + news_id;
        result = 31 * result + publishDate.hashCode();
        result = 31 * result + user_id;
        return result;
    }
}
