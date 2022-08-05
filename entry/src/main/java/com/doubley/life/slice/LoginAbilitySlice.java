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

public class LoginAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_login);

        //获取按钮和输入框内容
        Button login=(Button) findComponentById(ResourceTable.Id_button2);
        Button register= (Button) findComponentById(ResourceTable.Id_button1);
        TextField user= (TextField) findComponentById(ResourceTable.Id_userl);
        TextField password= (TextField) findComponentById(ResourceTable.Id_passwordl);
        //Toast相关设置
        DirectionalLayout layout = (DirectionalLayout) LayoutScatter.getInstance(this)
                .parse(ResourceTable.Layout_layout_toast_login, null, false);


        //登录操作
        login.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                //调用登录方法
                User user1=DataUtil.login(user.getText(),password.getText());
                if(user1==null){
                    new ToastDialog(getContext())
                            .setContentCustomComponent(layout)
                            .setSize(DirectionalLayout.LayoutConfig.MATCH_CONTENT, DirectionalLayout.LayoutConfig.MATCH_CONTENT)
                            .setAlignment(LayoutAlignment.CENTER)
                            .show();
                }

                else {
                    Intent intent1 = new Intent();
                    Operation operation = new Intent.OperationBuilder()
                            .withDeviceId("")
                            .withBundleName("com.doubley.life")
                            .withAbilityName("com.doubley.life.HomeAbility")
                            .build();
                    intent1.setOperation(operation);
                    startAbility(intent1);
                }
            }
        });

        //跳转到注册功能
        register.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent2= new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.doubley.life")
                        .withAbilityName("com.doubley.life.RegisterAbility")
                        .build();

                intent2.setOperation(operation);
                startAbility(intent2);
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
