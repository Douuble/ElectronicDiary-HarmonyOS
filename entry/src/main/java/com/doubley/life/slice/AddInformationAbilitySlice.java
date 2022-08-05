package com.doubley.life.slice;

import com.doubley.life.AddInformationAbility;
import com.doubley.life.ResourceTable;
import com.doubley.life.location.LocationBean;
import com.doubley.life.util.DataUtil;
import com.doubley.life.db.Information;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;
import ohos.bundle.IBundleManager;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.location.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;


public class AddInformationAbilitySlice extends AbilitySlice {

    private static final String TAG = AddInformationAbility.class.getSimpleName();

    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD000F00, TAG);

    private static final int EVENT_ID = 0x12;

    private static final String PERM_LOCATION = "ohos.permission.LOCATION";

   private final LocatorResult locatorResult = new LocatorResult();

    private Context context;

    private Locator locator;

    private GeoConvert geoConvert;

    private List<GeoAddress> gaList;

    private LocationBean locationDetails;

    private Text location;

    private String feeling;

    private int flag=0;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_add_information);
        //获取输入框内容
        TextField title= (TextField) findComponentById(ResourceTable.Id_tf_title);
        TextField content= (TextField) findComponentById(ResourceTable.Id_tf_content);
        //获取保存和返回按钮
        Button save= (Button) findComponentById(ResourceTable.Id_btn_save);
        Button back= (Button) findComponentById(ResourceTable.Id_btn_exit);

        //获取相关操作控件
        Image photo= (Image) findComponentById(ResourceTable.Id_tf_photo);
        Image record= (Image) findComponentById(ResourceTable.Id_tf_record);
        Image storage= (Image) findComponentById(ResourceTable.Id_tf_storage);
        Image microphone= (Image) findComponentById(ResourceTable.Id_tf_microphone);
        Image upset= (Image) findComponentById(ResourceTable.Id_upset);
        Image indifferent= (Image) findComponentById(ResourceTable.Id_indifferent);
        Image smile= (Image) findComponentById(ResourceTable.Id_smile);
        Image laugh= (Image) findComponentById(ResourceTable.Id_laugh);
        location= (Text) findComponentById(ResourceTable.Id_location_display);
        feeling=" ";


        //点击选择心情
        laugh.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                feeling="1";
                if(flag==0){
                laugh.setPixelMap(ResourceTable.Media_laugh);
                flag=1;}
                else {laugh.setPixelMap(ResourceTable.Media_ori_laugh);
                flag=0;}
            }
        });
        smile.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                feeling="2";
                if(flag==0){
                smile.setPixelMap(ResourceTable.Media_smile);
                flag=1;}
                else {smile.setPixelMap(ResourceTable.Media_ori_smile);
                flag=0;}
            }
        });
        indifferent.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                feeling="3";
                if(flag==0){
                    indifferent.setPixelMap(ResourceTable.Media_indifferent);
                    flag=1;}
                else {indifferent.setPixelMap(ResourceTable.Media_ori_indifferent);
                flag=0;}
            }
        });
        upset.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                feeling="4";
                if(flag==0){
                    upset.setPixelMap(ResourceTable.Media_upset);
                    flag=1;}
                else{
                     upset.setPixelMap(ResourceTable.Media_ori_upset);
                     flag=0;
                }
            }
        });

        //返回主菜单
        back.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent1= new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.doubley.life")
                        .withAbilityName("com.doubley.life.HomeAbility")
                        .build();
                intent1.setOperation(operation);
                startAbility(intent1);
            }
        });

        //拍照
        photo.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent2= new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.doubley.life")
                        .withAbilityName("com.doubley.life.PhotoAbility")
                        .build();
                intent2.setOperation(operation);
                startAbility(intent2);
            }
        });
        //录制视频
        record.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent3= new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.doubley.life")
                        .withAbilityName("com.doubley.life.RecordAbility")
                        .build();
                intent3.setOperation(operation);
                startAbility(intent3);
            }
        });

        //获取地理位置
        initComponents();
        register(this);

        //存储并回显到Home页面
        save.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {

                //调用添加Information的方法
                DataUtil.addInformation(new Information(title.getText(),content.getText(),location.getText(),feeling));
                //回调显示到Home页面
                getAbility().terminateAbility();

            }
        });
    }

    private void initComponents() {
        Component startLocatingButton = findComponentById(ResourceTable.Id_location);
        startLocatingButton.setClickedListener(component -> registerLocationEvent());
    }

    private void notifyLocationChange(LocationBean locationDetails) {
        update(locationDetails);
    }

    private void update(LocationBean locationDetails) {
        location.setText(locationDetails.getCountryName() +"," + locationDetails.getAdministrative() +","
                + locationDetails.getLocality() +"," + locationDetails.getRoadName());
    }

    private final EventHandler handler = new EventHandler(EventRunner.current()) {
        @Override
        protected void processEvent(InnerEvent event) {
            if (event.eventId == EVENT_ID) {
                notifyLocationChange(locationDetails);
            }
        }
    };

    private void register(Context ability) {
        context = ability;

    }

    private void registerLocationEvent() {
        requestPermission();
        if (hasPermissionGranted()) {
            int timeInterval = 0;
            int distanceInterval = 0;
            locator = new Locator(context);
            RequestParam requestParam = new RequestParam(RequestParam.PRIORITY_ACCURACY, timeInterval, distanceInterval);
            locator.startLocating(requestParam, locatorResult);
        }
    }

    private void unregisterLocationEvent() {
        if (locator != null) {
            locator.stopLocating(locatorResult);
        }
    }

    private boolean hasPermissionGranted() {
        return context.verifySelfPermission(PERM_LOCATION) == IBundleManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (context.verifySelfPermission(PERM_LOCATION) != IBundleManager.PERMISSION_GRANTED) {
            context.requestPermissionsFromUser(new String[] {PERM_LOCATION}, 0);
        }
    }

    private class LocatorResult implements LocatorCallback {
        @Override
        public void onLocationReport(Location location) {
            HiLog.info(LABEL_LOG, "%{public}s",
                    "onLocationReport : " + location.getLatitude() + "-" + location.getAltitude());
            setLocation(location);
        }

        @Override
        public void onStatusChanged(int statusCode) {
            HiLog.info(LABEL_LOG, "%{public}s", "MyLocatorCallback onStatusChanged : " + statusCode);
        }

        @Override
        public void onErrorReport(int errorCode) {
            HiLog.info(LABEL_LOG, "%{public}s", "MyLocatorCallback onErrorReport : " + errorCode);
        }
    }

    private void setLocation(Location location) {
        if (location != null) {
            Date date = new Date(location.getTimeStamp());
            locationDetails = new LocationBean();
            fillGeoInfo(locationDetails, location.getLatitude(), location.getLongitude());
            handler.sendEvent(EVENT_ID);
            gaList.clear();
        } else {
            HiLog.info(LABEL_LOG, "%{public}s", "EventNotifier or Location response is null");
            new ToastDialog(context).setText("EventNotifier or Location response is null").show();
        }
    }

    private void fillGeoInfo(LocationBean locationDetails, double geoLatitude, double geoLongitude) {
        if (geoConvert == null) {
            geoConvert = new GeoConvert();
        }
        if (geoConvert.isGeoAvailable()) {
            try {
                gaList = geoConvert.getAddressFromLocation(geoLatitude, geoLongitude, 1);
                if (!gaList.isEmpty()) {
                    GeoAddress geoAddress = gaList.get(0);
                    setGeo(locationDetails, geoAddress);
                }
            } catch (IllegalArgumentException | IOException e) {
                HiLog.error(LABEL_LOG, "%{public}s", "fillGeoInfo exception");
            }
        }
    }

    private void setGeo(LocationBean locationDetails, GeoAddress geoAddress) {
        locationDetails.setRoadName(checkIfNullOrEmpty(geoAddress.getRoadName()));
        locationDetails.setLocality(checkIfNullOrEmpty(geoAddress.getLocality()));
        locationDetails.setAdministrative(checkIfNullOrEmpty(geoAddress.getAdministrativeArea()));
        locationDetails.setCountryName(checkIfNullOrEmpty(geoAddress.getCountryName()));
    }

    private String checkIfNullOrEmpty(String value) {
        if (value == null || value.isEmpty()) {
            return "NA";
        }
        return value;
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
