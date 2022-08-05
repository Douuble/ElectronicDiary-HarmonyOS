package com.doubley.life.db;

import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;

@Database(entities = {User.class, Information.class, Resource.class},version = 1)
public abstract class UserStore extends OrmDatabase {
}
