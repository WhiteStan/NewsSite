package by.makouski.news.service;

import by.makouski.news.entity.News;
import by.makouski.news.entity.SearchCriteria;
import by.makouski.news.exception.ServiceException;

import java.util.Collection;
import java.util.List;

/**
 * Created by Stanislau_Makouski on 10/13/2016.
 */
public interface INewsService {
    boolean add(News news) throws ServiceException;
    boolean change(Integer id, News news) throws ServiceException;
    boolean delete(Integer id) throws ServiceException;
    boolean deleteNews(Collection<Integer> ids) throws ServiceException;
    News getNews(Integer id) throws ServiceException;
    List<News> getAllNews() throws ServiceException;
    List<News> findAllNewsBySearchCriteria(SearchCriteria searchCriteria) throws ServiceException;
}
