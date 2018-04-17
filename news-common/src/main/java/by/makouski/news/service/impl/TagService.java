package by.makouski.news.service.impl;

import by.makouski.news.dao.impl.TagDAO;
import by.makouski.news.entity.Tag;
import by.makouski.news.exception.DAOException;
import by.makouski.news.exception.ServiceException;
import by.makouski.news.service.ITagService;

import java.util.Collection;

/**
 * Created by Stanislau_Makouski on 10/18/2016.
 */
public class TagService implements ITagService {
    private TagDAO tagDAO;

    @Override
    public boolean add(Tag tag) throws ServiceException {
        boolean result = false;
        if (tag != null) {
            try {
                if (tagDAO.add(tag)) {
                    result = true;
                }
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        }
        return result;
    }

    @Override
    public boolean delete(Integer id) throws ServiceException {
        boolean result;
        try {
            result = tagDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean change(Integer id, String title) throws ServiceException {
        boolean result = false;
            try {
                Tag tag = new Tag();
                tag.setId(id);
                tag.setTitle(title);
                if (tagDAO.update(tag)) {
                    result = true;
                }
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        return result;
    }

    @Override
    public Collection<Tag> getAllTags() throws ServiceException {
        Collection<Tag> tags;
        try {
            tags = tagDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return tags;
    }

    public void setTagDAO(TagDAO tagDAO){
        this.tagDAO = tagDAO;
    }
}
