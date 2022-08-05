package com.doubley.life.slice;

import com.doubley.life.ResourceTable;
import com.doubley.life.db.Information;
import com.doubley.life.db.Resource;
import com.doubley.life.util.DataUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.agp.components.surfaceprovider.SurfaceProvider;
import ohos.agp.graphics.Surface;
import ohos.agp.graphics.SurfaceOps;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.common.Source;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.player.Player;

import java.io.*;

public class InformationAbilitySlice extends AbilitySlice {
    //视频开发相关
    private Context context = this;
    private HiLogLabel TAG = new HiLogLabel(HiLog.DEBUG, 0xD000500, "InformationAblityTest");
    private SurfaceProvider surfaceProvider;
    private DirectionalLayout directionalLayout;
    private Surface surface;


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_information);
        //获取ID
        Integer id=getAbility().getIntent().getSerializableParam("id");
        //通过Id获取information的信息及source的信息
        Information information= DataUtil.getOne(id);
        Resource resource=DataUtil.getOnes(id);
        //获取显示内容的控件
        Text title= (Text) findComponentById(ResourceTable.Id_t_title);
        Text date= (Text) findComponentById(ResourceTable.Id_date);
        Text content= (Text) findComponentById(ResourceTable.Id_content);
        Text location= (Text) findComponentById(ResourceTable.Id_location);
        Button play= (Button) findComponentById(ResourceTable.Id_bnt_play);
        Image photo= (Image) findComponentById(ResourceTable.Id_photo);
        directionalLayout= (DirectionalLayout) findComponentById(ResourceTable.Id_playposition);
        if( resource==null){
            photo.setVisibility(2);
            play.setVisibility(2);
            directionalLayout.setVisibility(2);
        }
        else{
            if(resource.toString().contains("jpg")){
            play.setVisibility(2);
            directionalLayout.setVisibility(2);
            PixelMap sources=getPixelMapFromSource(resource.getSource());
            photo.setPixelMap(sources);
            }
            else{
                photo.setVisibility(2);
                addSurfaceProvider();
                play.setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        Player player = new Player(context);
                        File file = new File(resource.getSource()); // 根据实际情况设置文件路径
                        FileInputStream in = null;
                        try {
                            in = new FileInputStream(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        FileDescriptor fd = null; // 从输入流获取FD对象
                        try {
                            fd = in.getFD();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Source source = new Source(fd);
                        player.setSource(source);
                        player.setVideoSurface(surface);
                        player.prepare();
                        player.play();
//                        player.stop();
                    }
                });
            }
        }
        //设置控件显示内容
        if(information.getTitle().equals(""))
            title.setVisibility(2);
        else
            title.setText(information.getTitle());
        date.setText(information.getDateStr());
        if(information.getContent().equals(""))
            content.setVisibility(2);
        else
            content.setText(information.getContent());
        if(information.getLocation().equals(""))
            location.setVisibility(2);
        else
            location.setText(information.getLocation());


        //删除记录
        Button delete= (Button) findComponentById(ResourceTable.Id_bnt_delete);
        delete.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                //根据ID值删除
                DataUtil.deleInformation(id);
                DataUtil.deleSource(id);
                getAbility().terminateAbility();
            }
        });

        //修改记录
//        Button modify= (Button) findComponentById(ResourceTable.Id_btn_modify);
//        modify.setClickedListener(new Component.ClickedListener() {
//            @Override
//            public void onClick(Component component) {
//                Intent intent2= new Intent();
//                Operation operation = new Intent.OperationBuilder()
//                        .withDeviceId("")
//                        .withBundleName("com.doubley.life")
//                        .withAbilityName("com.doubley.life.ModifyAbility")
//                        .build();
//                intent2.setOperation(operation);
//                startAbility(intent2);
//
//                intent2.setParam("id",id);
//                intent2.setOperation(operation);
//            }
//        });


    }

    //添加播放页面
    private void addSurfaceProvider() {
        surfaceProvider = new SurfaceProvider(this);

        if (surfaceProvider.getSurfaceOps().isPresent()) {
            surfaceProvider.getSurfaceOps().get().addCallback(new SurfaceCallBack());
            surfaceProvider.pinToZTop(true);
        }
        directionalLayout= (DirectionalLayout) findComponentById(ResourceTable.Id_playposition);
        directionalLayout.addComponent(surfaceProvider);
    }

    class SurfaceCallBack implements SurfaceOps.Callback {
        @Override
        public void surfaceCreated(SurfaceOps callbackSurfaceOps) {
            if (surfaceProvider.getSurfaceOps().isPresent()) {
                surface = surfaceProvider.getSurfaceOps().get().getSurface();
            }
        }

        @Override
        public void surfaceChanged(SurfaceOps callbackSurfaceOps, int format, int width, int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceOps callbackSurfaceOps) {

        }
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
