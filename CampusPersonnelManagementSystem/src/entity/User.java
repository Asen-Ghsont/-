package entity;

import column.Column;

public class User {
    @Column(label = "用户名", isPrimeKey = true, max = 12, min = 2)
    private String name;

    @Column(label = "密码", max = 12, min = 6)
    private String password;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
