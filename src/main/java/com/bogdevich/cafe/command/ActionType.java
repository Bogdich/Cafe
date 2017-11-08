package com.bogdevich.cafe.command;

import com.bogdevich.cafe.command.constant.Constant;
import com.bogdevich.cafe.command.impl.ajax.*;
import com.bogdevich.cafe.command.impl.auth.LoginCommand;
import com.bogdevich.cafe.command.impl.auth.LogoutCommand;
import com.bogdevich.cafe.command.impl.auth.RegistrationCommand;
import com.bogdevich.cafe.command.impl.common.LanguageCommand;
import com.bogdevich.cafe.command.impl.redirect.LoginPageCommand;
import com.bogdevich.cafe.command.impl.redirect.MainPageCommand;
import com.bogdevich.cafe.command.impl.redirect.MenuPageCommand;
import com.bogdevich.cafe.command.impl.redirect.RegistrationPageCommand;
import com.bogdevich.cafe.entity.type.Role;

import java.util.Arrays;
import java.util.EnumSet;

public enum ActionType {
    LOGIN(
            Constant.ActionName.LOGIN,
            new LoginCommand(),
            Role.GUEST
    ),
    LOGOUT(
            Constant.ActionName.LOGOUT,
            new LogoutCommand(),
            Role.ADMIN,
            Role.CUSTOMER
    ),
    REGISTRATION(
            Constant.ActionName.REGISTRATION,
            new RegistrationCommand(),
            Role.GUEST
    ),
    TO_MAIN_PAGE(
            Constant.ActionName.MAIN_PAGE,
            new MainPageCommand(),
            Role.GUEST,
            Role.CUSTOMER,
            Role.ADMIN
    ),
    TO_MENU_PAGE(
            Constant.ActionName.MENU_PAGE,
            new MenuPageCommand(),
            Role.GUEST,
            Role.CUSTOMER,
            Role.ADMIN
    ),
    TO_LOGIN_PAGE(
            Constant.ActionName.LOGIN_PAGE,
            new LoginPageCommand(),
            Role.GUEST
    ),
    TO_REGISTRATION_PAGE(
            Constant.ActionName.REGISTRATION_PAGE,
            new RegistrationPageCommand(),
            Role.GUEST
    ),
    LANGUAGE(
            Constant.ActionName.CHANGE_LANG,
            new LanguageCommand(),
            Role.ADMIN,
            Role.CUSTOMER,
            Role.GUEST
    ),
    GET_DISHES_BY_CATEGORY(
            Constant.ActionName.GET_DISHES_BY_CATEGORY,
            new GetDishesCommand(),
            Role.ADMIN,
            Role.CUSTOMER,
            Role.GUEST
    ),
    ADD_DISH_TO_SHOPPING_CART(
            Constant.ActionName.ADD_DISH_TO_SC,
            new AddDishToShoppingCartCommand(),
            Role.ADMIN,
            Role.CUSTOMER,
            Role.GUEST
    ),
    GET_SHOPPING_CART(
            Constant.ActionName.GET_SHOPPING_CART,
            new GetShoppingCartCommand(),
            Role.ADMIN,
            Role.CUSTOMER,
            Role.GUEST
    ),
    CHANGE_DISH_QUANTITY(
            Constant.ActionName.CHANGE_QUANTITY,
            new ChangeDishQuantityInShoppingCartCommand(),
            Role.ADMIN,
            Role.CUSTOMER,
            Role.GUEST
    ),
    DELETE_DISH_FROM_SHOPPING_CART(
            Constant.ActionName.DELETE_DISH_FROM_SHOPPING_CART,
            new DeleteDishFromShoppingCartCommand(),
            Role.ADMIN,
            Role.CUSTOMER,
            Role.GUEST
    );

    private Command command;
    private String actionName;
    private EnumSet<Role> roles = EnumSet.allOf(Role.class);

    ActionType(String actionName, Command command, Role... roles) {
        this.actionName = actionName;
        this.command = command;
        if (null != roles) {
            this.roles.addAll(Arrays.asList(roles));
        }
    }
    
    public Command getCommand() {
        return command;
    }

    public boolean permitAction(Role role) {
        return roles.contains(role);
    }

    public static ActionType defineActionType(String actionName){
        for (ActionType type : ActionType.values()) {
            if (type.actionName.equalsIgnoreCase(actionName)) {
                return type;
            }
        }
        return null;
    }
}
