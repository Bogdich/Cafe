package com.bogdevich.cafe.entity.bean;

import com.bogdevich.cafe.entity.Entity;
import com.bogdevich.cafe.entity.type.Role;

import java.math.BigDecimal;

public class User extends Entity{
    private String login;
    private String password;
    private Role role;
    private BigDecimal accountBalance;

    public User() {
    }

    public User(String login, String password, Role role, BigDecimal accountBalance) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.accountBalance = accountBalance;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (role != user.role) return false;
        return accountBalance != null ? accountBalance.equals(user.accountBalance) : user.accountBalance == null;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (accountBalance != null ? accountBalance.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", role=").append(role);
        sb.append(", accountBalance=").append(accountBalance);
        sb.append('}');
        return sb.toString();
    }
}
