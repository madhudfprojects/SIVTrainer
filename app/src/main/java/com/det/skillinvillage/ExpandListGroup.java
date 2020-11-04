package com.det.skillinvillage;

import java.util.ArrayList;

public class ExpandListGroup {

    private String Menu_ID;
    private String Menu_Name;
    private String Menu_Sort;
    private ArrayList<Class_SubMenu> childItems;

    public ArrayList<Class_SubMenu> getChildItems() {
        return childItems;
    }
    public void setChildItems(ArrayList<Class_SubMenu> childItems) {
        this.childItems = childItems;
    }

    public String getMenu_ID() {
        return Menu_ID;
    }

    public void setMenu_ID(String menu_ID) {
        Menu_ID = menu_ID;
    }

    public String getMenu_Name() {
        return Menu_Name;
    }

    public void setMenu_Name(String menu_Name) {
        Menu_Name = menu_Name;
    }

    public String getMenu_Sort() {
        return Menu_Sort;
    }

    public void setMenu_Sort(String menu_Sort) {
        Menu_Sort = menu_Sort;
    }


}