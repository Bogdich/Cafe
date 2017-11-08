package com.bogdevich.cafe.command.impl.redirect;

import com.bogdevich.cafe.command.Command;
import com.bogdevich.cafe.command.constant.Constant;
import com.bogdevich.cafe.command.constant.JavaServerPagePath;
import com.bogdevich.cafe.entity.type.Category;
import com.bogdevich.cafe.service.DishCategoryService;
import com.bogdevich.cafe.service.exception.ServiceException;
import com.bogdevich.cafe.service.factory.ServiceFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MenuPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final String BUNDLE_NAME = "localization/category";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private DishCategoryService dishCategoryService = serviceFactory.getDishCategoryService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String language = (String) session.getAttribute(Constant.AttributeName.LANGUAGE);
            ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.forLanguageTag(language));
            Map<Category, String> localeNames = dishCategoryService
                    .findCategories().stream()
                    .collect(Collectors.toMap(
                            Function.identity(),
                            category -> resourceBundle.getString(category.getKey())
                    )).entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            request.setAttribute(Constant.AttributeName.CATEGORY_LIST, localeNames);
            request.getRequestDispatcher(JavaServerPagePath.MENU_PAGE).forward(request, response);
        } catch (ServiceException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
