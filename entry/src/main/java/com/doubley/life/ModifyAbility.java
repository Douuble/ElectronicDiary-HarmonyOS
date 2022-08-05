package com.doubley.life;

import com.doubley.life.slice.ModifyAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ModifyAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ModifyAbilitySlice.class.getName());
    }
}
