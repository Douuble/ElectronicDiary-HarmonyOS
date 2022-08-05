package com.doubley.life.slice;

import com.doubley.life.ResourceTable;
import com.doubley.life.db.Information;
import com.doubley.life.db.Resource;
import com.doubley.life.util.DataUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.components.surfaceprovider.SurfaceProvider;
import ohos.agp.graphics.Surface;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;

public class ModifyAbilitySlice extends AbilitySlice {
    private Context context = this;
    private HiLogLabel TAG = new HiLogLabel(HiLog.DEBUG, 0xD000500, "InformationAblityTest");
    private SurfaceProvider surfaceProvider;
    private DirectionalLayout directionalLayout;
    private Surface surface;


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_modify);
        //获取ID
        Integer id=getAbility().getIntent().getSerializableParam("id");
        //通过Id获取information的信息及source的信息
        Information information= DataUtil.getOne(id);
        Resource resource=DataUtil.getOnes(id);
        //获取显示内容的控件
        TextField title= (TextField) findComponentById(ResourceTable.Id_t_title);
        TextField content= (TextField) findComponentById(ResourceTable.Id_content);
        Button delete= (Button) findComponentById(ResourceTable.Id_bnt_play);
        Button save= (Button) findComponentById(ResourceTable.Id_btn_save);
        Image photo= (Image) findComponentById(ResourceTable.Id_photo);
        directionalLayout= (DirectionalLayout) findComponentById(ResourceTable.Id_playposition);
        if( resource==null){
            photo.setVisibility(2);
            delete.setVisibility(2);
            directionalLayout.setVisibility(2);
        }
        else{
            if(resource.toString().contains("jpg")){
                directionalLayout.setVisibility(2);
                PixelMap sources=getPixelMapFromSource(resource.getSource());
                photo.setPixelMap(sources);
            }
            else{
                photo.setVisibility(2);
            }
            delete.setClickedListener(new Component.ClickedListener() {
                @Override
                public void onClick(Component component) {
                    resource.setSource(null);
                }
            });
        }
        //设置控件显示内容
        if(information.getTitle().equals(""))
            title.setVisibility(2);
        else
            title.setText(information.getTitle());
        if(information.getContent().equals(""))
            content.setVisibility(2);
        else
            content.setText(information.getContent());

        title.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                information.setTitle(title.getText());
            }
        });

        content.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                information.setContent(content.getText());
            }
        });

        save.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                getAbility().terminateAbility();
            }
        });

    }

    /**
     * 将图内存图片转化为Pixelmap对象显示
     * @param source
     * @return
     */
    private PixelMap getPixelMapFromSource(String source){
        String inputStream = null;
        // 创建图像数据源ImageSource对象
        inputStream = source;
        ImageSource.SourceOptions srcOpts = new ImageSource.SourceOptions();
        srcOpts.formatHint = "image/jpg";
        ImageSource imageSource = ImageSource.create(inputStream, srcOpts);
        // 普通解码
        return imageSource.createPixelmap(null);
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
