package com.doubley.life.slice;

import com.doubley.life.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorGroup;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.animation.AnimatorScatter;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.Image;
import ohos.agp.components.element.ShapeElement;

public class MainAbilitySlice extends AbilitySlice {
    private int started = 1;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        //创建播放动画的组件
        Image image = new Image(getContext());
        image.setPixelMap(ResourceTable.Media_gift);
        image.setLayoutConfig(new ComponentContainer.LayoutConfig(300, 1000));
        DirectionalLayout layout = new DirectionalLayout(getContext());
        layout.setLayoutConfig(new ComponentContainer.LayoutConfig(
                ComponentContainer.LayoutConfig.MATCH_PARENT,
                ComponentContainer.LayoutConfig.MATCH_PARENT
        ));
        layout.addComponent(image);
        ShapeElement element = new ShapeElement(getContext(),
                ResourceTable.Graphic_background);
        layout.setBackground(element);
        super.setUIContent(layout);


        //创建动画组对象
        AnimatorGroup animatorGroup = new AnimatorGroup();
        //动画1 - 沿x轴从100滚动到800位置;动画2 - 沿y轴从100滚动到300位置
        AnimatorScatter scatter = AnimatorScatter.getInstance(getContext());
        Animator animator = scatter.parse(ResourceTable.Animation_animator_property);
        if (animator instanceof AnimatorProperty) {
            AnimatorProperty action1 = (AnimatorProperty) animator;
            action1.setTarget(image);
            action1
                    //x轴从100移动到500位置
                    //y轴从100滚动到300位置
                    .moveFromX(0).moveToX(600)
                    .moveFromY(0).moveToY(300)

                    //旋转720度
                    .rotate(700);
            //动画2 - 沿y轴从100移动到800位置
            AnimatorProperty action2 = new AnimatorProperty();
            action2.setTarget(image);
            action2
                    .moveFromY(300).moveToY(1500)

            //反弹力效果
                    .rotate(720)
                    .setCurveType(Animator.CurveType.BOUNCE)
            //透明度变化
                     .alphaFrom(1.0f).alpha(0.8f);

            //动画3 - 弹出礼物及app名称
             AnimatorProperty action3 = new AnimatorProperty();
            action3.setTarget(image);
            //移动到屏幕中央
            action3.moveFromX(600).moveToX(450)
                   .moveFromY(1500).moveToY(900)
            //放大效果
                   .scaleYFrom(1.0f).scaleY(3.0f)
                   .scaleXFrom(1.0f).scaleX(3.0f)
                   .alphaFrom(0.8f).alpha(1.0f);

            //先动画1后动画2
            animatorGroup.runSerially(action1, action2);
            //时长
            animatorGroup.setDuration(2000);
            action3.setDuration(4000);

            //点击图片开始/结束动画
            image.setClickedListener(new Component.ClickedListener() {
                @Override
                public void onClick(Component component) {
                    switch (started) {
                        case 1:
                        animatorGroup.start();
                        started = 2;
                        break;
                        case 2:
                        animatorGroup.stop();
                        image.setPixelMap(ResourceTable.Media_gift_open);
                        action3.start();
                        action3.end();
                        started=3;
                        break;
                        case 3:
                        Intent intent1 = new Intent();
                        // 通过Intent中的OperationBuilder类构造operation对象，指定设备标识（空串表示当前设备）、应用包名、Ability名称
                        Operation operation = new Intent.OperationBuilder()
                                .withDeviceId("")
                                .withBundleName("com.doubley.life")
                                .withAbilityName("com.doubley.life.LoginAbility")
                                .build();
                        // 把operation设置到intent中
                        intent1.setOperation(operation);
                        startAbility(intent1);
                    }

                }

            });
        }

    }
}

