package com.doubley.life.db;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Column;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "resource")
public class Resource extends OrmObject implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @Column(name = "id")
    private Integer id;
    @Column(name = "source")
    private String source;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", source='" + source + '\'' +
                '}';
    }

    public Resource() {
        this.source = null;
    }
    public Resource(String source) {
        this.source = source;
    }
}


