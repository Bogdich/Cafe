package com.bogdevich.cafe.entity.wrapper;

import com.bogdevich.cafe.entity.bean.User;
import com.bogdevich.cafe.entity.bean.UserInfo;

public class UserWrapper {
    private User user;
    private UserInfo userInfo;

    public UserWrapper() {
    }

    public UserWrapper(User user, UserInfo userInfo) {
        this.user = user;
        this.userInfo = userInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserWrapper that = (UserWrapper) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return userInfo != null ? userInfo.equals(that.userInfo) : that.userInfo == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (userInfo != null ? userInfo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserWrapper{");
        sb.append("user=").append(user);
        sb.append(", userInfo=").append(userInfo);
        sb.append('}');
        return sb.toString();
    }
}
