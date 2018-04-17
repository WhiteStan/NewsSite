package by.makouski.news.service.impl;

import by.makouski.news.dao.impl.AuthorDAO;
import by.makouski.news.dao.impl.CommentDAO;
import by.makouski.news.dao.impl.NewsDAO;
import by.makouski.news.dao.impl.TagDAO;
import by.makouski.news.entity.*;
import by.makouski.news.exception.DAOException;
import by.makouski.news.exception.ServiceException;
import by.makouski.news.service.INewsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Stanislau_Makouski on 10/18/2016.
 */
public class NewsService implements INewsService {
    private NewsDAO newsDAO;
    private AuthorDAO authorDAO;
    private TagDAO tagDAO;
    private CommentDAO commentDAO;

    @Transactional
    @Override
    public boolean add(News news) throws ServiceException {
        boolean result = false;
        if (news != null) {
            try {
                Integer newsId = newsDAO.save(news);
                for (Tag tag : news.getTags()) {
                    newsDAO.addTag(newsId, tag.getId());
                }
                for (Author author : news.getAuthors()) {
                    newsDAO.addAuthor(newsId, author.getId());
                }
            } catch (DAOException e) {
                throw new ServiceException(e);
            }

        }
        return result;
    }

    @Transactional
    @Override
    public boolean change(Integer id, News news) throws ServiceException {
        boolean result = false;
        if (news != null) {
            try {
                if (newsDAO.update(news)) {
                    List<Author> currentAuthorList = new ArrayList<>();
                    currentAuthorList = authorDAO.findAll();
                    List<Author> newAuthorList = news.getAuthors();
                    for (Author author : currentAuthorList) {
                        if (newAuthorList.contains(author))
                            authorDAO.update(author);
                    }
                    currentAuthorList.removeAll(newAuthorList);
                    for (Author author : currentAuthorList) {
                        authorDAO.delete(author.getId());
                    }
                    newAuthorList.removeAll(currentAuthorList);
                    for (Author author : newAuthorList) {
                        authorDAO.add(author);
                    }
                    List<Tag> currentTag = new ArrayList<>();
                    currentTag = tagDAO.findAll();
                    List<Tag> newTagList = news.getTags();
                    for (Tag tag : currentTag) {
                        if (newTagList.contains(tag))
                            tagDAO.update(tag);
                    }
                    currentTag.removeAll(newTagList);
                    for (Tag tag : currentTag) {
                        tagDAO.delete(tag.getId());
                    }
                    newTagList.removeAll(currentTag);
                    for (Tag tag : newTagList) {
                        tagDAO.add(tag);
                    }
                }
            } catch (DAOException e) {
                throw new ServiceException(e);
            }

        }
        return result;
    }

    @Transactional
    @Override
    public boolean delete(Integer id) throws ServiceException {
        boolean result = true;
        try {
            List<Comment> comments =
                    new ArrayList(commentDAO.findByNewsId(id));
            for (Comment comment : comments) {
                commentDAO.delete(comment.getId());
            }
            if (!newsDAO.delete(id)) {
                result = false;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return result;
    }

    @Override
    public boolean deleteNews(Collection<Integer> ids) throws ServiceException {
        boolean result = true;
        for (Integer id : ids) {
            try {
                List<Comment> comments = commentDAO.findByNewsId(id);
                if(comments != null) {
                    for (Comment comment : comments) {
                        commentDAO.delete(comment.getId());
                    }
                }
                if (!newsDAO.delete(id)) {
                    result = false;
                    break;
                }

            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        }
        return result;
    }

    @Override
    public News getNews(Integer id) throws ServiceException {
        News news;
        try {
            news = newsDAO.findById(id);
            news.setAuthors(authorDAO.findByNewsId(news.getId()));
            news.setTags(tagDAO.findByNewsId(news.getId()));
            news.setComments(commentDAO.findByNewsId(news.getId()));

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return news;
    }

    @Override
    public List<News> getAllNews() throws ServiceException {
        List<News> newsList = new ArrayList<>();
        try {
            newsList = newsDAO.findAll();
            for(News news : newsList){
                news.setAuthors(authorDAO.findByNewsId(news.getId()));
                news.setTags(tagDAO.findByNewsId(news.getId()));
                news.setComments(commentDAO.findByNewsId(news.getId()));
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return newsList;
    }

    @Override
    public List<News> findAllNewsBySearchCriteria(SearchCriteria searchCriteria) throws ServiceException {
        ArrayList<News> result = new ArrayList<>();
        try {
            ArrayList<News> newsList = new ArrayList(newsDAO.findAllNewsBySearchCriteria(searchCriteria));
            for (News news : newsList) {

                news.setAuthors(new ArrayList<Author>(authorDAO.findByNewsId(news.getId())));
                news.setComments(new ArrayList<Comment>(commentDAO.findByNewsId(news.getId())));
                news.setTags(new ArrayList<Tag>(tagDAO.findByNewsId(news.getId())));
                newsList.add(news);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return result;
    }

    public void setNewsDAO(NewsDAO newsDAO) {
        this.newsDAO = newsDAO;
    }

    public void setAuthorDAO(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    public void setCommentDAO(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }
}
