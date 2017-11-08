package com.bogdevich.cafe.service;

import com.bogdevich.cafe.entity.type.Category;
import com.bogdevich.cafe.service.exception.InvalidDataException;
import com.bogdevich.cafe.service.exception.ServiceException;

import java.util.List;

public interface DishCategoryService {
    int defineCategoryID(String category) throws ServiceException, InvalidDataException;
    List<Category> findCategories() throws ServiceException;
}
