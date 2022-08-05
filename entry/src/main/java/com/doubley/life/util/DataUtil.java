package com.doubley.life.util;

import com.doubley.life.db.Information;
import com.doubley.life.db.Resource;
import com.doubley.life.db.User;
import com.doubley.life.db.UserStore;
import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {
    private static OrmContext ormContext;
    public static void onInitial(Context context){

        DatabaseHelper helper=new DatabaseHelper(context);
        ormContext= helper.getOrmContext("UserStore","UserStore.db", UserStore.class);
        //预设信息方便登录
        User tuser=new User("1","1","1");
        addUser(tuser);
//        Information tinformation=new Information("1","1","1","1","1");
//        addInformation(tinformation);
    }

    /**
     * 实现用户登录功能
     * @param user
     * @param password
     * @return User
     */
    public static User login(String user,String password){
        //创建一个条件对象：保证当前参数的类是数据库中的实体类----extends OrmObject
        OrmPredicates ormPredicates=ormContext.where(User.class);
        //第一个参数是列名称，第二个参数是输入值
        ormPredicates.equalTo("user",user);
        ormPredicates.equalTo("password",password);
        //调用query方法
        List<User> users=ormContext.query(ormPredicates);
        //有可能输入错误查无此人，要判空
        if(users==null||users.size()==0)
            return null;
        return users.get(0);
    }

    /**
     * 实现注册信息功能
     * @param user
     * @return
     */
    public static boolean addUser(User user) {
        OrmPredicates ormPredicates=ormContext.where(User.class);
        //第一个参数是列名称，第二个参数是输入值
        ormPredicates.equalTo("user", user.getUser());
        List<User> users2=ormContext.query(ormPredicates);
        if(users2==null||users2.size()==0) {
            ormContext.insert(user);
            return ormContext.flush();
        }
        else{
            return false;
        }
    }

    /**
     * 实现内容存储到数据库
     * @param information
     * @return
     */
    public static boolean addInformation(Information information){
        ormContext.insert(information);
        return ormContext.flush();
    }
    /**
     * 获得所有Information对象的信息
     * @return
     */
    public static List<Information> getAll(){
        OrmPredicates query1 = ormContext.where(Information.class);
        List<Information> information = ormContext.query(query1);
        if (information == null) {
            return new ArrayList<>();
        }
        return information;
    }
    /**
     * 获得某个Information对象的信息
     * @param id
     * @return
     */
    public static Information getOne(Integer id){
        OrmPredicates ormPredicates = ormContext.where(Information.class);
        ormPredicates.equalTo("id",id);
        List<Information> information = ormContext.query(ormPredicates);
        if (information == null||information.size()==0) {
            return null;
        }
        return information.get(0);
    }
    /**
     * 删除信息
     * @param id
     */
    public static void deleInformation(Integer id) {
        Information information=getOne(id);
        ormContext.delete(information);
        ormContext.flush();
    }

    /**
     * 添加存储路径
     * @param source
     * @return
     */
    public static boolean addSource(Resource source){
        ormContext.insert(source);
        return ormContext.flush();
    }

    /**
     * 通过id获得Resource的信息
     * @param id
     * @return
     */
    public static Resource getOnes(Integer id){
        OrmPredicates ormPredicates = ormContext.where(Resource.class);
        ormPredicates.equalTo("id",id);
        List<Resource> source = ormContext.query(ormPredicates);
        if (source == null||source.size()==0) {
            return null;
        }
        return source.get(0);
    }

    /**
     * 通过id删除Resource的信息
     * @param id
     */
    public static void deleSource(Integer id) {
        Resource source=getOnes(id);
        ormContext.delete(source);
        ormContext.flush();
    }
}
