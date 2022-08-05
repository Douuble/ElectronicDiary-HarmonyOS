package com.doubley.life;

import com.doubley.life.slice.InformationAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class InformationAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(InformationAbilitySlice.class.getName());
    }
}
