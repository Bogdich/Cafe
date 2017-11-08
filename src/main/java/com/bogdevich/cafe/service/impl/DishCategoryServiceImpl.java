package com.bogdevich.cafe.service.impl;

import com.bogdevich.cafe.dao.factory.DAOFactory;
import com.bogdevich.cafe.entity.type.Category;
import com.bogdevich.cafe.service.DishCategoryService;
import com.bogdevich.cafe.service.exception.InvalidDataException;
import com.bogdevich.cafe.service.exception.ServiceException;
import com.bogdevich.cafe.transaction.ITransactionManager;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DishCategoryServiceImpl implements DishCategoryService {

    private final ITransactionManager transactionManager;
    private final DAOFactory daoFactory;

    public DishCategoryServiceImpl(ITransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.daoFactory = DAOFactory.getInstance();
    }

    @Override
    public int defineCategoryID(String categoryName) throws ServiceException, InvalidDataException {
        Category category = Optional
                .ofNullable(Category.defineCategory(categoryName))
                .orElseThrow(() -> new InvalidDataException("Invalid category name."));
        return category.getId();
    }

    @Override
    public List<Category> findCategories() {
        return Arrays.asList(Category.values());
    }
}
