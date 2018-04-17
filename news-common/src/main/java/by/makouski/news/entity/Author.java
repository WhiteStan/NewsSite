package by.makouski.news.entity;

import java.io.Serializable;

/**
 * Created by Stanislau_Makouski on 10/12/2016.
 */
public class Author implements Serializable {
    private Integer id;
    private String name;
    private boolean isExpired;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (id != author.id) return false;
        if (isExpired != author.isExpired) return false;
        return !(name != null ? !name.equals(author.name) : author.name != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isExpired ? 1 : 0);
        return result;
    }
}
