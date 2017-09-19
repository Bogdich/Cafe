package com.bogdevich.cafe.command;

import com.bogdevich.cafe.command.impl.auth.LoginCommand;
import com.bogdevich.cafe.command.impl.auth.LogoutCommand;
import com.bogdevich.cafe.command.impl.auth.RegistrationCommand;
import com.bogdevich.cafe.command.impl.common.LanguageCommand;
import com.bogdevich.cafe.command.impl.redirect.LoginPageCommand;
import com.bogdevich.cafe.command.impl.redirect.MainPageCommand;
import com.bogdevich.cafe.command.impl.redirect.RegistrationPageCommand;
import com.bogdevich.cafe.constant.Constant;
import com.bogdevich.cafe.entity.type.Role;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;

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
