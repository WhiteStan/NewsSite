package by.makouski.news.entity;

import java.util.List;

/**
 * Created by Stanislau_Makouski on 10/13/2016.
 */
public class SearchCriteria {
    private List<Tag> tags;
    private List<Author> authors;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchCriteria that = (SearchCriteria) o;

        if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;
        return !(authors != null ? !authors.equals(that.authors) : that.authors != null);

    }

    @Override
    public int hashCode() {
        int result = tags != null ? tags.hashCode() : 0;
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        return result;
    }
}
