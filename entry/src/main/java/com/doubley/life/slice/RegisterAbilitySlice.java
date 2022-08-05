package com.doubley.life.slice;

import com.doubley.life.ResourceTable;
import com.doubley.life.db.User;
import com.doubley.life.util.DataUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ToastDialog;

public class RegisterAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_register);
        //将用户信息加入数据库
        TextField user= (TextField) findComponentById(ResourceTable.Id_user);
        TextField password= (TextField) findComponentById(ResourceTable.Id_password);
        TextField phone= (TextField) findComponentById(ResourceTable.Id_phone);
        Button register= (Button) findComponentById(ResourceTable.Id_register);

        //ToastDialog布局设置
        DirectionalLayout layout = (DirectionalLayout) LayoutScatter.getInstance(this)
                .parse(ResourceTable.Layout_layout_toast_register, null, false);
        DirectionalLayout layout1 = (DirectionalLayout) LayoutScatter.getInstance(this)
                .parse(ResourceTable.Layout_layout_toast_fail_register, null, false);

        register.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                User user1=new User(user.getText(),password.getText(),phone.getText());
                boolean flag=DataUtil.addUser(user1);
                if(!flag){
                    new ToastDialog(getContext())
                            .setContentCustomComponent(layout1)
                            .setSize(DirectionalLayout.LayoutConfig.MATCH_CONTENT, DirectionalLayout.LayoutConfig.MATCH_CONTENT)
                            .setAlignment(LayoutAlignment.CENTER)
                            .show();
                }
                else {
                    new ToastDialog(getContext())
                            .setContentCustomComponent(layout)
                            .setSize(DirectionalLayout.LayoutConfig.MATCH_CONTENT, DirectionalLayout.LayoutConfig.MATCH_CONTENT)
                            .setAlignment(LayoutAlignment.CENTER)
                            .show();
                    //注册成功后进行跳转
                    Intent intent1 = new Intent();
                    Operation operation = new Intent.OperationBuilder()
                            .withDeviceId("")
                            .withBundleName("com.doubley.life")
                            .withAbilityName("com.doubley.life.LoginAbility")
                            .build();
                    intent1.setOperation(operation);
                    startAbility(intent1);
                }


            }
        });
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
