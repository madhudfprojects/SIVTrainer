package com.det.skillinvillage;

public class Class_MainMenu {
    private  String Menu_ID;
    private  String Menu_Name;
    private  String Menu_Link;
    private  String Parent_ID;

    public Class_MainMenu(){}
    public Class_MainMenu(String menu_ID, String menu_Name, String menu_link, String parent_ID) {
        Menu_ID = menu_ID;
        Menu_Name = menu_Name;
        Menu_Link = menu_link;
        Parent_ID = parent_ID;
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

    public String getMenu_Link() {
        return Menu_Link;
    }

    public void setMenu_Link(String menu_Status) {
        Menu_Link = menu_Status;
    }

    public String getParent_ID() {
        return Parent_ID;
    }

    public void setParent_ID(String parent_ID) {
        Parent_ID = parent_ID;
    }
}
