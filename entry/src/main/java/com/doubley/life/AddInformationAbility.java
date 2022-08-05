package com.doubley.life;


import com.doubley.life.slice.AddInformationAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class AddInformationAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(AddInformationAbilitySlice.class.getName());
    }

}
