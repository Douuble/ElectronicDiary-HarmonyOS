package com.doubley.life;

import com.doubley.life.util.DataUtil;
import ohos.aafwk.ability.AbilityPackage;

public class MyApplication extends AbilityPackage {
    @Override
    public void onInitialize() {
        super.onInitialize();
        DataUtil.onInitial(this);
    }
}
