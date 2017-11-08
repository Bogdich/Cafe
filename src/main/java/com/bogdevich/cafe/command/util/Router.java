package com.bogdevich.cafe.command.util;

public class Router {
    public enum RouteType{
        FORWARD,
        REDIRECT
    }
    private String route;
    private RouteType type;

    public Router(String route, RouteType type) {
        this.route = route;
        this.type = type;
    }

    public String getRoute() {
        return route;
    }

    public RouteType getType() {
        return type;
    }
}
