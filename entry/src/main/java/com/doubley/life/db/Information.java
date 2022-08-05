package com.doubley.life.db;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Column;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "information")
public class Information extends OrmObject implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "resource")
    private String resource;
    @Column(name="content")
    private String content;
    @Column(name="location")
    private String location;
    @Column(name = "feeling")
    private String feeling;
    @Column(name = "date")
    private Date date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public Date getDate() {
        return date;
    }

    //日期格式设置
    public String getDateStr(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Information{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", resource='" + resource + '\'' +
                ", content='" + content + '\'' +
                ", location='" + location + '\'' +
                ", feeling='" + feeling + '\'' +
                ", date=" + date +
                '}';
    }

    public Information() {
        System.out.println("Information的无参构造函数");
        this.date=new Date();
    }


    public Information( String title, String content, String location, String feeling) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.feeling = feeling;
        this.date = new Date();
    }

}
