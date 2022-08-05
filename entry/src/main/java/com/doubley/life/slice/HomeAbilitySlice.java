package com.doubley.life.slice;

import com.doubley.life.db.Information;
import com.doubley.life.provider.HomeProvider;
import com.doubley.life.ResourceTable;
import com.doubley.life.util.DataUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;

import java.util.List;

public class HomeAbilitySlice extends AbilitySlice {
    //定义一个listcontainer对象
    ListContainer myListContainer;
    //定义显示Information内容的集合
    List<Information> infList;
    //定义一个适配器
    HomeProvider homeProvider;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_home);
        initItem();

    }

    public void initItem(){
        //得到布局中的listcontainer控件
        myListContainer= (ListContainer) findComponentById(ResourceTable.Id_list_container);
        //获取两个按钮进行页面跳转
        Button btnAddinformation=(Button)findComponentById(ResourceTable.Id_btn_addnote);
        Button btnExite= (Button) findComponentById(ResourceTable.Id_btn_exit);

        //创建适配器
        homeProvider=new HomeProvider(infList,this);
        //适配器关联ListContainer
        myListContainer.setItemProvider(homeProvider);

        //给listcontainer添加元素监听事件
        myListContainer.setItemClickedListener(new ListContainer.ItemClickedListener() {
            @Override
            public void onItemClicked(ListContainer listContainer, Component component, int i, long l) {
                Information information=homeProvider.getItem(i);
                Intent intent1 = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.doubley.life")
                        .withAbilityName("com.doubley.life.InformationAbility")
                        .build();

                intent1.setParam("id",information.getId());
                intent1.setOperation(operation);
                startAbility(intent1);
            }
        });

       btnAddinformation.setClickedListener(new Component.ClickedListener() {
           @Override
           public void onClick(Component component) {
               Intent intent1= new Intent();
               Operation operation = new Intent.OperationBuilder()
                       .withDeviceId("")
                       .withBundleName("com.doubley.life")
                       .withAbilityName("com.doubley.life.AddInformationAbility")
                       .build();
               intent1.setOperation(operation);
               startAbility(intent1);
           }
       });

       btnExite.setClickedListener(new Component.ClickedListener() {
           @Override
           public void onClick(Component component) {
               Intent intent2= new Intent();
               Operation operation = new Intent.OperationBuilder()
                       .withDeviceId("")
                       .withBundleName("com.doubley.life")
                       .withAbilityName("com.doubley.life.HomeAbility")
                       .build();
               intent2.setOperation(operation);
               startAbility(intent2);
           }
       });
    }



    @Override
    public void onActive() {
        super.onActive();
        //从数据库中得到Information的所有信息
        this.infList= DataUtil.getAll();
        homeProvider.setInformationList(this.infList);
        myListContainer.setItemProvider(homeProvider);
        homeProvider.notifyDataChanged();
        if(infList==null||infList.size()==0){
            myListContainer.setVisibility(Component.HIDE);
        }else{
            myListContainer.setVisibility(Component.VISIBLE);
        }
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
        infList.clear();
        homeProvider.setInformationList(infList);
        myListContainer.setItemProvider(homeProvider);
        homeProvider.notifyDataChanged();
    }
    
}
