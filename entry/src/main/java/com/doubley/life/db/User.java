package com.doubley.life.db;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Column;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "user")                                      //当前类与user表关联
public class User extends OrmObject implements Serializable {  //OrmObject当前类的对象映射   Serializable 序列化表示，可进行I/O传输
    @PrimaryKey(autoGenerate = true)
    @Column(name = "id")
    private Integer id;
    @Column(name="user")
    private String user;
    @Column(name="password")
    private String password;
    @Column(name = "phone")
    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public User() {
        System.out.println("User的无参构造");
    }

    public User(String user, String password, String phone) {
        this.user = user;
        this.password = password;
        this.phone = phone;
    }
}
