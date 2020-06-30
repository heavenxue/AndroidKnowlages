package com.lixue.admin.dagger2.cook;

import java.util.Map;

import javax.inject.Inject;

public class Menu {

    public Map<String,Boolean> menus;

    @Inject
    public Menu( Map<String,Boolean> menus){
        this.menus = menus;
    }

    Map<String,Boolean> getMenus(){
        return menus;
    }

}
