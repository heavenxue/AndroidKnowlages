package com.lixue.admin.dagger2;

import com.lixue.admin.dagger2.cook.Chef;
import com.lixue.admin.dagger2.cook.Menu;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CookModules {

    @Singleton
    @Provides
    public String providerMenus(){
        Map<String, Boolean> menus = new LinkedHashMap<>();
        menus.put("酸菜鱼", true);
        menus.put("土豆丝", true);
        menus.put("铁板牛肉", true);
        Menu menu = new Menu(menus);
        return new Chef(menu).cook();
    }
}
