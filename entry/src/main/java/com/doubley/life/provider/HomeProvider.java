package com.doubley.life.provider;


import com.doubley.life.ResourceTable;
import com.doubley.life.db.Information;
import com.doubley.life.db.Resource;
import com.doubley.life.util.DataUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;

import java.util.ArrayList;
import java.util.List;

public class HomeProvider extends RecycleItemProvider {
    //要显示的内容合集
    private List<Information> infList=new ArrayList<>();
    //定义一个布局适配器
    private LayoutScatter myLayout;
    //定义AbilitySlice
    private AbilitySlice myslice;

    //构造器来关联集合数据和AbilitySlice
    public HomeProvider(){

    }
    public HomeProvider(List<Information> list, AbilitySlice myslice){
        this.infList=list;
        this.myslice=myslice;
        //关联适配器
        this.myLayout=LayoutScatter.getInstance(myslice);
    }

    @Override
    public int getCount() {
        if(infList==null)
            return 0;
        //返回infList集合的元素个数
        return infList.size();
    }

    //获取到集合中的元素
    //i表示集合中元素的下标，因为list集合是一个有序集合，所以可以根据下标找到对应元素
    @Override
    public Information getItem(int i) {
        //调用get方法得到集合中的元素
        return infList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        //根据下标i得到当前的元素
        Information information=getItem(i);
        //通过id获得Source的信息
//        int count=i+1;
        Resource isource= DataUtil.getOnes(i+1);
//        count++;
        //判断容器中的component是否为空，不空则显示
        if(component!=null){
            return component;
        }
        //新建Component对象
        Component newCom=
                myLayout.parse(ResourceTable.Layout_ability_home_item,null,
                false);
        //获取布局中需要显示信息的控件
        Image feeling= (Image) newCom.findComponentById(ResourceTable.Id_t_feeling);
        Text title= (Text) newCom.findComponentById(ResourceTable.Id_t_title);
        Text time=(Text) newCom.findComponentById(ResourceTable.Id_list_text_time);
        Text content= (Text) newCom.findComponentById(ResourceTable.Id_t_content);
        Text location=(Text) newCom.findComponentById(ResourceTable.Id_t_location);
        Text source=(Text)newCom.findComponentById(ResourceTable.Id_source);


        //将对象中的数据添加到控件中
        switch (information.getFeeling()){
            case "1":
                feeling.setPixelMap(ResourceTable.Media_laugh);
                break;
            case "2":
                feeling.setPixelMap(ResourceTable.Media_smile);
                break;
            case "3":
                feeling.setPixelMap(ResourceTable.Media_indifferent);
                break;
            case "4":
                feeling.setPixelMap(ResourceTable.Media_upset);
                break;
        }
        title.setText(information.getTitle());
        time.setText(information.getDateStr());
        location.setText(information.getLocation());
        content.setText(information.getContent());

        if(isource==null){
            source.setText("");
        }

        return newCom;
    }
        //添加一个方法，当调用此方法的时候，可以将集合传递给当前类的属性
        public void setInformationList (List<Information> list){
            //调用此方法时会将list实参的值传递给当前类的infoList
            this.infList = list ;
    }
}
